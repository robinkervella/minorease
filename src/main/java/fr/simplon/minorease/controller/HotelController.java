package fr.simplon.minorease.controller;

import fr.simplon.minorease.entities.Chambre;
import fr.simplon.minorease.entities.Hotel;
import fr.simplon.minorease.entities.Image;
import fr.simplon.minorease.repositories.ChambreRepository;
import fr.simplon.minorease.repositories.HotelRepository;
import fr.simplon.minorease.services.RechercheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
     * @param dateDebut date de début du séjour
     * @param dateFin date de fin du séjour
     * @param nbPersonne nombre de personnes pour le séjour
     * @param ville ville où l'hôtel est recherché
     * @param model modèle pour passer des attributs à la vue
     * @return hotels le nom de la vue à afficher
     */
    @GetMapping(path = "/chercherHotel")
    public String rechercherUnHotel(
            @RequestParam("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateDebut,
            @RequestParam("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFin,
            @RequestParam("nbPersonne") int nbPersonne,
            @RequestParam("ville") String ville,  Model model) {
        ville.toLowerCase();
        List<Hotel> allHotels = hotelRepository.findAll();
        LocalDateTime localDateTimeDebut = LocalDateTime.of(dateDebut, LocalTime.MIDNIGHT);
        LocalDateTime localDateTimeFin = LocalDateTime.of(dateFin, LocalTime.MIDNIGHT);
        List<Hotel> listeARetourner = rechercheService.rechercheHotel(allHotels,ville,localDateTimeDebut,localDateTimeFin,nbPersonne);
        model.addAttribute("hotel", listeARetourner);
        return "hotels";
    }

    /**
     * Recherche des hôtels en fonction des critères de l'utilisateur, y compris le prix.
     *
     * @param ville ville où l'hôtel est recherché
     * @param dateDebut date de début du séjour
     * @param dateFin date de fin du séjour
     * @param nbPersonne nombre de personnes pour le séjour
     * @param prixMini prix minimum que l'utilisateur est prêt à payer
     * @param prixMax prix maximum que l'utilisateur est prêt à payer
     * @param model modèle pour passer des attributs à la vue
     * @return hotels le nom de la vue à afficher
     */
    @GetMapping(path = "/Hotel/{ville}/{dateDebut}/{dateFin}/{nbPersonne}/{prixMax}/{prixMini}")
    public String rechercherUnHotelParPrix(@RequestParam("ville") String ville, @RequestParam("dateDebut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut, @RequestParam("dateFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin, @RequestParam("nbPersonne") int nbPersonne,@RequestParam("prixMini")double prixMini,@RequestParam("prixMax")double prixMax, Model model) {
        ville.toLowerCase();
        List<Hotel> allHotels = hotelRepository.findAll();
        LocalDateTime localDateTimeDebut = LocalDateTime.of(dateDebut, LocalTime.MIDNIGHT);
        LocalDateTime localDateTimeFin = LocalDateTime.of(dateFin, LocalTime.MIDNIGHT);
        List<Hotel> listeARetourner = rechercheService.rechercheHotel(allHotels,ville,localDateTimeDebut,localDateTimeFin,nbPersonne);
        List<Hotel> listeTriéAvecPrix = rechercheService.trouverLesHotelsDansLaFourchetteDePrix(listeARetourner,prixMini,prixMax);
        model.addAttribute("hotel", listeTriéAvecPrix);
        return "hotels";
    }

    @GetMapping(path ="/reserver/hotel/{id}")
        public String afficherHotelParSonId(@PathVariable Long id,Model model){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        Hotel hotel = optionalHotel.orElseThrow(() -> new NoSuchElementException("Hotel introuvable"));
        List<Integer> typesDeChambre = rechercheService.renvoyerTypeChambre(hotel.getChambres());
        model.addAttribute("hotel", hotel);
        model.addAttribute("typeChambre",typesDeChambre);
        return "ficheHotel";
    }

    @GetMapping(path = "reserver/hotel/{id}/recap")
    public String afficherRecapReservation(@PathVariable Long id,Model model){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        Hotel hotel = optionalHotel.orElseThrow(() -> new NoSuchElementException("Hotel introuvable"));
        List<Chambre> allChambres = hotel.getChambres();
        Image image = hotel.getImages().get(1);
        model.addAttribute("chambres", allChambres);
        model.addAttribute("hotel",hotel);
        model.addAttribute("image",image);
        return "recapitulatif";
    }

}
