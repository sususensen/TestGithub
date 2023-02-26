package FileTree;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main extends Application {
    public static File ROOT_FILE = FileSystemView.getFileSystemView().getRoots()[0];
    @Override
    public void start(Stage stage) throws IOException {
        HBox hBox = new HBox();
        TreeView<String> treeView = new TreeView<>();
        List<ImageView> contentLabelList = new ArrayList<>();
        FileTreeItem fileTreeItem = new FileTreeItem(ROOT_FILE, f -> {
            File[] allFiles = f.listFiles();
            File[] directorFiles = f.listFiles(File::isDirectory);
            List<File> list = new ArrayList<>(Arrays.asList(allFiles));
            list.removeAll(Arrays.asList(directorFiles));
            return list.toArray(new File[list.size()]);
        });


       
        treeView.setRoot(fileTreeItem);
        treeView.setShowRoot(false);
        treeView.setMinWidth(200);
        hBox.getChildren().add(treeView);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(hBox);
       // stackPane.getChildren().add(imageView);
        Scene scene = new Scene(stackPane, 900, 700);
        stage.setTitle("图片展示");
        stage.setScene(scene);

        stage.show();
        stage.setOnCloseRequest(e -> {
            System.exit(0);
        });

    }

    public static void main(String[] args) {
        launch();
    }


}
