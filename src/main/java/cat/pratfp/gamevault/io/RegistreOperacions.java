package cat.pratfp.gamevault.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Registre de les operacions de GameVault en un fitxer de text (operacions.log).
 */
public class RegistreOperacions {

    /** Format de la data i hora de cada línia: 2026-10-13 15:10:02 */
    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Path fitxer;

    /**
     * @param fitxer fitxer de registre; si no existeix, es crea en la primera escriptura
     */
    public RegistreOperacions(Path fitxer) {
        this.fitxer = fitxer;
    }

    /*
     * TODO PR1-05 · Escriure al registre sense esborrar-lo (CA 1.4)
     * Afegeix UNA línia al final del fitxer amb el format:
     *     2026-10-13 15:10:02 | <text>
     * - La data i hora: LocalDateTime.now().format(FORMAT)
     * - Fes servir un BufferedWriter dins d'un try-with-resources.
     * - Codificació UTF-8 explícita.
     * - Si el fitxer no existeix s'ha de crear; si existeix, NO s'ha de buidar.
     * Pista: sessió 5, Ex1FluxosDeText (StandardOpenOption).
     */
    public void registra(String text) throws IOException {
        // Escriu aquí el teu codi (PR1-05)
        throw new UnsupportedOperationException("PR1-05: encara no està fet");
    }
}
