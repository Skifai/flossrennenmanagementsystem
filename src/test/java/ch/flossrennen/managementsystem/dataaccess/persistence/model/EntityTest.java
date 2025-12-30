package ch.flossrennen.managementsystem.dataaccess.persistence.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void testBenutzer() {
        Benutzer b = new Benutzer();
        b.setId(1L);
        b.setVorname("John");
        b.setNachname("Doe");
        b.setEmail("john@example.com");
        b.setTelefonnummer("123");
        b.setPasswordhash("hash");
        b.setRolle(BenutzerRolle.ADMINISTRATOR);

        assertEquals(1L, b.getId());
        assertEquals("John", b.getVorname());
        assertEquals("Doe", b.getNachname());
        assertEquals("john@example.com", b.getEmail());
        assertEquals("123", b.getTelefonnummer());
        assertEquals("hash", b.getPasswordhash());
        assertEquals(BenutzerRolle.ADMINISTRATOR, b.getRolle());
    }

    @Test
    void testRessort() {
        Ressort r = new Ressort();
        r.setId(1L);
        r.setName("Ressort");
        r.setBeschreibung("Desc");
        r.setZustaendigkeit("Zust");
        Benutzer b = new Benutzer();
        r.setRessortleitung(b);

        assertEquals(1L, r.getId());
        assertEquals("Ressort", r.getName());
        assertEquals("Desc", r.getBeschreibung());
        assertEquals("Zust", r.getZustaendigkeit());
        assertEquals(b, r.getRessortleitung());
    }

    @Test
    void testHelfer() {
        Helfer h = new Helfer();
        h.setId(1L);
        h.setVorname("John");
        h.setNachname("Doe");
        h.setEmail("john@example.com");
        h.setTelefonnummer("123");
        Ressort r = new Ressort();
        h.setRessort(r);

        assertEquals(1L, h.getId());
        assertEquals("John", h.getVorname());
        assertEquals("Doe", h.getNachname());
        assertEquals("john@example.com", h.getEmail());
        assertEquals("123", h.getTelefonnummer());
        assertEquals(r, h.getRessort());
        assertFalse(h.isNew());

        h.setId(null);
        assertTrue(h.isNew());
    }
}
