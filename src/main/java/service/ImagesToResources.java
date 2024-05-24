package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImagesToResources {

    private static final String imagePath = "C:\\Users\\Administrator\\IdeaProjects\\KNK_Football_gr9\\src\\main\\resources\\imagess\\s";

    public static String getImagePath() {
        return imagePath;
    }

    public static void imageToResources(String folderName, String imageName, String name, Path sourcePath) throws IOException {
        Path directoryPath = Paths.get(imagePath, folderName);
        Path targetPath = directoryPath.resolve(imageName);

        if (!Files.exists(sourcePath)) {
            throw new IOException("Source file does not exist: " + sourcePath);
        }

        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File copied successfully to " + targetPath);
    }

}
