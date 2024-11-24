/*
 * Copyright 2016 Bekwam, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yuzhihao.learn.modelchangeapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author carl
 */
public class URLTestView extends StackPane {

    private final URLTestViewModel testViewModel =
            new URLTestViewModel();

    public URLTestView() {

        Label lblURL = new Label("URL to Test");
        TextField tfURL = new TextField();
        Button btnTest = new Button("Test");
        Label lblStatus = new Label("");
        Label lblLoadTime = new Label("");
        HBox resultHBox = new HBox(lblStatus, lblLoadTime);
        resultHBox.setSpacing(10);

        VBox vbox = new VBox( lblURL, tfURL, btnTest, resultHBox );
        vbox.setPadding( new Insets(40) );
        vbox.setSpacing( 10 );
        vbox.setAlignment(Pos.CENTER_LEFT);

        Label lblTaskStatus = new Label("");
        ProgressBar pb = new ProgressBar();
        HBox statusHBox = new HBox(pb, lblTaskStatus);
        statusHBox.setSpacing(4);
        statusHBox.setPadding(new Insets(4));
        statusHBox.setMaxHeight( 20 );

        StackPane.setAlignment(statusHBox, Pos.BOTTOM_LEFT );

        this.getChildren().addAll( vbox, statusHBox );

        //
        // Bind data elements
        //
        lblStatus.textProperty().bind( testViewModel.statusCodeProperty() );
        lblLoadTime.textProperty().bind( testViewModel.loadTimeProperty() );
//        tfURL.textProperty().bindBidirectional( testViewModel.urlProperty() );
        testViewModel.urlProperty().bind( tfURL.textProperty() );

        //
        // Bind status elements
        //
        statusHBox.visibleProperty().bind(testViewModel.urlTestTaskRunningProperty() );
        pb.progressProperty().bind( testViewModel.urlTestTaskProgressProperty() );
        lblTaskStatus.textProperty().bind( testViewModel.urlTestTaskMessageProperty());

        //
        // Set up actions
        //
        btnTest.setOnAction( (evt) -> testViewModel.test() );

        //
        // Set up special error handling notification
        //
        testViewModel.errorMessageProperty().addListener(
                (obs,ov,nv) -> {
                    if( nv != null && !nv.isEmpty() ) {
                        Alert alert = new Alert(
                                Alert.AlertType.ERROR, nv
                        );
                        alert.showAndWait();
                    }
                }
        );
    }
}
