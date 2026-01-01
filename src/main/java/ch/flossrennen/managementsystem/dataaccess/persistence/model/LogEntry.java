package ch.flossrennen.managementsystem.dataaccess.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = LogSchema.TABLE_NAME)
public class LogEntry implements LogSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = TIMESTAMP, nullable = false)
    private LocalDateTime timestamp;

    @Column(name = TYPE, nullable = false, length = 50)
    private String type;

    @Column(name = LOG_LEVEL, nullable = false, length = 20)
    private String logLevel;

    @Column(name = BENUTZER)
    private String benutzer;

    @Column(name = MESSAGE, nullable = false, columnDefinition = "TEXT")
    private String message;
}
