package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.DTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelferDTOService implements DTOService<HelferDTO> {
    private final DTODataAccess<HelferDTO> helferDTODataAccess;

    public HelferDTOService(DTODataAccess<HelferDTO> helferDTODataAccess) {
        this.helferDTODataAccess = helferDTODataAccess;
    }

    @NonNull
    public HelferDTO save(@NonNull HelferDTO helferDTO) {
            HelferDTO savedHelferDTO = helferDTODataAccess.save(helferDTO);
            return savedHelferDTO;
    }

    // TODO: Implement Filtering for HelferDTOs
    @NonNull
    public List<HelferDTO> findAll() {
        return helferDTODataAccess.findAll();
    }

    public void delete(@NonNull HelferDTO helferDTO) {
        helferDTODataAccess.deleteById(helferDTO.id());
    }
}
