package cat.pratfp.gamevault.servei;

import cat.pratfp.gamevault.Dades;
import cat.pratfp.gamevault.io.CsvJocDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class ConvertidorTest {

    @TempDir
    Path tmp;

    private final Convertidor conv = new Convertidor();

    @Test
    @DisplayName("PR1-10 · CSV -> JSON -> CSV conserva totes les dades")
    void cadenaCompleta() throws Exception {
        Path csv = tmp.resolve("cataleg.csv");
        new CsvJocDao().escriu(csv, Dades.cataleg());

        assertEquals(5, conv.converteix(csv, tmp.resolve("jocs.json")));
        assertEquals(5, conv.converteix(tmp.resolve("jocs.json"), tmp.resolve("tornada.csv")));
        assertEquals(Dades.cataleg(), conv.carrega(tmp.resolve("tornada.csv")));
    }

    @Test
    @DisplayName("PR1-10 · una extensió no suportada dona CatalegException amb missatge clar")
    void extensioNoSuportada() throws Exception {
        Path csv = tmp.resolve("cataleg.csv");
        new CsvJocDao().escriu(csv, Dades.cataleg());
        var e = assertThrows(CatalegException.class, () -> conv.converteix(csv, tmp.resolve("jocs.txt")));
        assertTrue(e.getMessage().toLowerCase(Locale.ROOT).contains("no suportat"), e.getMessage());
    }

    @Test
    @DisplayName("PR1-10 · un error d'escriptura es converteix en CatalegException amb la causa")
    void errorDEscriptura() throws Exception {
        Path csv = tmp.resolve("cataleg.csv");
        new CsvJocDao().escriu(csv, Dades.cataleg());
        Path carpetaQueNoExisteix = tmp.resolve("no").resolve("hi").resolve("es").resolve("jocs.json");
        var e = assertThrows(CatalegException.class, () -> conv.converteix(csv, carpetaQueNoExisteix));
        assertNotNull(e.getCause(), "cal conservar l'excepció original com a causa");
    }
}
