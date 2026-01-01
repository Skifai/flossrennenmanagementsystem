package ch.flossrennen.managementsystem.dataaccess.persistence.repository;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogEntry, Long>, JpaSpecificationExecutor<LogEntry> {
}