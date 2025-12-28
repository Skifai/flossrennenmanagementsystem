package ch.flossrennen.managementsystem.security;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.BenutzerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialBenutzer implements CommandLineRunner {

    private final BenutzerRepository benutzerRepository;
    private final PasswordEncoder passwordEncoder;

    public InitialBenutzer(BenutzerRepository benutzerRepository, PasswordEncoder passwordEncoder) {
        this.benutzerRepository = benutzerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = System.getenv("ADMIN_EMAIL");
        String adminPassword = System.getenv("ADMIN_PASSWORD");

        if (adminEmail == null || adminEmail.isBlank() || adminPassword == null || adminPassword.isBlank()) {
            return;
        }

        benutzerRepository.findByEmail(adminEmail).orElseGet(() ->
                benutzerRepository.save(new Benutzer(
                        null,
                        "Admin",
                        "Admin",
                        "0000000000",
                        adminEmail,
                        passwordEncoder.encode(adminPassword),
                        BenutzerRolle.ADMINISTRATOR
                ))
        );
    }
}
