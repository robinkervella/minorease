/**
 * Creates a Leaflet map centered on Bretagne, France and adds a tile layer from OpenStreetMap.
 * @type {L.Map} The Leaflet map object.
 */
let map = L.map('mapid').setView([48.2020471, -2.9326435], 8);

// ajouter une couche de tuiles OpenStreetMap à la carte
L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 14,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);

/**
 * Fetches hotels data from the API and adds markers for each hotel to the map.
 */
fetch("/geolocalisationHotels")
    .then(response => response.json())
    .then(data => {
        data.forEach(hotel => {
            let marker = L.marker([hotel.latitude, hotel.longitude]).addTo(map);
            let url = hotel.url;
            if (!/^http(s)?:\/\//i.test(url)) {
                url = "http://" + url;
            }
            let link = "<a href='" + url + "'>" + hotel.url + "</a>";
            marker.bindPopup("<b>" + hotel.libelle+ "</b><br>" + hotel.addresse + "<br>" +
                 hotel.ville + "<br>" + link).openPopup();


            // Add a 'click' event listener to the marker
            marker.on('click', function() {
                // Change the zoom level of the map
                map.setView([hotel.latitude, hotel.longitude], 14);
            });


        });
    })
    .catch(error => console.error(error));