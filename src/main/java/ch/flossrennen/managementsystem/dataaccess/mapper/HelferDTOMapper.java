package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
public class HelferDTOMapper {

    @NonNull
    public HelferDTO toDTO(@NonNull Helfer helfer) {
        return new HelferDTO(
                helfer.getId(),
                helfer.getVorname(),
                helfer.getNachname(),
                helfer.getEmail(),
                helfer.getTelefonnummer(),
                helfer.getRessort());
    }

    @NonNull
    public Helfer toEntity(@NonNull HelferDTO helferDTO) {
        return new Helfer(
                helferDTO.id(),
                helferDTO.vorname(),
                helferDTO.nachname(),
                helferDTO.email(),
                helferDTO.telefonnummer(),
                helferDTO.ressort()
        );
    }
}
