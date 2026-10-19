package cat.pratfp.gamevault.model;

import java.time.LocalDate;

/**
 * Representa un joc dins del catàleg de GameVault.
 */
public record Joc(int id, String titol, String plataforma, String estudi, LocalDate dataSortida, double horesJugades, double nota) {

    public Joc {
        if (titol == null || titol.isBlank()) {
            throw new IllegalArgumentException("El títol no pot estar buit");
        }
        if (horesJugades < 0) {
            throw new IllegalArgumentException("Les hores no poden ser negatives");
        }
        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException("La nota ha d'anar de 0 a 10");
        }
    }
}