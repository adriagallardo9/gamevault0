package cat.pratfp.gamevault;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Les teves proves (PR1-14). Aquesta classe SÍ que l'has de modificar.
 * Escriu dues proves noves que no estiguin a les altres classes de prova.
 * Canvia el @DisplayName perquè digui què comproves i treu el fail().
 * Idees: l'índex amb un títol de més de 40 caràcters; una còpia de seguretat d'un fitxer sense extensió;
 * un CSV amb una línia en blanc; carregar un .json que no existeix amb el Convertidor;
 * sumaHores en dos registres seguits...
 */
class ProvesPropiesTest {

    @TempDir
    Path tmp;

    @Test
    @DisplayName("PR1-14 · la meva primera prova")
    void primeraProva() throws Exception {
        // Escriu aquí el teu codi (PR1-14)
        fail("PR1-14: escriu la teva primera prova");
    }

    @Test
    @DisplayName("PR1-14 · la meva segona prova")
    void segonaProva() throws Exception {
        // Escriu aquí el teu codi (PR1-14)
        fail("PR1-14: escriu la teva segona prova");
    }
}
