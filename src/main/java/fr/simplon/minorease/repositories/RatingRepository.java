package fr.simplon.minorease.repositories;

import fr.simplon.minorease.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating,Long> {
}
