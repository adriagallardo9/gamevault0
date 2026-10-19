package cat.pratfp.gamevault.io;

import cat.pratfp.gamevault.Dades;
import cat.pratfp.gamevault.model.Joc;
import cat.pratfp.gamevault.model.ResultatCarrega;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvJocDaoTest {

    @TempDir
    Path tmp;

    private final CsvJocDao dao = new CsvJocDao();
    private Path brut;

    @BeforeEach
    void preparaBrut() throws Exception {
        brut = tmp.resolve("catalog_brut.csv");
        Files.writeString(brut, Dades.CSV_BRUT, StandardCharsets.UTF_8);
    }

    @Test
    @DisplayName("PR1-06 · del CSV brut només entren els jocs 1 i 5")
    void nomesEntrenElsValids() throws Exception {
        ResultatCarrega r = dao.llegeix(brut);
        assertEquals(List.of(1, 5), r.jocs().stream().map(Joc::id).toList());
        assertEquals("Baldur's Gate 3; Deluxe", r.jocs().get(1).titol(), "les cometes protegeixen el ;");
    }

    @Test
    @DisplayName("PR1-06 · quatre incidències, una per línia dolenta, amb el motiu")
    void incidencies() throws Exception {
        List<String> inc = dao.llegeix(brut).incidencies();
        assertEquals(4, inc.size(), "incidències: " + inc);
        assertTrue(inc.get(0).startsWith("línia 3") && inc.get(0).contains("nota"), inc.get(0));
        assertTrue(inc.get(1).startsWith("línia 4") && inc.get(1).contains("incompleta"), inc.get(1));
        assertTrue(inc.get(2).startsWith("línia 5") && inc.get(2).contains("data"), inc.get(2));
        assertTrue(inc.get(3).startsWith("línia 7") && inc.get(3).contains("hores"), inc.get(3));
    }

    @Test
    @DisplayName("PR1-07 · escriu la capçalera i una fila per joc")
    void escriuCapcalera() throws Exception {
        Path desti = tmp.resolve("sortida.csv");
        dao.escriu(desti, Dades.cataleg());
        List<String> linies = Files.readAllLines(desti, StandardCharsets.UTF_8);
        assertEquals(6, linies.size());
        assertEquals("id;titol;plataforma;estudi;dataSortida;horesJugades;nota", linies.get(0));
        assertEquals("1;Hollow Knight;PC;Team Cherry;2017-02-24;42.5;9.5", linies.get(1));
        assertTrue(linies.get(5).contains("\"Baldur's Gate 3; Deluxe\""), "el títol amb ; ha d'anar entre cometes");
    }

    @Test
    @DisplayName("PR1-07 · anada i tornada: el que s'escriu es torna a llegir igual")
    void anadaITornada() throws Exception {
        Path desti = tmp.resolve("sortida.csv");
        dao.escriu(desti, Dades.cataleg());
        ResultatCarrega r = dao.llegeix(desti);
        assertEquals(Dades.cataleg(), r.jocs());
        assertTrue(r.incidencies().isEmpty());
    }
}
