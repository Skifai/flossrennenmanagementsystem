package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
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
        Benutzer benutzer = new Benutzer(2L, "Hans", "Muster", "0791234567", "hans.muster@test.ch", "hash", BenutzerRolle.RESSORTLEITER);

        Ressort ressort = new Ressort(1L, "Sicherheit", "Sicherheit auf dem Fluss", "Rettung", benutzer);
        RessortDTO dto = mapper.toDTO(ressort);

        assertEquals(ressort.getId(), dto.id());
        assertEquals(ressort.getName(), dto.name());
        assertEquals(ressort.getBeschreibung(), dto.beschreibung());
        assertEquals(ressort.getZustaendigkeit(), dto.zustaendigkeit());
        assertEquals(2L, dto.ressortleitung().id());
    }

    @Test
    void toEntity() {
        BenutzerDTO ressortleitung = new BenutzerDTO(2L, "Hans", "Muster", "0791234567", "hans.muster@test.ch", "hash", BenutzerRolle.RESSORTLEITER);
        RessortDTO dto = new RessortDTO(1L, "Sicherheit", "Sicherheit auf dem Fluss", "Rettung", ressortleitung);
        Ressort ressort = mapper.toEntity(dto);

        assertEquals(dto.id(), ressort.getId());
        assertEquals(dto.name(), ressort.getName());
        assertEquals(dto.beschreibung(), ressort.getBeschreibung());
        assertEquals(dto.zustaendigkeit(), ressort.getZustaendigkeit());
        assertEquals(dto.ressortleitung().id(), ressort.getRessortleitung().getId());
    }
}
