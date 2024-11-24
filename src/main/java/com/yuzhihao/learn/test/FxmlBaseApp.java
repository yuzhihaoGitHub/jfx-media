package com.yuzhihao.learn.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

public class FxmlBaseApp extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader load = new FXMLLoader(getClass().getResource("/base.fxml"));

        load.setControllerFactory(context::getBean);

        Object controller = load.getController();

        primaryStage.setScene(new Scene(load.load()));
        primaryStage.show();
    }
}
