package fr.simplon.minorease.services;

import fr.simplon.minorease.entities.Parent;
import fr.simplon.minorease.entities.Reservation;
import fr.simplon.minorease.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
}

    /*public List<Reservation> getReservationsByParent(Parent parent) {
        return reservationRepository.findByParentOrderByDateDesc(parent);
    }
}*/

