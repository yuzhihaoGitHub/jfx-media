package com.yuzhihao.learn.test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.yuzhihao.learn.test.ImageViewApp.IMAGE_LOC;

/**
 *
 * 网格排列布局
 *
 * 例子：表单提交
 *
 * @author yuzhihao
 */
public class GridPaneApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox vBox = new VBox();

        GridPane gp = new GridPane();
        gp.setPadding( new Insets(10) );
        gp.setHgap( 4 );
        gp.setVgap( 8 );
        Label label = new Label("用户名");
        TextField textField = new TextField();

        Label label1 = new Label("手机号");
        TextField textField1 = new TextField();

        Label label2 = new Label("用户密码");
        TextField textField2 = new TextField();

        Label label3 = new Label("描述信息");
        TextArea textField3 = new TextArea();

//        gp.setGridLinesVisible(true);

        gp.add(new Label("用户信息提交"), 1, 0);

        gp.add(label, 0, 1);gp.add(textField, 1, 1);gp.add(label1, 2, 1);gp.add(textField1, 3, 1);
        gp.add(label2, 0, 2);gp.add(textField2, 1, 2);
        gp.add(label3, 0, 3);gp.add(textField3, 1, 3);

        VBox.setVgrow(gp, Priority.ALWAYS );

        GridPane.setColumnSpan( textField2, 3 );
        GridPane.setColumnSpan( textField3, 3 );

        Separator sep = new Separator(); // hr



        ButtonBar buttonBar = new ButtonBar();
        buttonBar.setPadding(new Insets(10));
        Button button = new Button("保存");
        ButtonBar.setButtonData(button, ButtonBar.ButtonData.OK_DONE);
        Button button1 = new Button("取消");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.runLater(()->{

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

                    primaryStage.setScene(new Scene(box));

                });
            }
        });

        ButtonBar.setButtonData(button1, ButtonBar.ButtonData.CANCEL_CLOSE);

        buttonBar.getButtons().addAll(button, button1);

        vBox.getChildren().addAll(gp,sep,buttonBar);

        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();




    }

}
