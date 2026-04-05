/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.interact;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author rash4
 */
public final class BasicStatics {
    private BasicStatics(){}

    public static final String APP_NAME = "Noice";

    private static final Path HOME = Path.of(System.getProperty("user.home"));
    private static final Path ROOT = ensureDir(computeRoot());
    private static final Path DOCS = ensureDir(HOME.resolve("Documents"));

    private static Path computeRoot() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return HOME.resolve("AppData").resolve("Local").resolve(APP_NAME);
        } else if (os.contains("mac")) {
            return HOME.resolve("Library").resolve("Application Support").resolve(APP_NAME);
        } else {
            return HOME.resolve(".local").resolve("share").resolve(APP_NAME);
        }
    }
    public static Path ensureDir(Path path) {
        try {
            Files.createDirectories(path); // creates if missing, does nothing if exists
            return path;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create directory: " + path, e);
        }
    }
    public static Path home() { return HOME; }
    public static Path root() { return ROOT; }
    public static Path docs() { return DOCS; }
}