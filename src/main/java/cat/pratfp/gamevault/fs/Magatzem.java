package cat.pratfp.gamevault.fs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

/**
 * Gestor del magatzem de fitxers de GameVault.
 */
public class Magatzem {

    private final Path base;
    private static final DateTimeFormatter MARCA = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    public Magatzem(Path base) {
        this.base = base;
    }

    public Path base() {
        return base;
    }

    public Path importacio() {
        return base.resolve("import");
    }

    public Path exportacio() {
        return base.resolve("export");
    }

    public Path copies() {
        return base.resolve("backup");
    }

    public static String marcaDeTemps() {
        return LocalDateTime.now().format(MARCA);
    }

    /**
     * Prepara l'estructura de carpetes creant import, export i backup si no existeixen.
     *
     * @throws IOException Si hi ha un error en la creació dels directoris.
     */
    public void preparaEstructura() throws IOException {
        Files.createDirectories(importacio());
        Files.createDirectories(exportacio());
        Files.createDirectories(copies());
    }

    /**
     * Realitza una còpia de seguretat d'un fitxer al directori backup amb una marca de temps.
     *
     * @param origen La ruta del fitxer original a copiar.
     * @return La ruta Path de la còpia creada.
     * @throws IOException Si hi ha un error d'entrada/sortida.
     */
    public Path copiaSeguretat(Path origen) throws IOException {
        String nomOriginal = origen.getFileName().toString();
        int punt = nomOriginal.lastIndexOf('.');
        String nom = (punt == -1) ? nomOriginal : nomOriginal.substring(0, punt);
        String ext = (punt == -1) ? "" : nomOriginal.substring(punt);

        String nomDesti = nom + "_" + marcaDeTemps() + ext;
        Path desti = copies().resolve(nomDesti);

        return Files.copy(origen, desti, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Calcula la mida total en bytes de tots els fitxers del magatzem incloent subcarpetes.
     *
     * @return La suma de les mides en bytes.
     * @throws IOException Si hi ha un error d'entrada/sortida.
     */
    public long midaTotal() throws IOException {
        try (Stream<Path> stream = Files.walk(base)) {
            return stream.filter(Files::isRegularFile)
                    .mapToLong(p -> {
                        try {
                            return Files.size(p);
                        } catch (IOException e) {
                            return 0L;
                        }
                    }).sum();
        }
    }

    /**
     * Retorna una llista amb les rutes relatives de tots els fitxers del magatzem.
     *
     * @return Llista de rutes Path relatives a la base.
     * @throws IOException Si hi ha un error d'entrada/sortida.
     */
    public List<Path> inventari() throws IOException {
        try (Stream<Path> stream = Files.walk(base)) {
            return stream.filter(Files::isRegularFile)
                    .map(base::relativize)
                    .sorted()
                    .toList();
        }
    }
}