package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImagesToResources {

    private static String imagePath = "C:\\Users\\Administrator\\IdeaProjects\\KNK_Football_gr9\\src\\main\\resources\\imagess\\s";

    public static String getImagePath() {
        return imagePath;
    }


    public static void imageToResources(String folderName, String imageName, Path sourcePath) throws IOException {
        String path = imagePath + "\\" + folderName + "\\" + imageName;

        File filedest = new File(path);
        if (!filedest.exists()) {
            path = imagePath + "\\" + folderName;
            File folder = new File(path);
            folder.mkdir();
            File newFile = new File(path + "\\" + imageName);
            Files.copy(sourcePath, newFile.toPath());
        } else {
            Files.copy(sourcePath, filedest.toPath());
        }
    }

    public static void imageToResourcesTeam(String leagueName, String teamName, String imageName, Path sourcePath) throws IOException {
        Path directoryPath = Paths.get(imagePath, leagueName, teamName);
        Path targetPath = directoryPath.resolve(imageName);

        if (!Files.exists(sourcePath)) {
            System.err.println("Source file does not exist: " + sourcePath);
            return;
        }

        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        try {
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied successfully to " + targetPath);
        } catch (IOException e) {
            System.err.println("Failed to copy file: " + e.getMessage());
            throw e;
        }
    }

}