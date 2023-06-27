package fr.simplon.minorease.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.simplon.minorease.entities.Parent;

public interface ParentRepository extends JpaRepository<Parent,Long> {
}
