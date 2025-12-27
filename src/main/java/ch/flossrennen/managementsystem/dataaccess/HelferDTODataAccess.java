package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.mapper.HelferDTOMapper;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.HelferRepository;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.RessortRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class HelferDTODataAccess {
    private final HelferRepository helferRepository;
    private final RessortRepository ressortRepository;
    private final HelferDTOMapper helferDTOMapper;

    public HelferDTODataAccess(HelferRepository helferRepository, RessortRepository ressortRepository, HelferDTOMapper helferDTOMapper) {
        this.helferRepository = helferRepository;
        this.ressortRepository = ressortRepository;
        this.helferDTOMapper = helferDTOMapper;
    }

    @NonNull
    public List<HelferDTO> findAll() {
        return helferRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @NonNull
    public Optional<HelferDTO> findById(@NonNull Long id) {
        return helferRepository.findById(id).map(this::toDTO);
    }

    public void deleteById(@NonNull Long id) {
        helferRepository.deleteById(id);
    }

    @NonNull
    public HelferDTO save(@NonNull HelferDTO helferDTO) {
        if (helferDTO.ressort() == null) {
            throw new IllegalArgumentException("Helfer must have a ressort");
        }
        Ressort ressort = ressortRepository.findById(helferDTO.ressort().id())
                .orElseThrow(() -> new IllegalArgumentException("Ressort not found with id: " + helferDTO.ressort().id()));
        Helfer helfer = helferDTOMapper.toEntity(helferDTO, ressort);
        Helfer savedHelfer = helferRepository.save(helfer);
        return toDTO(savedHelfer);
    }

    private HelferDTO toDTO(Helfer helfer) {
        Ressort ressort = ressortRepository.findById(helfer.getRessort())
                .orElseThrow(() -> new IllegalStateException("Ressort not found with id: " + helfer.getRessort() + " for Helfer: " + helfer.getId()));
        return helferDTOMapper.toDTO(helfer, ressort);
    }
}
