package ch.flossrennen.managementsystem.dataaccess.mapper;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MapperHelper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MapperHelper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Named("passwordToHash")
    public String passwordToHash(String password) {
        if (password != null && !password.isBlank()) {
            return passwordEncoder.encode(password);
        }
        return null;
    }
}