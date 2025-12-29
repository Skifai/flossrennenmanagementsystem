package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import ch.flossrennen.managementsystem.initialisation.constants.InitialDataConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class RessortDTOMapperTest {

    private BenutzerDTOMapper benutzerDTOMapper;
    private RessortDTOMapper mapper;

    @BeforeEach
    void setUp() {
        benutzerDTOMapper = Mappers.getMapper(BenutzerDTOMapper.class);
        benutzerDTOMapper.passwordEncoder = mock(PasswordEncoder.class);

        mapper = Mappers.getMapper(RessortDTOMapper.class);
        // MapStruct uses the mapper instance, we need to make sure it's injected if not using Spring context
        ReflectionTestUtils.setField(mapper, "benutzerDTOMapper", benutzerDTOMapper);
    }

    @Test
    void toDTO() {
        Benutzer benutzer = new Benutzer(2L, InitialDataConstants.VERKEHR_USER_VORNAME, InitialDataConstants.VERKEHR_USER_NACHNAME, InitialDataConstants.VERKEHR_USER_TEL, InitialDataConstants.VERKEHR_USER_EMAIL, "hash", InitialDataConstants.ADMIN_ROLLE);

        Ressort ressort = new Ressort(1L, InitialDataConstants.RESSORT_VERKEHR_NAME, InitialDataConstants.RESSORT_VERKEHR_BESCHREIBUNG, InitialDataConstants.RESSORT_VERKEHR_ZUSTAENDIGKEIT, benutzer);
        RessortDTO dto = mapper.toDTO(ressort);

        assertEquals(ressort.getId(), dto.id());
        assertEquals(ressort.getName(), dto.name());
        assertEquals(ressort.getBeschreibung(), dto.beschreibung());
        assertEquals(ressort.getZustaendigkeit(), dto.zustaendigkeit());
        assertEquals(2L, dto.ressortleitung().id());
    }

    @Test
    void toEntity() {
        BenutzerDTO ressortleitung = new BenutzerDTO(2L, InitialDataConstants.RENNLEITUNG_USER_VORNAME, InitialDataConstants.RENNLEITUNG_USER_NACHNAME, InitialDataConstants.RENNLEITUNG_USER_TEL, InitialDataConstants.RENNLEITUNG_USER_EMAIL, "hash", InitialDataConstants.ADMIN_ROLLE);
        RessortDTO dto = new RessortDTO(1L, InitialDataConstants.RESSORT_RENNLEITUNG_NAME, InitialDataConstants.RESSORT_RENNLEITUNG_BESCHREIBUNG, InitialDataConstants.RESSORT_RENNLEITUNG_ZUSTAENDIGKEIT, ressortleitung);
        Ressort ressort = mapper.toEntity(dto);

        assertEquals(dto.id(), ressort.getId());
        assertEquals(dto.name(), ressort.getName());
        assertEquals(dto.beschreibung(), ressort.getBeschreibung());
        assertEquals(dto.zustaendigkeit(), ressort.getZustaendigkeit());
        assertEquals(dto.ressortleitung().id(), ressort.getRessortleitung().getId());
    }
}
