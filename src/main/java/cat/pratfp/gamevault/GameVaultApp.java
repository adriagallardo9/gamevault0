package cat.pratfp.gamevault;

import cat.pratfp.gamevault.fs.Magatzem;
import cat.pratfp.gamevault.io.CauCataleg;
import cat.pratfp.gamevault.io.CsvJocDao;
import cat.pratfp.gamevault.io.IndexHores;
import cat.pratfp.gamevault.io.JsonJocDao;
import cat.pratfp.gamevault.io.RegistreOperacions;
import cat.pratfp.gamevault.model.Joc;
import cat.pratfp.gamevault.model.ResultatCarrega;
import cat.pratfp.gamevault.servei.CatalegException;
import cat.pratfp.gamevault.servei.Convertidor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Programa principal de la PR1. NO s'ha de modificar.
 * Executa totes les peces de GameVault v1 una darrere l'altra sobre la carpeta dades/.
 * Quan tots els TODO estiguin fets, la sortida ha de ser com la de l'annex de l'enunciat.
 */
public class GameVaultApp {

    public static void main(String[] args) {
        Path dades = Path.of("dades");
        Magatzem magatzem = new Magatzem(dades);
        RegistreOperacions registre = new RegistreOperacions(dades.resolve("operacions.log"));

        System.out.println("GameVault v1 · PR1 Accés a dades");
        System.out.println("================================");
        try {
            // 1. Estructura de carpetes
            magatzem.preparaEstructura();
            System.out.println("1. Estructura preparada: import, export i backup");

            // 2. Còpia de seguretat del catàleg
            Path cataleg = dades.resolve("catalog.csv");
            Path copia = magatzem.copiaSeguretat(cataleg);
            System.out.println("2. Còpia de seguretat: " + copia.getFileName());
            registre.registra("còpia de seguretat " + copia.getFileName());

            // 3. Càrrega del CSV brut, amb incidències
            CsvJocDao csv = new CsvJocDao();
            ResultatCarrega brut = csv.llegeix(dades.resolve("catalog_brut.csv"));
            System.out.println("3. catalog_brut.csv: " + brut.jocs().size() + " vàlids, "
                    + brut.incidencies().size() + " descartats");
            brut.incidencies().forEach(i -> System.out.println("     " + i));
            registre.registra("càrrega catalog_brut.csv · " + brut.jocs().size() + " vàlids · "
                    + brut.incidencies().size() + " descartats");

            // 4. Càrrega del catàleg bo
            List<Joc> jocs = csv.llegeix(cataleg).jocs();
            System.out.println("4. catalog.csv: " + jocs.size() + " jocs");

            // 5. Exportació a JSON
            Path json = magatzem.exportacio().resolve("jocs.json");
            new JsonJocDao().escriu(json, jocs);
            System.out.println("5. Exportat a JSON: " + json.getFileName() + " (" + Files.size(json) + " bytes)");

            // 6. Conversió JSON -> CSV i comprovació
            Convertidor convertidor = new Convertidor();
            Path tornada = magatzem.exportacio().resolve("tornada.csv");
            int n = convertidor.converteix(json, tornada);
            boolean iguals = csv.llegeix(tornada).jocs().equals(jocs);
            System.out.println("6. Convertit JSON -> CSV: " + n + " jocs · iguals a l'original? " + (iguals ? "sí" : "NO"));
            registre.registra("conversió jocs.json -> tornada.csv · " + n + " jocs");

            // 7. Cau binària amb serialització
            Path ser = dades.resolve("cataleg.ser");
            CauCataleg cau = new CauCataleg();
            cau.desa(ser, jocs);
            List<Joc> recuperats = cau.carrega(ser);
            System.out.println("7. Cau binària: " + ser.getFileName() + " (" + Files.size(ser) + " bytes) · "
                    + recuperats.size() + " jocs recuperats");

            // 8. Índex d'hores amb accés aleatori
            Path dat = magatzem.exportacio().resolve("hores.dat");
            Files.deleteIfExists(dat);
            IndexHores index = new IndexHores(dat);
            for (int i = 0; i < jocs.size(); i++) {
                index.escriu(i, jocs.get(i));
            }
            double abans = index.llegeixHores(2);
            double despres = index.sumaHores(2, 3.0);
            System.out.println("8. Índex d'hores: " + index.nombreRegistres() + " registres de "
                    + IndexHores.MIDA_REGISTRE + " bytes (" + Files.size(dat) + " bytes)");
            System.out.println("     posició 2 · " + jocs.get(2).titol() + ": " + abans + " h + 3.0 h = " + despres + " h");
            registre.registra("hores posició 2 · " + abans + " -> " + despres);

            // 9. Inventari del magatzem
            System.out.println("9. Inventari (" + magatzem.midaTotal() + " bytes en total):");
            magatzem.inventari().forEach(f -> System.out.println("     " + f));

            // 10. Prova d'error controlat
            try {
                convertidor.converteix(cataleg, dades.resolve("export/jocs.xml"));
            } catch (CatalegException e) {
                System.out.println("10. Error controlat: " + e.getMessage());
            }
            System.out.println("Registre de l'execució: " + dades.resolve("operacions.log"));

        } catch (CatalegException | IOException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            System.out.println("Encara falta: " + e.getMessage());
            System.exit(2);
        }
    }
}
