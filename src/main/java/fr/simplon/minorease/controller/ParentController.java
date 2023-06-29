package fr.simplon.minorease.controller;

import fr.simplon.minorease.entities.Parent;
import fr.simplon.minorease.entities.Reservation;
import fr.simplon.minorease.repositories.HotelRepository;
import fr.simplon.minorease.repositories.ParentRepository;
import fr.simplon.minorease.repositories.ReservationRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class ParentController {
    private final ParentRepository parentRepository; // Import du repository Parent
    private final ReservationRepository reservationRepository; // Import du repository Reservation
    private final HotelRepository hotelRepository; // Import du repository Hotel

    // Injection des repositories via le constructeur
    public ParentController(ParentRepository parentRepository, ReservationRepository reservationRepository, HotelRepository hotelRepository) {
        this.parentRepository = parentRepository;
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
    }
    @GetMapping("/")
    public String afficherPageAccueil() {
        return "index";
    }


}
