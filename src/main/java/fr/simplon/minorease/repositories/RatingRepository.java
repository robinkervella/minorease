package fr.simplon.minorease.repositories;

import fr.simplon.minorease.entities.Hotel;
import fr.simplon.minorease.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {
    List<Rating> findByHotel(Hotel hotel);
}
