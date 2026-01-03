package ch.flossrennen.managementsystem.initialisation;

import ch.flossrennen.managementsystem.dataaccess.BenutzerDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.initialisation.constants.InitialDataConstants;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
//@Profile({"dev", "test"})
@Order(1)
public class InitialBenutzer implements CommandLineRunner {

    private final BenutzerDTODataAccess benutzerDTODataAccess;

    public InitialBenutzer(BenutzerDTODataAccess benutzerDTODataAccess) {
        this.benutzerDTODataAccess = benutzerDTODataAccess;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = InitialDataConstants.ADMIN_EMAIL;

        if (benutzerDTODataAccess.findByEmail(adminEmail).isEmpty()) {
            benutzerDTODataAccess.save(new BenutzerDTO(
                    null,
                    InitialDataConstants.ADMIN_VORNAME,
                    InitialDataConstants.ADMIN_NACHNAME,
                    InitialDataConstants.ADMIN_TELEFONNUMMER,
                    adminEmail,
                    InitialDataConstants.ADMIN_PASSWORD,
                    InitialDataConstants.ADMIN_ROLLE
            ));
        }
    }
}
