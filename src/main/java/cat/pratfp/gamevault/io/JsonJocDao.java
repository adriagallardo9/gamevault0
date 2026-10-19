package cat.pratfp.gamevault.io;

import cat.pratfp.gamevault.model.Joc;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Lectura i escriptura del catàleg en JSON amb Jackson.
 */
public class JsonJocDao {

    private final ObjectMapper mapper = creaMapper();

    /*
     * TODO PR1-08 (1a part) · Configurar l'ObjectMapper (CA 1.5)
     * Retorna un ObjectMapper que:
     *   - entengui LocalDate (registra el JavaTimeModule),
     *   - escrigui les dates com "2017-02-24" i no com [2017,2,24],
     *   - no peti si el JSON porta camps que Joc no té.
     * Pista: sessió 6, Ex4JsonAmbJackson.
     */
    private static ObjectMapper creaMapper() {
        // Escriu aquí el teu codi (PR1-08)
        return new ObjectMapper();
    }

    /*
     * TODO PR1-08 (2a part) · Escriure la llista en JSON (CA 1.4)
     * Escriu la llista sencera al fitxer, amb la sortida INDENTADA (una dada per línia).
     */
    public void escriu(Path p, List<Joc> jocs) throws IOException {
        // Escriu aquí el teu codi (PR1-08)
        throw new UnsupportedOperationException("PR1-08: encara no està fet");
    }

    /*
     * TODO PR1-09 · Llegir la llista des de JSON (CA 1.3)
     * Llegeix el fitxer i retorna una List<Joc>.
     * Compte: amb List.class Jackson no sap que els elements són Joc. Què feia servir l'exemple?
     */
    public List<Joc> llegeix(Path p) throws IOException {
        // Escriu aquí el teu codi (PR1-09)
        throw new UnsupportedOperationException("PR1-09: encara no està fet");
    }
}
