package fr.simplon.minorease.controller;

import fr.simplon.minorease.entities.Hotel;
import fr.simplon.minorease.repositories.HotelRepository;
import fr.simplon.minorease.services.RechercheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
@Controller
public class HotelController {
    @Autowired
    private RechercheService rechercheService;
    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping(path = "/rechercherUnHotel")
    public String rechercherUnHotel(@PathVariable String ville, @PathVariable LocalDateTime dateDebut, @PathVariable LocalDateTime dateFin, int nbPersonne, Model model) {
        List<Hotel> allHotels = hotelRepository.findAll();
        List<Hotel> listeARetourner = rechercheService.rechercheHotel(allHotels,ville,dateDebut,dateFin,nbPersonne);
        model.addAttribute("hotel", listeARetourner);
        return "hotels";
    }
}
