package service;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class BrowseImage {
    public static File browseImage(String imagePath, File fileSource, ImageView imageView) {
        FileChooser fileChooser = new FileChooser();
        //   fileChooser.setInitialDirectory(new File(imagePath));
        fileSource = fileChooser.showOpenDialog(new Stage());
        if(fileSource != null) {
            String fileUrl = fileSource.toURI().toString();
            Image image = new Image(fileUrl);
            imageView.setImage(image);
        }
        return fileSource;
    }
}
