package fr.simplon.minorease.controller;

import fr.simplon.minorease.entities.Hotel;
import fr.simplon.minorease.repositories.HotelRepository;
import fr.simplon.minorease.services.RechercheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
@Controller
public class HotelController {
    @Autowired
    private RechercheService rechercheService;
    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/hotels")
    public String afficherHotels(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 10; // Nombre d'hôtels par page
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Hotel> hotels = hotelRepository.findAll(pageable);
        model.addAttribute("hotels", hotels);
        return "hotels";
    }
    @GetMapping(path = "/Hotel/{ville}/{dateDebut}/{dateFin}/{nbPersonne}")
    public String rechercherUnHotel(@PathVariable String ville, @PathVariable LocalDateTime dateDebut, @PathVariable LocalDateTime dateFin, @PathVariable int nbPersonne, Model model) {
        List<Hotel> allHotels = hotelRepository.findAll();
        List<Hotel> listeARetourner = rechercheService.rechercheHotel(allHotels,ville,dateDebut,dateFin,nbPersonne);
        model.addAttribute("hotel", listeARetourner);
        return "hotels";
    }

    @GetMapping(path = "/Hotel/{ville}/{dateDebut}/{dateFin}/{nbPersonne}/{prixMax}/{prixMini}")
    public String rechercherUnHotelParPrix(@PathVariable String ville, @PathVariable LocalDateTime dateDebut, @PathVariable LocalDateTime dateFin, int nbPersonne,double prixMax,double prixMini, Model model) {
        List<Hotel> allHotels = hotelRepository.findAll();
        List<Hotel> listeARetourner = rechercheService.rechercheHotel(allHotels,ville,dateDebut,dateFin,nbPersonne);
        List<Hotel> listeTriéAvecPrix = rechercheService.trouverLesHotelsDansLaFourchetteDePrix(listeARetourner,prixMini,prixMax);
        model.addAttribute("hotel", listeTriéAvecPrix);
        return "hotels";
    }
}
