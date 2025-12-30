package ch.flossrennen.managementsystem.dataaccess.persistence.repository;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {

    Optional<Benutzer> findByEmail(String email);

    Optional<Benutzer> findByTelefonnummer(String telefonnummer);
}
