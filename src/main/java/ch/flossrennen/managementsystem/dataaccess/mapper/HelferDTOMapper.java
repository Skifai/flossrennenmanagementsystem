package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

@Component
public class HelferDTOMapper {

    @NonNull
    public HelferDTO toDTO(@NonNull Helfer helfer, @NonNull Ressort ressort) {
        return new HelferDTO(
                helfer.getId(),
                helfer.getVorname(),
                helfer.getNachname(),
                helfer.getEmail(),
                helfer.getTelefonnummer(),
                ressort.getName());
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
