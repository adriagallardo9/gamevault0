package cat.pratfp.gamevault.io;

import cat.pratfp.gamevault.model.Joc;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Lectura i escriptura del catàleg en format JSON amb Jackson.
 */
public class JsonJocDao {

    private final ObjectMapper mapper;

    public JsonJocDao() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Llegeix el catàleg des d'un fitxer JSON.
     *
     * @param p ruta del fitxer JSON
     * @return Llista de jocs llegits
     * @throws IOException si hi ha un error de lectura
     */
    public List<Joc> llegeix(Path p) throws IOException {
        if (!Files.exists(p)) {
            return new ArrayList<>();
        }

        try (Reader reader = Files.newBufferedReader(p, StandardCharsets.UTF_8)) {
            List<Joc> carregats = mapper.readValue(reader, new TypeReference<List<Joc>>() {});
            return carregats != null ? carregats : new ArrayList<>();
        }
    }

    /**
     * Escriu el catàleg en un fitxer JSON.
     *
     * @param p ruta del fitxer JSON
     * @param jocs llista de jocs a desar
     * @throws IOException si hi ha un error d'escriptura
     */
    public void escriu(Path p, List<Joc> jocs) throws IOException {
        try (Writer writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8)) {
            mapper.writeValue(writer, jocs);
        }
    }
}