package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImagesToResources {

    private static String imagePath = "C:\\Users\\PULSE Electronics\\Desktop\\KNK_football\\src\\main\\resources\\imagess\\s";

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
        String path = imagePath + "\\" + leagueName + "\\" + teamName + "\\" + imageName;

        File filedest = new File(path);
        if (!filedest.exists()) {
            path = imagePath + "\\" + leagueName + "\\" + teamName;
            File folder = new File(path);
            folder.mkdir();
            File newFile = new File(path + "\\" + imageName);
            Files.copy(sourcePath, newFile.toPath());
        } else if (sourcePath == filedest.toPath()) {
            //...
        } else {
            Files.copy(sourcePath, filedest.toPath());
        }

    }
}