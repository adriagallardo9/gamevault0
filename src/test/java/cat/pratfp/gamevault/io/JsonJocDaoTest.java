package cat.pratfp.gamevault.io;

import cat.pratfp.gamevault.Dades;
import cat.pratfp.gamevault.model.Joc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonJocDaoTest {

    @TempDir
    Path tmp;

    private final JsonJocDao dao = new JsonJocDao();

    @Test
    @DisplayName("PR1-08 · el JSON surt indentat i amb les dates en format ISO")
    void escriuIndentatIDatesIso() throws Exception {
        Path p = tmp.resolve("jocs.json");
        dao.escriu(p, Dades.cataleg());
        String text = Files.readString(p, StandardCharsets.UTF_8);
        assertTrue(text.contains("\"dataSortida\" : \"2017-02-24\""), "la data ha de ser \"2017-02-24\"");
        assertTrue(text.lines().count() > 10, "la sortida ha d'estar indentada");
    }

    @Test
    @DisplayName("PR1-09 · anada i tornada JSON sense perdre res")
    void anadaITornada() throws Exception {
        Path p = tmp.resolve("jocs.json");
        dao.escriu(p, Dades.cataleg());
        List<Joc> llegits = dao.llegeix(p);
        assertEquals(Dades.cataleg(), llegits);
        assertInstanceOf(Joc.class, llegits.get(0), "els elements han de ser Joc, no mapes");
    }

    @Test
    @DisplayName("PR1-09 · un JSON amb camps de més es llegeix igualment")
    void campsDeMes() throws Exception {
        Path p = tmp.resolve("amb_preu.json");
        Files.writeString(p, """
                [ { "id": 4, "titol": "Gris", "plataforma": "PC", "estudi": "Nomada Studio",
                    "dataSortida": "2018-12-13", "horesJugades": 4.0, "nota": 8.0, "preu": 16.99 } ]
                """, StandardCharsets.UTF_8);
        List<Joc> llegits = dao.llegeix(p);
        assertEquals("Gris", llegits.get(0).titol());
    }
}
