package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
public class HelferDTOMapper implements DTOMapper<Helfer, HelferDTO> {

    private final RessortDTOMapper ressortDTOMapper;

    public HelferDTOMapper(RessortDTOMapper ressortDTOMapper) {
        this.ressortDTOMapper = ressortDTOMapper;
    }

    @NonNull
    @Override
    public HelferDTO toDTO(@NonNull Helfer helfer) {
        return new HelferDTO(
                helfer.getId(),
                helfer.getVorname(),
                helfer.getNachname(),
                helfer.getEmail(),
                helfer.getTelefonnummer(),
                ressortDTOMapper.toDTO(helfer.getRessort()));
    }

    @NonNull
    @Override
    public Helfer toEntity(@NonNull HelferDTO helferDTO) {
        return new Helfer(
                helferDTO.id(),
                helferDTO.vorname(),
                helferDTO.nachname(),
                helferDTO.email(),
                helferDTO.telefonnummer(),
                ressortDTOMapper.toEntity(helferDTO.ressort())
        );
    }
}
