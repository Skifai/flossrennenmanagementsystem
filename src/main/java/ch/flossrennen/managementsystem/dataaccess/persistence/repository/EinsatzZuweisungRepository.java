package ch.flossrennen.managementsystem.dataaccess.persistence.repository;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzZuweisung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EinsatzZuweisungRepository extends JpaRepository<EinsatzZuweisung, Long> {

    List<EinsatzZuweisung> findByEinsatzId(Long einsatzId);

    List<EinsatzZuweisung> findByHelferId(Long helferId);

    void deleteByEinsatzIdAndHelferId(Long einsatzId, Long helferId);

    @Query("""
            SELECT zuweisung FROM EinsatzZuweisung zuweisung
            WHERE zuweisung.helfer.id = :helferId
            AND (:einsatzId IS NULL OR zuweisung.einsatz.id <> :einsatzId)
            AND zuweisung.einsatz.startzeit < :end
            AND zuweisung.einsatz.endzeit > :start
            """)
    List<EinsatzZuweisung> findOverlappingZuweisungen(@Param("helferId") Long helferId,
                                                      @Param("einsatzId") Long einsatzId,
                                                      @Param("start") LocalDateTime start,
                                                      @Param("end") LocalDateTime end);
}