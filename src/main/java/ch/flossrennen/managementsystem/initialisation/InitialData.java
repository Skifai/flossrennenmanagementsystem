package ch.flossrennen.managementsystem.initialisation;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.BenutzerRepository;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.HelferRepository;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.RessortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dev")
@Order(2)
@RequiredArgsConstructor
public class InitialData implements CommandLineRunner {

    private final BenutzerRepository benutzerRepository;
    private final RessortRepository ressortRepository;
    private final HelferRepository helferRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (benutzerRepository.count() > 1 || ressortRepository.count() > 0 || helferRepository.count() > 0) {
            return;
        }

        String[] vornamen = {"Lukas", "Sarah", "Marco", "Elena", "Thomas"};
        String[] nachnamen = {"Müller", "Schmidt", "Schneider", "Fischer", "Weber"};
        String[] ressortNamen = {"Logistik", "Sicherheit", "Verpflegung", "Infrastruktur", "Kommunikation"};
        String[] ressortBeschreibungen = {
                "Planung und Durchführung der Transportlogistik für das Flossrennen.",
                "Gewährleistung der Sicherheit für Teilnehmer und Zuschauer.",
                "Organisation der Verpflegungsstände und Helfermahlzeiten.",
                "Aufbau und Unterhalt der Start- und Zielgelände.",
                "Betreuung der Social Media Kanäle und Pressearbeit."
        };
        String[] ressortZustaendigkeiten = {
                "Fahrzeuge, Absperrungen, Transporte",
                "Sanität, Security, Wasserrettung",
                "Catering, Getränke, Standpersonal",
                "Zelte, Elektrizität, Sanitäranlagen",
                "Webseite, Facebook, Instagram, lokale Presse"
        };

        List<Benutzer> benutzerList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String email = vornamen[i].toLowerCase() + "." + nachnamen[i].toLowerCase() + "@flossrennen-test.ch";
            Benutzer benutzer = new Benutzer(
                    null,
                    vornamen[i],
                    nachnamen[i],
                    "079 123 45 0" + (i + 1),
                    email,
                    passwordEncoder.encode("password" + (i + 1)),
                    BenutzerRolle.RESSORTLEITER
            );
            benutzerList.add(benutzerRepository.save(benutzer));
        }

        List<Ressort> ressortList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Ressort ressort = new Ressort(
                    null,
                    ressortNamen[i],
                    ressortBeschreibungen[i],
                    ressortZustaendigkeiten[i],
                    benutzerList.get(i)
            );
            ressortList.add(ressortRepository.save(ressort));
        }

        String[] helferVornamen = {
                "Anna", "Beat", "Christian", "Daniela", "Erik",
                "Fabienne", "Gabriel", "Hanna", "Ivan", "Julia",
                "Kevin", "Laura", "Marcel", "Nadine", "Oliver"
        };
        String[] helferNachnamen = {
                "Keller", "Meier", "Huber", "Frei", "Widmer",
                "Graf", "Baumann", "Sutter", "Ziegler", "Brun",
                "Vogt", "Bieri", "Arm", "Lutz", "Stucki"
        };

        for (int i = 0; i < 15; i++) {
            String email = helferVornamen[i].toLowerCase() + "." + helferNachnamen[i].toLowerCase() + "@example.ch";
            Helfer helfer = new Helfer(
                    null,
                    helferVornamen[i],
                    helferNachnamen[i],
                    email,
                    "078 987 65 " + (i < 10 ? "0" + i : i),
                    ressortList.get(i % 5)
            );
            helferRepository.save(helfer);
        }
    }
}
