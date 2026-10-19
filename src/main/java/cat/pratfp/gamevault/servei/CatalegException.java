package cat.pratfp.gamevault.servei;

/**
 * Error del catàleg pensat per a l'usuari: porta un missatge entenedor
 * i conserva l'excepció original com a causa.
 */
public class CatalegException extends Exception {

    /**
     * Crea l'excepció només amb el missatge.
     *
     * @param missatge text que veurà l'usuari
     */
    public CatalegException(String missatge) {
        super(missatge);
    }

    /**
     * Crea l'excepció amb missatge i causa.
     *
     * @param missatge text que veurà l'usuari
     * @param causa    excepció tècnica original (no es perd)
     */
    public CatalegException(String missatge, Throwable causa) {
        super(missatge, causa);
    }
}
