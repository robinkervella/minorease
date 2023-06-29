package fr.simplon.minorease.controller;

import fr.simplon.minorease.entities.Parent;
import fr.simplon.minorease.entities.Reservation;
import fr.simplon.minorease.repositories.HotelRepository;
import fr.simplon.minorease.repositories.ParentRepository;
import fr.simplon.minorease.repositories.ReservationRepository;
import fr.simplon.minorease.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class ParentController {
    private final ReservationService reservationService;

    @Autowired
    private ParentRepository parentRepository;
    private final ReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;

    public ParentController(ReservationService reservationService, ParentRepository parentRepository, ReservationRepository reservationRepository, HotelRepository hotelRepository) {
        this.reservationService = reservationService;
        this.parentRepository = parentRepository;
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
    }

    @GetMapping("/")
    public String afficherPageAccueil() {
        return "index";
    }

}
