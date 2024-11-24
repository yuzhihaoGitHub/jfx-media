package com.yuzhihao.learn.test.table;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.UUID;

public class TableApp extends Application {


    public TableView<SysUser> init11(){
        TableView<SysUser> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        VBox.setVgrow(tableView, Priority.ALWAYS );

        TableColumn<SysUser, Object> 用户ID = new TableColumn<>("用户ID");
        TableColumn<SysUser, Object> 用户账号 = new TableColumn<>("用户账号");
        TableColumn<SysUser, Object> 用户昵称 = new TableColumn<>("用户昵称");
        TableColumn<SysUser, Object> 用户邮箱 = new TableColumn<>("用户邮箱");
        TableColumn<SysUser, Object> 手机号码 = new TableColumn<>("手机号码");
        TableColumn<SysUser, Object> 用户性别 = new TableColumn<>("用户性别");
        用户ID.setCellValueFactory(new PropertyValueFactory<>("userId"));
        用户账号.setCellValueFactory(new PropertyValueFactory<>("userName"));
        用户昵称.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        用户邮箱.setCellValueFactory(new PropertyValueFactory<>("email"));
        手机号码.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        用户性别.setCellValueFactory(new PropertyValueFactory<>("sex"));



        tableView.getColumns().addAll(
                用户ID
                , 用户账号
                , 用户昵称
                , 用户邮箱
                , 手机号码
                , 用户性别
        );

        tableView.getItems().addAll(
                new SysUser(1L,"KBD-0455892", "Mechanical Keyboard", UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString()),
                new SysUser( 2L,"145256", "Product Docs", UUID.randomUUID().toString(), UUID.randomUUID().toString() , UUID.randomUUID().toString()),
                new SysUser( 3L,"OR-198975", "O-Ring (100)", UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        );

        return tableView;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox vbox = new VBox( init11());
        vbox.setPadding( new Insets(10) );
        vbox.setSpacing( 10 );

        Scene scene = new Scene(vbox);

        primaryStage.setTitle("TableSelectApp");
        primaryStage.setScene( scene );
        primaryStage.setHeight( 376 );
        primaryStage.setWidth( 667 );
        primaryStage.show();
    }

}
