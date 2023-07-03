package fr.simplon.minorease.repositories;

import fr.simplon.minorease.entities.Parent;
import fr.simplon.minorease.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

}
