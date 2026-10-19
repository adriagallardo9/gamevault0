package cat.pratfp.gamevault.io;

import cat.pratfp.gamevault.model.Joc;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Índex d'hores jugades en un fitxer binari d'accés aleatori.
 * <p>
 * Cada registre fa sempre {@value #MIDA_REGISTRE} bytes:
 * <pre>
 *   id (int, 4 bytes) | títol (40 caràcters, 80 bytes) | hores (double, 8 bytes)
 * </pre>
 * El registre de la posició n comença al byte n × MIDA_REGISTRE.
 */
public class IndexHores {

    /** Caràcters reservats per al títol (s'omple amb espais o es talla). */
    public static final int LONG_TITOL = 40;
    /** Mida fixa de cada registre en bytes: 4 + 40 × 2 + 8 = 92. */
    public static final int MIDA_REGISTRE = Integer.BYTES + LONG_TITOL * Character.BYTES + Double.BYTES;

    private final Path fitxer;

    /**
     * @param fitxer fitxer binari de l'índex
     */
    public IndexHores(Path fitxer) {
        this.fitxer = fitxer;
    }

    /**
     * Escriu el registre d'un joc a la posició indicada.
     *
     * @param posicio número de registre (0, 1, 2…)
     * @param j       joc del qual es desen l'id, el títol i les hores
     * @throws IOException si no es pot escriure
     */
    public void escriu(int posicio, Joc j) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(fitxer.toFile(), "rw")) {
            raf.seek((long) posicio * MIDA_REGISTRE);
            raf.writeInt(j.id());
            String fix = String.format("%-" + LONG_TITOL + "s", j.titol()).substring(0, LONG_TITOL);
            raf.writeChars(fix);
            raf.writeDouble(j.horesJugades());
        }
    }

    /**
     * @return quants registres té el fitxer (0 si no existeix)
     * @throws IOException si no es pot consultar la mida
     */
    public long nombreRegistres() throws IOException {
        return Files.exists(fitxer) ? Files.size(fitxer) / MIDA_REGISTRE : 0;
    }

    /*
     * TODO PR1-12 · Llegir només les hores d'un registre (CA 1.2 i 1.3)
     * Obre el fitxer amb RandomAccessFile en mode lectura, fes seek directament al camp de les
     * hores del registre i llegeix el double.
     * Pensa-hi: quants bytes hi ha, dins del registre, ABANS del camp de les hores?
     * Si la posició és més enllà del final, readDouble llançarà una EOFException (és una IOException):
     * deixa-la sortir.
     * Pista: sessió 5, Ex5AccesAleatori.
     */
    public double llegeixHores(int posicio) throws IOException {
        // Escriu aquí el teu codi (PR1-12)
        throw new UnsupportedOperationException("PR1-12: encara no està fet");
    }

    /*
     * TODO PR1-13 · Sumar hores sense reescriure el fitxer (CA 1.2)
     * Suma "extra" hores al registre de la posició indicada:
     *   1. obre el fitxer en mode "rw",
     *   2. seek al camp de les hores i llegeix el valor actual,
     *   3. torna a fer seek AL MATEIX LLOC (llegir ha avançat el punter 8 bytes),
     *   4. escriu el valor nou.
     * Cap altre byte del fitxer pot canviar i la mida del fitxer ha de quedar igual.
     * Retorna les hores noves.
     */
    public double sumaHores(int posicio, double extra) throws IOException {
        // Escriu aquí el teu codi (PR1-13)
        throw new UnsupportedOperationException("PR1-13: encara no està fet");
    }
}
