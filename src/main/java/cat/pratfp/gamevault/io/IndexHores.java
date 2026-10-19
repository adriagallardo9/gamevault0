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
    public static final int MIDA_REGISTRE = Integer.BYTES + (LONG_TITOL * Character.BYTES) + Double.BYTES;

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

    /**
     * PR1-12 · Llegir només les hores d'un registre (CA 1.2 i 1.3)
     */
    public double llegeixHores(int posicio) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(fitxer.toFile(), "r")) {
            long offset = (long) posicio * MIDA_REGISTRE + Integer.BYTES + (LONG_TITOL * Character.BYTES);
            raf.seek(offset);
            return raf.readDouble();
        }
    }

    /**
     * PR1-13 · Sumar hores sense reescriure el fitxer (CA 1.2)
     */
    public double sumaHores(int posicio, double extra) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(fitxer.toFile(), "rw")) {
            long offset = (long) posicio * MIDA_REGISTRE + Integer.BYTES + (LONG_TITOL * Character.BYTES);
            raf.seek(offset);
            double actual = raf.readDouble();
            double nou = actual + extra;
            raf.seek(offset);
            raf.writeDouble(nou);
            return nou;
        }
    }
}