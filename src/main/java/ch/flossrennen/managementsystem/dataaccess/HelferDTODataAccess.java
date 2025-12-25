package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.mapper.HelferDTOMapper;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.HelferRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class HelferDTODataAccess {
    private final HelferRepository helferRepository;
    private final HelferDTOMapper helferDTOMapper;

    public HelferDTODataAccess(HelferRepository helferRepository, HelferDTOMapper helferDTOMapper) {
        this.helferRepository = helferRepository;
        this.helferDTOMapper = helferDTOMapper;
    }

    @NonNull
    public List<HelferDTO> findAll() {
        return helferRepository.findAll().stream()
                .map(helferDTOMapper::toDTO)
                .toList();
    }

    @NonNull
    public Optional<HelferDTO> findById(@NonNull Long id) {
        return helferRepository.findById(id).map(helferDTOMapper::toDTO);
    }

    public void deleteById(@NonNull Long id) {
        helferRepository.deleteById(id);
    }

    @NonNull
    public HelferDTO save(@NonNull HelferDTO helferDTO) {
        return helferDTOMapper.toDTO(helferRepository.save(helferDTOMapper.toEntity(helferDTO)));
    }
}
