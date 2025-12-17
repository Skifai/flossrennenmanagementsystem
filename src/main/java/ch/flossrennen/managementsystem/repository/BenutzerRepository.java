package ch.flossrennen.managementsystem.repository;

import ch.flossrennen.managementsystem.model.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {

    Optional<Benutzer> findByEmail(String email);
}
