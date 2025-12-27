package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.DTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RessortDTOService implements DTOService<RessortDTO> {
    private final DTODataAccess<RessortDTO> ressortDTODataAccess;

    public RessortDTOService(DTODataAccess<RessortDTO> ressortDTODataAccess) {
        this.ressortDTODataAccess = ressortDTODataAccess;
    }

    @NonNull
    public List<RessortDTO> findAll() {
        return ressortDTODataAccess.findAll();
    }
}
