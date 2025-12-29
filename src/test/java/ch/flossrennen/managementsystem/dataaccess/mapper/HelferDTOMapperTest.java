package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import ch.flossrennen.managementsystem.initialisation.constants.InitialDataConstants;
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
        Benutzer benutzer = new Benutzer(2L, InitialDataConstants.FESTWIRTSCHAFT_USER_VORNAME, InitialDataConstants.FESTWIRTSCHAFT_USER_NACHNAME, InitialDataConstants.FESTWIRTSCHAFT_USER_TEL, InitialDataConstants.FESTWIRTSCHAFT_USER_EMAIL, "hash", InitialDataConstants.ADMIN_ROLLE);
        Ressort ressort = new Ressort(1L, InitialDataConstants.RESSORT_FESTWIRTSCHAFT_NAME, InitialDataConstants.RESSORT_FESTWIRTSCHAFT_BESCHREIBUNG, InitialDataConstants.RESSORT_FESTWIRTSCHAFT_ZUSTAENDIGKEIT, benutzer);
        Helfer helfer = new Helfer(1L, InitialDataConstants.FESTWIRTSCHAFT_HELFER1_VORNAME, InitialDataConstants.FESTWIRTSCHAFT_HELFER1_NACHNAME, InitialDataConstants.FESTWIRTSCHAFT_HELFER1_EMAIL, InitialDataConstants.FESTWIRTSCHAFT_HELFER1_TEL, ressort);

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
        BenutzerDTO ressortleitung = new BenutzerDTO(2L, InitialDataConstants.FINANZEN_USER_VORNAME, InitialDataConstants.FINANZEN_USER_NACHNAME, InitialDataConstants.FINANZEN_USER_TEL, InitialDataConstants.FINANZEN_USER_EMAIL, "hash", InitialDataConstants.ADMIN_ROLLE);
        RessortDTO ressortDto = new RessortDTO(1L, InitialDataConstants.RESSORT_FINANZEN_NAME, InitialDataConstants.RESSORT_FINANZEN_BESCHREIBUNG, InitialDataConstants.RESSORT_FINANZEN_ZUSTAENDIGKEIT, ressortleitung);
        HelferDTO dto = new HelferDTO(1L, InitialDataConstants.FINANZEN_HELFER1_VORNAME, InitialDataConstants.FINANZEN_HELFER1_NACHNAME, InitialDataConstants.FINANZEN_HELFER1_EMAIL, InitialDataConstants.FINANZEN_HELFER1_TEL, ressortDto);

        Helfer helfer = mapper.toEntity(dto);

        assertEquals(dto.id(), helfer.getId());
        assertEquals(dto.vorname(), helfer.getVorname());
        assertEquals(dto.nachname(), helfer.getNachname());
        assertEquals(dto.email(), helfer.getEmail());
        assertEquals(dto.telefonnummer(), helfer.getTelefonnummer());
        assertEquals(dto.ressort().id(), helfer.getRessort().getId());
    }
}
