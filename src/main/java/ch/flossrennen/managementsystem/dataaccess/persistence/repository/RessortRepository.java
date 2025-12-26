package ch.flossrennen.managementsystem.dataaccess.persistence.repository;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RessortRepository extends JpaRepository<Ressort, Long> {
}
