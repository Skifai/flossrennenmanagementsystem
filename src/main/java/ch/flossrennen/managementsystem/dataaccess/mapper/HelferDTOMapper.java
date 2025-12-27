package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
public class HelferDTOMapper {

    private final RessortDTOMapper ressortDTOMapper;

    public HelferDTOMapper(RessortDTOMapper ressortDTOMapper) {
        this.ressortDTOMapper = ressortDTOMapper;
    }

    @NonNull
    public HelferDTO toDTO(@NonNull Helfer helfer, @NonNull Ressort ressort) {
        return new HelferDTO(
                helfer.getId(),
                helfer.getVorname(),
                helfer.getNachname(),
                helfer.getEmail(),
                helfer.getTelefonnummer(),
                ressortDTOMapper.toDTO(ressort));
    }

    @NonNull
    public Helfer toEntity(@NonNull HelferDTO helferDTO, @NonNull Ressort ressort) {
        return new Helfer(
                helferDTO.id(),
                helferDTO.vorname(),
                helferDTO.nachname(),
                helferDTO.email(),
                helferDTO.telefonnummer(),
                ressort.getId()
        );
    }
}
