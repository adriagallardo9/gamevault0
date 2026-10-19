package cat.pratfp.gamevault.io;

import cat.pratfp.gamevault.model.Joc;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Gestió del cau (cache) mitjançant serialització de Java.
 */
public class CauCatalegTest {

    public void desa(Path p, List<Joc> jocs) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(p))) {
            oos.writeObject(jocs);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Joc> carrega(Path p) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(p))) {
            return (List<Joc>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Error de deserialització: " + e.getMessage(), e);
        }
    }
}