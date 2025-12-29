package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class HelferDTOMapperTest {

    private RessortDTOMapper ressortMapper;
    private HelferDTOMapper mapper;

    @BeforeEach
    void setUp() {
        BenutzerDTOMapper benutzerDTOMapper = Mappers.getMapper(BenutzerDTOMapper.class);
        benutzerDTOMapper.passwordEncoder = mock(PasswordEncoder.class);

        ressortMapper = Mappers.getMapper(RessortDTOMapper.class);
        ReflectionTestUtils.setField(ressortMapper, "benutzerDTOMapper", benutzerDTOMapper);

        mapper = Mappers.getMapper(HelferDTOMapper.class);
        ReflectionTestUtils.setField(mapper, "ressortDTOMapper", ressortMapper);
    }

    @Test
    void toDTO() {
        Benutzer benutzer = new Benutzer(2L, "Hans", "Muster", "0791234567", "hans.muster@test.ch", "hash", BenutzerRolle.RESSORTLEITER);
        Ressort ressort = new Ressort(1L, "Sicherheit", "Sicherheit auf dem Fluss", "Rettung", benutzer);
        Helfer helfer = new Helfer(1L, "Hans", "Muster", "hans.muster@test.ch", "0791234567", ressort);

        HelferDTO dto = mapper.toDTO(helfer);

        assertEquals(helfer.getId(), dto.id());
        assertEquals(helfer.getVorname(), dto.vorname());
        assertEquals(helfer.getNachname(), dto.nachname());
        assertEquals(helfer.getEmail(), dto.email());
        assertEquals(helfer.getTelefonnummer(), dto.telefonnummer());
        assertEquals(helfer.getRessort().getId(), dto.ressort().id());
    }

    @Test
    void toEntity() {
        BenutzerDTO ressortleitung = new BenutzerDTO(2L, "Hans", "Muster", "0791234567", "hans.muster@test.ch", "hash", BenutzerRolle.RESSORTLEITER);
        RessortDTO ressortDto = new RessortDTO(1L, "Sicherheit", "Sicherheit auf dem Fluss", "Rettung", ressortleitung);
        HelferDTO dto = new HelferDTO(1L, "Hans", "Muster", "hans.muster@test.ch", "0791234567", ressortDto);

        Helfer helfer = mapper.toEntity(dto);

        assertEquals(dto.id(), helfer.getId());
        assertEquals(dto.vorname(), helfer.getVorname());
        assertEquals(dto.nachname(), helfer.getNachname());
        assertEquals(dto.email(), helfer.getEmail());
        assertEquals(dto.telefonnummer(), helfer.getTelefonnummer());
        assertEquals(dto.ressort().id(), helfer.getRessort().getId());
    }
}
