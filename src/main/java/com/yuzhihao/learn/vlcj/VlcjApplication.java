package com.yuzhihao.learn.vlcj;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * media的jfx程序
 *
 * @author yuzhihao
 */
@SpringBootApplication
public class VlcjApplication extends Application {
    private ConfigurableApplicationContext context;

    private final MediaVlc vlc = new MediaVlc();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {

        context = SpringApplication.run(VlcjApplication.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 在这里设置 JavaFX 界面
        primaryStage.setTitle("JavaFX + Spring Boot");

        primaryStage.setScene(new Scene(new StackPane(new TextArea("Hello, World!")),300,300));
        primaryStage.show();

//        vlc.start(primaryStage);
    }

    @Override
    public void stop() {
        context.close();
        vlc.stop();
    }

}
