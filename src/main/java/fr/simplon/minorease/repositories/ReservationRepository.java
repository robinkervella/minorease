package fr.simplon.minorease.repositories;

import fr.simplon.minorease.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
