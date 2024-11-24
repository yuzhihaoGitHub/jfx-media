package com.yuzhihao.learn.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


/**
 *
 * 裁剪图像 没学会
 *
 * @author yuzhihao
 */
public class RegionApp extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        Circle circle = new Circle(100,100,50);

        Pane pane = new Pane(circle);


        pane.setPrefSize(100,100);

        VBox box = new VBox(pane);
        box.setPrefSize(100,100);

        final Region container = new StackPane(box);
        Border border = new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID,new CornerRadii(4),BorderStroke.THICK));
        container.setBorder(border);

        Scene scene = new Scene(container);

        primaryStage.setScene(scene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);

        primaryStage.show();

    }

}
