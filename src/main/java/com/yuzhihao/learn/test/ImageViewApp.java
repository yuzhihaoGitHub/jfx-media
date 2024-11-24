package com.yuzhihao.learn.test;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ImageViewApp extends Application {

    public final static String IMAGE_LOC = "vlcj-logo.png";

    @Override
    public void start(Stage primaryStage) throws Exception {

        Image image2 = new Image(IMAGE_LOC, 360.0d, 360.0d, true, true );
        Image image3 = new Image(IMAGE_LOC, 360.0d, 360.0d, false, true);
        Image image4 = new Image(IMAGE_LOC);

        ImageView iv1 = new ImageView(IMAGE_LOC);
        ImageView iv2 = new ImageView(image2);
        ImageView iv3 = new ImageView(image3);
        ImageView iv4 = new ImageView(image4);

        iv4.setPreserveRatio(true);
        iv4.setFitHeight(360);
        iv4.setFitWidth(360);
        Rectangle2D viewportRect = new Rectangle2D(20, 50, 100, 100);
        iv4.setViewport(viewportRect);


        HBox box1 = new HBox(iv1,iv2);
        HBox box2 = new HBox(iv3,iv4);

        VBox box = new VBox(box1,box2);

        primaryStage.setScene(new Scene(box, 720, 720));
        primaryStage.show();
    }
}
