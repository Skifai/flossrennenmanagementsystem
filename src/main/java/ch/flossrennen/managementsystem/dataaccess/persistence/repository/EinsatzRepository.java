package ch.flossrennen.managementsystem.dataaccess.persistence.repository;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.Einsatz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EinsatzRepository extends JpaRepository<Einsatz, Long> {
}