package cat.pratfp.gamevault.servei;

import cat.pratfp.gamevault.io.CsvJocDao;
import cat.pratfp.gamevault.io.JsonJocDao;
import cat.pratfp.gamevault.model.Joc;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

/**
 * Convertidor de formats: un sol model intern (List<Joc>) i un DAO per format.
 * Convertir és llegir amb un DAO i escriure amb un altre.
 */
public class Convertidor {

    private final CsvJocDao csv = new CsvJocDao();
    private final JsonJocDao json = new JsonJocDao();

    /**
     * Carrega els jocs d'un fitxer triant el DAO per l'extensió (.csv o .json).
     *
     * @param origen fitxer d'entrada
     * @return els jocs llegits
     * @throws CatalegException si el format no és suportat o el fitxer no es pot llegir
     */
    public List<Joc> carrega(Path origen) throws CatalegException {
        try {
            return switch (extensio(origen)) {
                case "csv" -> csv.llegeix(origen).jocs();
                case "json" -> json.llegeix(origen);
                default -> throw new CatalegException("Format no suportat: " + origen.getFileName());
            };
        } catch (IOException e) {
            throw new CatalegException("No s'ha pogut llegir " + origen.getFileName() + ": " + e.getMessage(), e);
        }
    }

    /**
     * Desa els jocs en un fitxer triant el DAO per l'extensió (.csv o .json). (PR1-10)
     */
    public void desa(Path desti, List<Joc> jocs) throws CatalegException {
        try {
            switch (extensio(desti)) {
                case "csv" -> csv.escriu(desti, jocs);
                case "json" -> json.escriu(desti, jocs);
                default -> throw new CatalegException("Format no suportat: " + desti.getFileName());
            }
        } catch (IOException e) {
            throw new CatalegException("No s'ha pogut escriure " + desti.getFileName() + ": " + e.getMessage(), e);
        }
    }

    /**
     * Converteix un fitxer d'un format a un altre. (PR1-10)
     *
     * @param origen fitxer d'entrada (.csv o .json)
     * @param desti  fitxer de sortida (.csv o .json)
     * @return el nombre de jocs convertits
     * @throws CatalegException si algun dels dos formats no és suportat o hi ha un error d'E/S
     */
    public int converteix(Path origen, Path desti) throws CatalegException {
        List<Joc> jocs = carrega(origen);
        desa(desti, jocs);
        return jocs.size();
    }

    /** Extensió del fitxer en minúscules i sense el punt ("" si no en té). */
    private static String extensio(Path p) {
        String nom = p.getFileName().toString();
        int punt = nom.lastIndexOf('.');
        return punt < 0 ? "" : nom.substring(punt + 1).toLowerCase(Locale.ROOT);
    }
}