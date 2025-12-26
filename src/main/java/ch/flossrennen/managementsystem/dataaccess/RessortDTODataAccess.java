package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.mapper.RessortDTOMapper;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.RessortRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RessortDTODataAccess {
    private final RessortRepository ressortRepository;
    private final RessortDTOMapper ressortDTOMapper;

    public RessortDTODataAccess(RessortRepository ressortRepository, RessortDTOMapper ressortDTOMapper) {
        this.ressortRepository = ressortRepository;
        this.ressortDTOMapper = ressortDTOMapper;
    }

    @NonNull
    public List<RessortDTO> findAll() {
        return ressortRepository.findAll().stream()
                .map(ressortDTOMapper::toDTO)
                .toList();
    }

    @NonNull
    public Optional<RessortDTO> findById(@NonNull Long id) {
        return ressortRepository.findById(id).map(ressortDTOMapper::toDTO);
    }

    public void deleteById(@NonNull Long id) {
        ressortRepository.deleteById(id);
    }

    @NonNull
    public RessortDTO save(@NonNull RessortDTO ressortDTO) {
        return ressortDTOMapper.toDTO(ressortRepository.save(ressortDTOMapper.toEntity(ressortDTO)));
    }
}
