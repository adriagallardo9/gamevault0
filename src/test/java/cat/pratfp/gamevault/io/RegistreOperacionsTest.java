package cat.pratfp.gamevault.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistreOperacionsTest {

    @TempDir
    Path tmp;

    @Test
    @DisplayName("PR1-05 · crea el fitxer si no existeix i afegeix sense esborrar")
    void afegeixSenseEsborrar() throws Exception {
        Path log = tmp.resolve("operacions.log");
        RegistreOperacions r = new RegistreOperacions(log);
        r.registra("primera operació");
        r.registra("segona operació");

        List<String> linies = Files.readAllLines(log, StandardCharsets.UTF_8);
        assertEquals(2, linies.size(), "hi ha d'haver dues línies: " + linies);
        assertTrue(linies.get(0).endsWith("| primera operació"));
        assertTrue(linies.get(1).endsWith("| segona operació"));
    }

    @Test
    @DisplayName("PR1-05 · cada línia comença amb la data i hora en format yyyy-MM-dd HH:mm:ss")
    void formatDeLinia() throws Exception {
        Path log = tmp.resolve("operacions.log");
        new RegistreOperacions(log).registra("còpia de seguretat");
        String linia = Files.readAllLines(log, StandardCharsets.UTF_8).get(0);
        assertTrue(linia.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} \\| còpia de seguretat"),
                "línia inesperada: " + linia);
    }
}
