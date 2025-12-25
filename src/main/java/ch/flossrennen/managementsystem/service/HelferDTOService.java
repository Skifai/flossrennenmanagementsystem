package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.HelferDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.util.CheckResult;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelferDTOService {
    private final HelferDTODataAccess helferDTODataAccess;

    public HelferDTOService(HelferDTODataAccess helferDTODataAccess) {
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
