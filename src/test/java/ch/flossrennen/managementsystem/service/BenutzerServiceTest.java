package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.BenutzerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BenutzerServiceTest {

    @Mock
    private BenutzerRepository benutzerRepository;

    private BenutzerService benutzerService;

    @BeforeEach
    void setUp() {
        benutzerService = new BenutzerService(benutzerRepository);
    }

    @Test
    void loadUserByUsername_Success() {
        Benutzer benutzer = new Benutzer(1L, "John", "Doe", "123", "john@test.ch", "hash", BenutzerRolle.ADMINISTRATOR);
        when(benutzerRepository.findByEmail("john@test.ch")).thenReturn(Optional.of(benutzer));

        UserDetails userDetails = benutzerService.loadUserByUsername("john@test.ch");

        assertNotNull(userDetails);
        assertEquals(benutzer.getEmail(), userDetails.getUsername());
        assertEquals(benutzer.getPasswordhash(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRATOR")));
    }

    @Test
    void loadUserByUsername_NotFound_ThrowsException() {
        when(benutzerRepository.findByEmail("unknown@test.ch")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> benutzerService.loadUserByUsername("unknown@test.ch"));
    }
}