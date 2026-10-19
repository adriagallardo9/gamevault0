package cat.pratfp.gamevault.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JocTest {

    private static final LocalDate DATA = LocalDate.of(2017, 2, 24);

    @Test
    @DisplayName("PR1-01 · un joc correcte es crea, també amb els valors límit")
    void jocCorrecte() {
        assertDoesNotThrow(() -> new Joc(1, "Hollow Knight", "PC", "Team Cherry", DATA, 42.5, 9.5));
        assertDoesNotThrow(() -> new Joc(2, "Gris", "PC", "Nomada", DATA, 0.0, 0.0));
        assertDoesNotThrow(() -> new Joc(3, "Celeste", "PC", "Maddy", DATA, 1.0, 10.0));
    }

    @Test
    @DisplayName("PR1-01 · títol buit -> IllegalArgumentException amb «títol»")
    void titolBuit() {
        var e = assertThrows(IllegalArgumentException.class,
                () -> new Joc(1, "   ", "PC", "Team Cherry", DATA, 1.0, 5.0));
        assertTrue(e.getMessage().contains("títol"), "missatge: " + e.getMessage());
        assertThrows(IllegalArgumentException.class, () -> new Joc(1, null, "PC", "Team Cherry", DATA, 1.0, 5.0));
    }

    @Test
    @DisplayName("PR1-01 · hores negatives -> IllegalArgumentException amb «hores»")
    void horesNegatives() {
        var e = assertThrows(IllegalArgumentException.class,
                () -> new Joc(1, "Gris", "PC", "Nomada", DATA, -0.5, 5.0));
        assertTrue(e.getMessage().contains("hores"), "missatge: " + e.getMessage());
    }

    @Test
    @DisplayName("PR1-01 · nota fora de 0-10 -> IllegalArgumentException amb «nota»")
    void notaForaDeRang() {
        var e = assertThrows(IllegalArgumentException.class,
                () -> new Joc(1, "Gris", "PC", "Nomada", DATA, 1.0, 10.5));
        assertTrue(e.getMessage().contains("nota"), "missatge: " + e.getMessage());
        assertThrows(IllegalArgumentException.class, () -> new Joc(1, "Gris", "PC", "Nomada", DATA, 1.0, -1));
    }
}
