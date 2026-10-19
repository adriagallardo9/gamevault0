package cat.pratfp.gamevault.io;

import cat.pratfp.gamevault.Dades;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CauCatalegTest {

    @TempDir
    Path tmp;

    private final CauCataleg cau = new CauCataleg();

    @Test
    @DisplayName("PR1-11 · desa i carrega la llista sencera")
    void desaICarrega() throws Exception {
        Path ser = tmp.resolve("cataleg.ser");
        cau.desa(ser, Dades.cataleg());
        assertEquals(Dades.cataleg(), cau.carrega(ser));
    }

    @Test
    @DisplayName("PR1-11 · el fitxer és una serialització Java (comença per AC ED)")
    void esSerialitzacioJava() throws Exception {
        Path ser = tmp.resolve("cataleg.ser");
        cau.desa(ser, Dades.cataleg());
        byte[] b = Files.readAllBytes(ser);
        assertEquals((byte) 0xAC, b[0]);
        assertEquals((byte) 0xED, b[1]);
    }

    @Test
    @DisplayName("PR1-11 · un fitxer que no és una cau dona IOException, no un error inesperat")
    void fitxerCorromput() throws Exception {
        Path ser = tmp.resolve("brossa.ser");
        Files.writeString(ser, "això no és una serialització");
        assertThrows(IOException.class, () -> cau.carrega(ser));
    }
}
