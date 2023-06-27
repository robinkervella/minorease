package fr.simplon.minorease.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.simplon.minorease.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
