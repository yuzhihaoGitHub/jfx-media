package com.yuzhihao.learn.test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;

public class MenuExample extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("我的应用程序");

        // 创建菜单栏
        MenuBar menuBar = new MenuBar();

        // 创建文件菜单
        Menu fileMenu = new Menu("文件");
        MenuItem newItem = new MenuItem("新建");
        MenuItem exitItem = new MenuItem("退出");

        exitItem.setOnAction(e -> System.exit(0)); // 退出应用程序

        fileMenu.getItems().addAll(newItem, exitItem);

        // 创建帮助菜单
        Menu helpMenu = new Menu("帮助");
        MenuItem aboutItem = new MenuItem("关于");
        aboutItem.setOnAction(e -> showAboutDialog());

        helpMenu.getItems().add(aboutItem);

        // 将菜单添加到菜单栏
        menuBar.getMenus().addAll(fileMenu, helpMenu);

        // 创建主布局
        BorderPane layout = new BorderPane();
        layout.setTop(menuBar); // 将菜单栏放在顶部



        // 创建场景并显示
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("关于");
        alert.setHeaderText("我的应用程序");
        alert.setContentText("这是一个示例应用程序。");
        alert.showAndWait();

    }

    public static void main(String[] args) {
        // 设置应用程序名称
        System.setProperty("apple.awt.application.name", "Media");
        launch(args);
    }
}
