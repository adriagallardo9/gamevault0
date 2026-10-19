package cat.pratfp.gamevault;

import cat.pratfp.gamevault.model.Joc;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProvesPropiesTest {

    @Test
    void primeraProva() {
        Joc joc = new Joc(1, "Hollow Knight", "PC", "Team Cherry", LocalDate.of(2017, 2, 24), 42.5, 9.5);
        assertNotNull(joc);
        assertEquals("Hollow Knight", joc.titol());
    }
}