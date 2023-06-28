package fr.simplon.minorease.controller;

import fr.simplon.minorease.entities.Hotel;
import fr.simplon.minorease.repositories.HotelRepository;
import fr.simplon.minorease.services.RechercheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String rechercherUnHotel(@PathVariable String ville, @PathVariable LocalDate dateDebut, @PathVariable LocalDate dateFin, @PathVariable int nbPersonne, Model model) {
        List<Hotel> allHotels = hotelRepository.findAll();
        LocalDateTime localDateTimeDebut = LocalDateTime.of(dateDebut, LocalTime.MIDNIGHT);
        LocalDateTime localDateTimeFin = LocalDateTime.of(dateFin, LocalTime.MIDNIGHT);

        List<Hotel> listeARetourner = rechercheService.rechercheHotel(allHotels,ville,localDateTimeDebut,localDateTimeFin,nbPersonne);
        model.addAttribute("hotel", listeARetourner);
        return "hotels";
    }

    @GetMapping(path = "/Hotel/{ville}/{dateDebut}/{dateFin}/{nbPersonne}/{prixMax}/{prixMini}")
    public String rechercherUnHotelParPrix(@PathVariable String ville, @PathVariable LocalDate dateDebut, @PathVariable LocalDate dateFin, int nbPersonne,double prixMax,double prixMini, Model model) {
        List<Hotel> allHotels = hotelRepository.findAll();
        LocalDateTime localDateTimeDebut = LocalDateTime.of(dateDebut, LocalTime.MIDNIGHT);
        LocalDateTime localDateTimeFin = LocalDateTime.of(dateFin, LocalTime.MIDNIGHT);
        List<Hotel> listeARetourner = rechercheService.rechercheHotel(allHotels,ville,localDateTimeDebut,localDateTimeFin,nbPersonne);
        List<Hotel> listeTriéAvecPrix = rechercheService.trouverLesHotelsDansLaFourchetteDePrix(listeARetourner,prixMini,prixMax);
        model.addAttribute("hotel", listeTriéAvecPrix);
        return "hotels";
    }
}
