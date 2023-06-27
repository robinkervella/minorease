package fr.simplon.minorease.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.simplon.minorease.entities.Mineur;

public interface MineurRepository extends JpaRepository<Mineur,Long> {
}

