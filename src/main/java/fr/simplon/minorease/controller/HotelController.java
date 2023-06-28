package fr.simplon.minorease.controller;

import fr.simplon.minorease.entities.Hotel;
import fr.simplon.minorease.repositories.HotelRepository;
import fr.simplon.minorease.services.RechercheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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
@Controller
public class HotelController {
    @Autowired
    private RechercheService rechercheService;
    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping(path = "/Hotel/{ville}/{dateDebut}/{dateFin}/{nbPersonne}")
    public String rechercherUnHotel(
            @RequestParam("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateDebut,
            @RequestParam("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFin,
            @RequestParam("nbPersonne") int nbPersonne,
            @RequestParam("ville") String ville,  Model model) {

        List<Hotel> allHotels = hotelRepository.findAll();

        LocalDateTime localDateTimeDebut = LocalDateTime.of(dateDebut, LocalTime.MIDNIGHT);
        LocalDateTime localDateTimeFin = LocalDateTime.of(dateFin, LocalTime.MIDNIGHT);

        System.out.println("Ville: " + ville);
        System.out.println("Date de début: " + dateDebut);
        System.out.println("Date de fin: " + dateFin);
        System.out.println("Nombre de personnes: " + nbPersonne);
        List<Hotel> listeARetourner = rechercheService.rechercheHotel(allHotels,ville,localDateTimeDebut,localDateTimeFin,nbPersonne);
        model.addAttribute("hotel", listeARetourner);
        return "hotels";
    }

    @GetMapping(path = "/Hotel/{ville}/{dateDebut}/{dateFin}/{nbPersonne}/{prixMax}/{prixMini}")
    public String rechercherUnHotelParPrix(@RequestParam("ville") String ville, @RequestParam("dateDebut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut, @RequestParam("dateFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin, @RequestParam("nbPersonne") int nbPersonne,@RequestParam("prixMini")double prixMini,@RequestParam("prixMax")double prixMax, Model model) {
        List<Hotel> allHotels = hotelRepository.findAll();
        LocalDateTime localDateTimeDebut = LocalDateTime.of(dateDebut, LocalTime.MIDNIGHT);
        LocalDateTime localDateTimeFin = LocalDateTime.of(dateFin, LocalTime.MIDNIGHT);
        List<Hotel> listeARetourner = rechercheService.rechercheHotel(allHotels,ville,localDateTimeDebut,localDateTimeFin,nbPersonne);
        List<Hotel> listeTriéAvecPrix = rechercheService.trouverLesHotelsDansLaFourchetteDePrix(listeARetourner,prixMini,prixMax);
        model.addAttribute("hotel", listeTriéAvecPrix);
        return "hotels";
    }
}
