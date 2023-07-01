package fr.simplon.minorease.controller;

import fr.simplon.minorease.entities.Chambre;
import fr.simplon.minorease.entities.Hotel;
import fr.simplon.minorease.entities.Image;
import fr.simplon.minorease.repositories.ChambreRepository;
import fr.simplon.minorease.repositories.HotelRepository;
import fr.simplon.minorease.services.RechercheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Classe contrôleur pour la gestion des hôtels.
 */
@Controller
public class HotelController {
    @Autowired
    private RechercheService rechercheService;
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    /**
     * Recherche des hôtels en fonction des critères de l'utilisateur.
     *
     * @param dateDebut  date de début du séjour
     * @param dateFin    date de fin du séjour
     * @param nbPersonne nombre de personnes pour le séjour
     * @param ville      ville où l'hôtel est recherché
     * @param model      modèle pour passer des attributs à la vue
     * @return hotels le nom de la vue à afficher
     */
    @GetMapping(path = "/chercherHotel")
    public String rechercherUnHotel(
            @RequestParam("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateDebut,
            @RequestParam("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFin,
            @RequestParam("nbPersonne") int nbPersonne,
            @RequestParam("ville") String ville,
            //@RequestParam(value = "page", defaultValue = "0") int page,
            Model model)
    {
        int pageSize = 10;
        List<Hotel> allHotels = hotelRepository.findAll();
        LocalDateTime localDateTimeDebut = LocalDateTime.of(dateDebut, LocalTime.MIDNIGHT);
        LocalDateTime localDateTimeFin = LocalDateTime.of(dateFin, LocalTime.MIDNIGHT);
        List<Hotel> listeARetourner = rechercheService.rechercheHotel(allHotels, ville, localDateTimeDebut, localDateTimeFin, nbPersonne);
        model.addAttribute("hotel", listeARetourner);
        model.addAttribute("dateDebut",dateDebut);
        model.addAttribute("dateFin",dateFin);
        model.addAttribute("ville", ville);
        model.addAttribute("nbPersonne",nbPersonne );
/*
        int start = page * pageSize;
        int end = Math.min(start + pageSize, listeARetourner.size());
        List<Hotel> hotelsPerPage = listeARetourner.subList(start, end)
        //model.addAttribute("hotel", hotelsPerPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (listeARetourner.size() - 1) / pageSize + 1);
        */

        return "hotels";
    }

    /**
     * Recherche des hôtels en fonction des critères de l'utilisateur, y compris le prix.
     *
     * @param ville      ville où l'hôtel est recherché
     * @param dateDebut  date de début du séjour
     * @param dateFin    date de fin du séjour
     * @param nbPersonne nombre de personnes pour le séjour
     * @param prixMini   prix minimum que l'utilisateur est prêt à payer
     * @param prixMax    prix maximum que l'utilisateur est prêt à payer
     * @param model      modèle pour passer des attributs à la vue
     * @return hotels le nom de la vue à afficher
     */
    @GetMapping(path = "/Hotel/{ville}/{dateDebut}/{dateFin}/{nbPersonne}/{prixMax}/{prixMini}")
    public String rechercherUnHotelParPrix(
            @PathVariable("ville") String ville,
            @PathVariable("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateDebut,
            @PathVariable("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFin,
            @PathVariable("nbPersonne") int nbPersonne, @RequestParam("prixMini") double prixMini,
            @PathVariable("prixMax") double prixMax, Model model) {
        List<Hotel> allHotels = hotelRepository.findAll();
        LocalDateTime localDateTimeDebut = LocalDateTime.of(dateDebut, LocalTime.MIDNIGHT);
        LocalDateTime localDateTimeFin = LocalDateTime.of(dateFin, LocalTime.MIDNIGHT);
        List<Hotel> listeARetourner = rechercheService.rechercheHotel(allHotels, ville, localDateTimeDebut, localDateTimeFin, nbPersonne);
        List<Hotel> listeTriéAvecPrix = rechercheService.trouverLesHotelsDansLaFourchetteDePrix(listeARetourner, prixMini, prixMax);
        model.addAttribute("hotel", listeTriéAvecPrix);
        model.addAttribute("dateDebut",dateDebut);
        model.addAttribute("dateFin",dateFin);

        return "hotels";
    }

    @GetMapping(path ="/reserver/hotel/{id}/{dateDebut}/{dateFin}")
    public String afficherHotelParSonId(
            @PathVariable long id,
            @PathVariable("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateDebut,
            @PathVariable("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFin,
            Model model){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        LocalDateTime localDateTimeDebut = LocalDateTime.of(dateDebut, LocalTime.MIDNIGHT);
        LocalDateTime localDateTimeFin = LocalDateTime.of(dateFin, LocalTime.MIDNIGHT);
        Hotel hotel = optionalHotel.orElseThrow(() -> new NoSuchElementException("Hotel introuvable"));
        List<Integer> typesDeChambre = rechercheService.renvoyerTypeChambre(hotel.getChambres(),localDateTimeDebut,localDateTimeFin);
        Collections.sort(typesDeChambre);
        model.addAttribute("hotel", hotel);
        model.addAttribute("typeChambres",typesDeChambre);
        model.addAttribute("dateDebut", dateFin);
        model.addAttribute("dateFin", dateFin);

        return "ficheHotel";
    }


    @GetMapping(path ="/reserver/hotel")
    public String test12(){
        return "ficheHotel";
    }


    @GetMapping(path = "reserver/hotel/{id}/recap")
    public String afficherRecapReservation(@PathVariable Long id, Model model) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        Hotel hotel = optionalHotel.orElseThrow(() -> new NoSuchElementException("Hotel introuvable"));
        List<Chambre> allChambres = chambreRepository.findAll();
        Image image = hotel.getImages().get(1);
        model.addAttribute("chambres", allChambres);
        model.addAttribute("hotel", hotel);
        model.addAttribute("image", image);
        return "recapitulatif";
    }


    @GetMapping("/geolocalisationHotels")
    public List<Hotel> getAllHotelsAPIGeolocalisation() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels;
    }
}
