package fr.simplon.minorease.repositories;

import fr.simplon.minorease.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
