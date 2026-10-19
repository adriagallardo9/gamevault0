package cat.pratfp.gamevault.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Un joc del catàleg GameVault.
 * <p>
 * És un record: immutable, amb constructor, accessors, equals, hashCode i toString generats.
 * El constructor compacte valida les dades abans de crear l'objecte.
 */
public record Joc(int id, String titol, String plataforma, String estudi,
                  LocalDate dataSortida, double horesJugades, double nota) {   // TODO PR1-11 (2a part): fes que Joc es pugui serialitzar

    /*
     * TODO PR1-01 · Validació del model (CA 1.6)
     * Si alguna dada no és vàlida, llança IllegalArgumentException amb un missatge que contingui
     * la paraula indicada (les proves i el CsvJocDao fan servir aquest missatge):
     *   - títol null o en blanc      -> el missatge ha de contenir "títol"
     *   - hores jugades negatives    -> el missatge ha de contenir "hores"
     *   - nota més petita que 0 o més gran que 10 -> el missatge ha de contenir "nota"
     * Els valors límit (nota 0 i nota 10, 0 hores) SÍ que són vàlids.
     */
    public Joc {
        // Escriu aquí el teu codi (PR1-01)
    }
}
