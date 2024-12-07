package adventOfCode2024.abstractClasses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class AbstractFileReader {

    private String filePath;

    public AbstractFileReader(String filePath) {
        this.filePath = filePath;
    }

    public final void readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            processFile(reader);
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    protected abstract void processFile(BufferedReader reader) throws IOException;

    protected void handleIOException(IOException e) {
        System.out.println("Error reading file: " + e.getMessage());
    }
}
