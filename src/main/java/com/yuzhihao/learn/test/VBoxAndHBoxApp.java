package com.yuzhihao.learn.test;

import com.yuzhihao.learn.test.table.SysUser;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VBoxAndHBoxApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();
//        vBox.setSpacing(20);

        Button button = new Button("Refresh");

        HBox hBox = new HBox();

        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().add(new Hyperlink("Sign Out"));
        HBox.setHgrow(hBox, Priority.ALWAYS );

        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.BOTTOM_LEFT);
        hBox2.getChildren().addAll(button,hBox);
        VBox.setMargin(hBox2,new Insets(10));


        TableView<SysUser> tblCustomers = new TableView<>();
        tblCustomers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        TableColumn<SysUser,String> 用户ID = new TableColumn<>("用户ID");

        TableColumn<SysUser,String> 用户账号 = new TableColumn<>("用户账号");
        用户ID.setCellValueFactory(new PropertyValueFactory<>("userId"));
        用户账号.setCellValueFactory(new PropertyValueFactory<>("userName"));

        tblCustomers.getColumns().addAll(用户ID,用户账号);
        tblCustomers.getItems().addAll(new SysUser(1L,"admin"),new SysUser(2L,"user"));

        VBox.setMargin( tblCustomers, new Insets(0.0d, 10.0d, 10.0d, 10.0d) );
        VBox.setVgrow( tblCustomers, Priority.ALWAYS );

        HBox bottomControls = new HBox();

        Button btnClose = new Button("Close");
        bottomControls.getChildren().add(btnClose);
        bottomControls.setAlignment(Pos.BOTTOM_RIGHT );

        VBox.setMargin( bottomControls, new Insets(10.0d) );

        Separator sep = new Separator();

//        VBox.setMargin(hBox2, new Insets(20));
//        VBox.setMargin(tblCustomers, new Insets(20));
//        VBox.setMargin(bottomControls, new Insets(20));

        vBox.getChildren().add(hBox2);
        vBox.getChildren().add(tblCustomers);
        vBox.getChildren().add(sep);
        vBox.getChildren().add(bottomControls);

        Scene scene = new Scene(vBox, 800, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }


}
