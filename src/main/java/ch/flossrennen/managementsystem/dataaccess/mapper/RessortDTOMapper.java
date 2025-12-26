package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
public class RessortDTOMapper {

    @NonNull
    public RessortDTO toDTO(@NonNull Ressort ressort) {
        return new RessortDTO(
                ressort.getId(),
                ressort.getName(),
                ressort.getBeschreibung(),
                ressort.getZustaendigkeit(),
                ressort.getVerantwortlich());
    }

    @NonNull
    public Ressort toEntity(@NonNull RessortDTO ressortDTO) {
        return new Ressort(
                ressortDTO.id(),
                ressortDTO.name(),
                ressortDTO.beschreibung(),
                ressortDTO.zustaendigkeit(),
                ressortDTO.verantwortlich()
        );
    }
}
