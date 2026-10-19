package cat.pratfp.gamevault.io;

import cat.pratfp.gamevault.Dades;
import cat.pratfp.gamevault.model.Joc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IndexHoresTest {

    @TempDir
    Path tmp;

    private Path dat;
    private IndexHores index;

    @BeforeEach
    void escriuCinc() throws Exception {
        dat = tmp.resolve("hores.dat");
        index = new IndexHores(dat);
        List<Joc> jocs = Dades.cataleg();
        for (int i = 0; i < jocs.size(); i++) {
            index.escriu(i, jocs.get(i));
        }
    }

    @Test
    @DisplayName("PR1-12 · llegeix les hores de qualsevol registre")
    void llegeixHores() throws Exception {
        assertEquals(42.5, index.llegeixHores(0));
        assertEquals(12.5, index.llegeixHores(2));
        assertEquals(120.0, index.llegeixHores(4));
    }

    @Test
    @DisplayName("PR1-12 · una posició més enllà del final dona IOException")
    void posicioForaDeRang() {
        assertThrows(IOException.class, () -> index.llegeixHores(9));
    }

    @Test
    @DisplayName("PR1-13 · suma hores i només canvien els 8 bytes del camp")
    void sumaNomesElCamp() throws Exception {
        byte[] abans = Files.readAllBytes(dat);

        assertEquals(15.5, index.sumaHores(2, 3.0));
        assertEquals(15.5, index.llegeixHores(2));

        byte[] despres = Files.readAllBytes(dat);
        assertEquals(abans.length, despres.length, "la mida del fitxer no pot canviar");
        int iniciCamp = 2 * IndexHores.MIDA_REGISTRE + 84;
        for (int i = 0; i < abans.length; i++) {
            if (i < iniciCamp || i >= iniciCamp + 8) {
                assertEquals(abans[i], despres[i], "el byte " + i + " no havia de canviar");
            }
        }
        assertFalse(Arrays.equals(abans, despres));
    }
}
