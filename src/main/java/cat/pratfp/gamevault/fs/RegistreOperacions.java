package cat.pratfp.gamevault.fs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import cat.pratfp.gamevault.fs.RegistreOperacions;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Gestor del registre d'operacions (log) de GameVault.
 */
public class RegistreOperacions {

    private final Path fitxerLog;
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public RegistreOperacions(Path fitxerLog) {
        this.fitxerLog = fitxerLog;
    }

    /**
     * Registra un missatge al fitxer de log amb la data i hora actuals.
     *
     * @param missatge El text de l'operació a registrar.
     * @throws IOException Si hi ha un error d'entrada/sortida.
     */
    public void registra(String missatge) throws IOException {
        String linia = LocalDateTime.now().format(FORMAT) + " | " + missatge + System.lineSeparator();
        Files.writeString(fitxerLog, linia, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}