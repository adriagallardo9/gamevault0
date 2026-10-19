package cat.pratfp.gamevault;

import cat.pratfp.gamevault.model.Joc;

import java.time.LocalDate;
import java.util.List;

/** Dades de prova compartides per totes les proves. NO s'ha de modificar. */
public final class Dades {

    private Dades() {
    }

    public static final String CSV_BRUT = """
            id;titol;plataforma;estudi;dataSortida;horesJugades;nota
            1;Hollow Knight;PC;Team Cherry;2017-02-24;42.5;9.5
            2;Celeste;PC;Maddy Makes Games;2018-01-25;15.0;12.0
            3;Gris;PC
            4;Hades;PC;Supergiant Games;25-09-2020;30.0;9.0
            5;"Baldur's Gate 3; Deluxe";PC;Larian Studios;2023-08-03;120.0;9.8
            6;Metroid Dread;Switch;MercurySteam;2021-10-08;-5.0;9.0
            """;

    public static List<Joc> cataleg() {
        return List.of(
                new Joc(1, "Hollow Knight", "PC", "Team Cherry", LocalDate.of(2017, 2, 24), 42.5, 9.5),
                new Joc(2, "Blasphemous 2", "PS5", "The Game Kitchen", LocalDate.of(2023, 8, 24), 18.0, 8.5),
                new Joc(3, "Metroid Dread", "Switch", "MercurySteam", LocalDate.of(2021, 10, 8), 12.5, 9.0),
                new Joc(4, "Gris", "PC", "Nomada Studio", LocalDate.of(2018, 12, 13), 4.0, 8.0),
                new Joc(5, "Baldur's Gate 3; Deluxe", "PC", "Larian Studios", LocalDate.of(2023, 8, 3), 120.0, 9.8));
    }
}
