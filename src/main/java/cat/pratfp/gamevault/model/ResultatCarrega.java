package cat.pratfp.gamevault.model;

import java.util.List;

/**
 * Resultat de carregar un fitxer de jocs: els jocs vàlids i les incidències de les línies descartades.
 *
 * @param jocs        jocs que s'han pogut crear
 * @param incidencies una entrada per línia descartada, amb el format "línia N: motiu"
 */
public record ResultatCarrega(List<Joc> jocs, List<String> incidencies) {
}
