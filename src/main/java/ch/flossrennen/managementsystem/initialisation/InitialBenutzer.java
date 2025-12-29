package ch.flossrennen.managementsystem.initialisation;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.BenutzerRepository;
import ch.flossrennen.managementsystem.initialisation.constants.InitialDataConstants;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "test"})
@Order(1)
public class InitialBenutzer implements CommandLineRunner {

    private final BenutzerRepository benutzerRepository;
    private final PasswordEncoder passwordEncoder;

    public InitialBenutzer(BenutzerRepository benutzerRepository, PasswordEncoder passwordEncoder) {
        this.benutzerRepository = benutzerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = InitialDataConstants.ADMIN_EMAIL;

        benutzerRepository.findByEmail(adminEmail).orElseGet(() ->
                benutzerRepository.save(new Benutzer(
                        null,
                        InitialDataConstants.ADMIN_VORNAME,
                        InitialDataConstants.ADMIN_NACHNAME,
                        InitialDataConstants.ADMIN_TELEFONNUMMER,
                        adminEmail,
                        passwordEncoder.encode(InitialDataConstants.ADMIN_PASSWORD),
                        InitialDataConstants.ADMIN_ROLLE
                ))
        );
    }
}
