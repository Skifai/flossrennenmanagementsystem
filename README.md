# Flossrennen Management System

Das Flossrennen Management System ist eine webbasierte Anwendung zur effizienten Verwaltung von Helfern und deren
Einsätzen während des Flossrennens.

> [!IMPORTANT]
> **Hinweis zur Anzeige**: Das Projekt **nicht auf einem Laptop-Bildschirm** (bzw. kleinen Bildschirmen) geöffnet
werden. Eine hohe Bildschirmauflösung wird empfohlen.

## Voraussetzungen

Bevor Sie das Projekt starten, stellen Sie sicher, dass die folgenden Komponenten auf Ihrem System installiert sind:

* **Java 21** oder höher
* **Docker** (für die Datenbank-Container)
* **Maven** (oder Verwendung des mitgelieferten `mvnw` Wrappers)

## Projekt-Setup

1. Klonen Sie das Repository:
   ```bash
   git clone <repository-url>
   cd managementsystem
   ```
2. Stellen Sie sicher, dass Docker läuft.
3. Bauen Sie das Projekt und laden Sie alle Abhängigkeiten herunter:
   ```bash
   ./mvnw clean install
   ```

## Anwendung ausführen

Für alle Profile / Modi wird auch eine IntelliJ IDEA RunConfig mitgeliefert, welche genutzt werden kann, anstatt der
Commands.

### Entwicklungsmodus

Starten Sie die Anwendung mit dem `dev`-Profil, um die Entwicklungsdatenbank zu nutzen:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Durch das `dev`-Profil wird automatisch ein PostgreSQL-Container gestartet (definiert in `docker-compose-dev.yaml`).

### Datenbank-Management

* **Docker Compose**: Die Anwendung nutzt die Spring Boot Docker Compose Unterstützung.
* **Migrationen**: Datenbankänderungen werden mit Flyway verwaltet. Neue Migrationsskripte gehören nach
  `src/main/resources/db/migration`.

## Tests

Um alle Tests auszuführen und dabei die Spring-Kontext-Caching-Vorteile zu nutzen, verwenden Sie folgenden Befehl:

```bash
./mvnw test
```

### Test-Umgebung

Tests verwenden das `test`-Profil. Dies startet einen separaten PostgreSQL-Container über `docker-compose-test.yaml`.
Integrationstests sollten immer mit `@ActiveProfiles("test")` annotiert sein.

## Architektur & Technologien

* **Backend**: Spring Boot 3.x
* **Frontend**: Vaadin 24 (Komponentenbasierte UI-Entwicklung in Java)
* **Datenzugriff**: Spring Data JPA mit PostgreSQL
* **Mapping**: MapStruct für DTO/Entity-Konvertierung
* **Boilerplate-Reduktion**: Project Lombok
* **Internationalisierung (i18n)**: Texte sind in `src/main/resources/translation/texts_de_CH.properties` definiert und
  werden über den `TextProvider` abgerufen.

### Verzeichnisstruktur

* `ch.flossrennen.managementsystem.view`: Vaadin UI-Views
* `ch.flossrennen.managementsystem.service`: Geschäftslogik
* `ch.flossrennen.managementsystem.dataaccess`: Repositories und DTOs
* `src/main/resources/db/migration`: Flyway Migrationsdateien
