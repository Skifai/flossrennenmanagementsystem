package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.RessortDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RessortDTOService {
    private final RessortDTODataAccess ressortDTODataAccess;

    public RessortDTOService(RessortDTODataAccess ressortDTODataAccess) {
        this.ressortDTODataAccess = ressortDTODataAccess;
    }

    @NonNull
    public List<RessortDTO> findAll() {
        return ressortDTODataAccess.findAll();
    }
}
