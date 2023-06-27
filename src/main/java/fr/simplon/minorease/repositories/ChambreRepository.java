package fr.simplon.minorease.repositories;

import fr.simplon.minorease.entities.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChambreRepository extends JpaRepository<Chambre,Long> {
}
