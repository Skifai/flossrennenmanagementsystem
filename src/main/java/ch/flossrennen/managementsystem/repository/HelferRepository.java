package ch.flossrennen.managementsystem.repository;

import ch.flossrennen.managementsystem.model.Helfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HelferRepository extends JpaRepository<Helfer, Long> {

    Optional<Helfer> findByEmail(String email);
}
