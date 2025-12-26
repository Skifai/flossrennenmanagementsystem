package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.mapper.BenutzerDTOMapper;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.BenutzerRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BenutzerDTODataAccess {
    private final BenutzerRepository benutzerRepository;
    private final BenutzerDTOMapper benutzerDTOMapper;

    public BenutzerDTODataAccess(BenutzerRepository benutzerRepository, BenutzerDTOMapper benutzerDTOMapper) {
        this.benutzerRepository = benutzerRepository;
        this.benutzerDTOMapper = benutzerDTOMapper;
    }

    @NonNull
    public List<BenutzerDTO> findAll() {
        return benutzerRepository.findAll().stream()
                .map(benutzerDTOMapper::toDTO)
                .toList();
    }

    @NonNull
    public Optional<BenutzerDTO> findById(@NonNull Long id) {
        return benutzerRepository.findById(id).map(benutzerDTOMapper::toDTO);
    }

    public void deleteById(@NonNull Long id) {
        benutzerRepository.deleteById(id);
    }

    @NonNull
    public BenutzerDTO save(@NonNull BenutzerDTO benutzerDTO) {
        return benutzerDTOMapper.toDTO(benutzerRepository.save(benutzerDTOMapper.toEntity(benutzerDTO)));
    }
}
