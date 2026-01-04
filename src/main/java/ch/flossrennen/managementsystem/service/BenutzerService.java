package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.BenutzerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service für die Benutzerauthentifizierung und -verwaltung.
 * Implementiert UserDetailsService für Spring Security.
 */
@Service
public class BenutzerService implements UserDetailsService {

    private final BenutzerRepository benutzerRepository;

    /**
     * Erstellt einen neuen BenutzerService.
     *
     * @param benutzerRepository Das Repository für den Zugriff auf Benutzerdaten.
     */
    public BenutzerService(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    /**
     * Lädt die Benutzerdetails anhand der E-Mail-Adresse (Benutzername).
     *
     * @param email Die E-Mail-Adresse des Benutzers.
     * @return Die UserDetails des Benutzers.
     * @throws UsernameNotFoundException Wenn der Benutzer nicht gefunden wurde.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Benutzer benutzer = benutzerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Benutzer nicht gefunden: " + email));

        return User.withUsername(benutzer.getEmail())
                .password(benutzer.getPasswordhash())
                .roles(benutzer.getRolle().name())
                .build();
    }
}
