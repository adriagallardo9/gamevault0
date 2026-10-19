package cat.pratfp.gamevault.fs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MagatzemTest {

    @TempDir
    Path tmp;

    @Test
    @DisplayName("PR1-02 · crea import, export i backup i es pot cridar dues vegades")
    void preparaEstructura() throws Exception {
        Magatzem m = new Magatzem(tmp.resolve("dades"));
        m.preparaEstructura();
        assertTrue(Files.isDirectory(m.importacio()));
        assertTrue(Files.isDirectory(m.exportacio()));
        assertTrue(Files.isDirectory(m.copies()));
        assertDoesNotThrow(m::preparaEstructura, "la segona crida no pot fallar");
    }

    @Test
    @DisplayName("PR1-03 · la còpia va a backup amb nom_marca.ext i l'original es manté")
    void copiaSeguretat() throws Exception {
        Magatzem m = new Magatzem(tmp);
        m.preparaEstructura();
        Path original = tmp.resolve("catalog.csv");
        Files.writeString(original, "id;titol\n1;Gris\n");

        Path copia = m.copiaSeguretat(original);

        assertEquals(m.copies(), copia.getParent(), "la còpia ha d'anar a la carpeta backup");
        assertTrue(copia.getFileName().toString().matches("catalog_\\d{8}-\\d{6}\\.csv"),
                "nom inesperat: " + copia.getFileName());
        assertEquals(Files.readString(original), Files.readString(copia));
        assertTrue(Files.exists(original), "l'original no es pot moure ni esborrar");
    }

    @Test
    @DisplayName("PR1-04 · suma les mides de tots els fitxers, també dins de subcarpetes")
    void midaTotal() throws Exception {
        Magatzem m = new Magatzem(tmp);
        m.preparaEstructura();
        Files.write(tmp.resolve("a.txt"), new byte[100]);
        Files.write(m.exportacio().resolve("b.bin"), new byte[250]);
        Files.createDirectories(m.copies().resolve("vell"));
        Files.write(m.copies().resolve("vell").resolve("c.csv"), new byte[50]);

        assertEquals(400, m.midaTotal());
    }

    static class RegistreOperacionsTest {

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
}
