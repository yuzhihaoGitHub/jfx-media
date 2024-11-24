package com.yuzhihao.learn;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

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

        Rectangle rectangle = new Rectangle(100, 50, Color.BLUE);

        rectangle.setX(100);
        rectangle.setY(100);
        rectangle.setScaleX(0.1);
        rectangle.setScaleY(0.1);
        rectangle.setRotate(45);

        rectangle.addEventHandler(MOUSE_CLICKED, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if(MOUSE_CLICKED.equals(event.getEventType())){
                    rectangle.setScaleX(rectangle.getScaleX()+0.1);
                    rectangle.setScaleY(rectangle.getScaleX()+0.1);
                }
                System.out.println(event.getEventType());
                System.out.println(event.getSource());
            }
        });

        Button button = new Button("click me");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(event+"click me");
            }
        });
        Pane root = new Pane(rectangle,button);


        FXMLLoader load = new FXMLLoader(getClass().getResource("/base.fxml"));

        load.setControllerFactory(context::getBean);

        System.out.println(Optional.ofNullable(load.getController()));

        primaryStage.setScene(new Scene(load.load()));
        primaryStage.show();
        System.out.println(Optional.ofNullable(load.getController()));

//        vlc.start(primaryStage);
    }

    @Override
    public void stop() {
        context.close();
        vlc.stop();
    }

}
