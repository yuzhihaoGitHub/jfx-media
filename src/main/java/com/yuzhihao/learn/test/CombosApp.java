package com.yuzhihao.learn.test;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class CombosApp extends Application {

    private final ComboBox<Color> account = new ComboBox<>();

    private final static Pair<String, String> EMPTY_PAIR = new Pair<>("", "");

    @Override
    public void start(Stage primaryStage) throws Exception {

        Label accountsLabel = new Label("Account:");
        account.setPrefWidth(200);
        Button saveButton = new Button("Save");

        HBox hbox = new HBox(
                accountsLabel,
                account,
                saveButton);
        hbox.setSpacing( 10.0d );
        hbox.setAlignment(Pos.CENTER );
        hbox.setPadding( new Insets(40) );

        Scene scene = new Scene(hbox);

        initCombo();

        saveButton.setOnAction( (evt) -> {
            if( account.getValue().equals(EMPTY_PAIR ) ) {
                System.out.println("no save needed; no item selected");
            } else {
                System.out.println("saving " + account.getValue());
            }
        });

        primaryStage.setTitle("CombosApp");
        primaryStage.setScene( scene );
        primaryStage.show();
    }

    private void initCombo() {

        List<Color> accounts = new ArrayList<>();


        accounts.add(  Color.RED );
        accounts.add( Color.GREEN );
        accounts.add( Color.BLUE );
        accounts.add( Color.BEIGE );

//        account.getItems().add( EMPTY_PAIR );
        account.getItems().addAll( accounts );
//        account.setValue( EMPTY_PAIR );

        Callback<ListView<Color>, ListCell<Color>> factory =
                (lv) ->

                        new ListCell<Color>() {
                            private final Rectangle rectangle;
                            {
                                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                rectangle = new Rectangle(10, 10);
                            }
                            @Override
                            protected void updateItem(Color item, boolean empty) {
                                super.updateItem(item, empty);
                                if( empty ) {
                                    setGraphic(null);
                                } else {
                                    rectangle.setFill(item);
                                    setGraphic(rectangle);
                                }
                            }
                        };

        account.setCellFactory( factory );
        account.setButtonCell( factory.call( null ) );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
