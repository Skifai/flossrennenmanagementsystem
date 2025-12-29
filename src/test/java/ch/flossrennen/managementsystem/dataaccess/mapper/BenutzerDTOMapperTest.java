package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.initialisation.constants.InitialDataConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BenutzerDTOMapperTest {

    private PasswordEncoder passwordEncoder;
    private BenutzerDTOMapper mapper;

    @BeforeEach
    void setUp() {
        passwordEncoder = mock(PasswordEncoder.class);
        when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> "hashed_" + invocation.getArgument(0));
        mapper = Mappers.getMapper(BenutzerDTOMapper.class);
        mapper.passwordEncoder = passwordEncoder;
    }

    @Test
    void toDTO() {
        Benutzer benutzer = new Benutzer(1L, InitialDataConstants.ADMIN_VORNAME, InitialDataConstants.ADMIN_NACHNAME, InitialDataConstants.ADMIN_TELEFONNUMMER, InitialDataConstants.ADMIN_EMAIL, "hash", InitialDataConstants.ADMIN_ROLLE);
        BenutzerDTO dto = mapper.toDTO(benutzer);

        assertEquals(benutzer.getId(), dto.id());
        assertEquals(benutzer.getVorname(), dto.vorname());
        assertEquals(benutzer.getNachname(), dto.nachname());
        assertEquals(benutzer.getTelefonnummer(), dto.telefonnummer());
        assertEquals(benutzer.getEmail(), dto.email());
        assertEquals("", dto.password());
        assertEquals(benutzer.getRolle(), dto.rolle());
    }

    @Test
    void toEntity() {
        BenutzerDTO dto = new BenutzerDTO(1L, InitialDataConstants.VERKEHR_USER_VORNAME, InitialDataConstants.VERKEHR_USER_NACHNAME, InitialDataConstants.VERKEHR_USER_TEL, InitialDataConstants.VERKEHR_USER_EMAIL, "password123", InitialDataConstants.ADMIN_ROLLE);
        Benutzer benutzer = mapper.toEntity(dto);

        assertEquals(dto.id(), benutzer.getId());
        assertEquals(dto.vorname(), benutzer.getVorname());
        assertEquals(dto.nachname(), benutzer.getNachname());
        assertEquals(dto.telefonnummer(), benutzer.getTelefonnummer());
        assertEquals(dto.email(), benutzer.getEmail());
        assertEquals("hashed_password123", benutzer.getPasswordhash());
        assertEquals(dto.rolle(), benutzer.getRolle());
    }
}
