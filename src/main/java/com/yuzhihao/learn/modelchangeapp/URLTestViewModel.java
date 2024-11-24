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

import javafx.beans.property.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * @author carl
 */
public class URLTestViewModel {

    private final URLTestModel urlTestModel = new URLTestModel();

    private final Notifications notifications = new Notifications();

    //
    // Data elements
    //
    private final StringProperty url = new SimpleStringProperty("");
    private final StringProperty statusCode = new SimpleStringProperty("");
    private final StringProperty loadTime = new SimpleStringProperty("");

    //
    // Status elements
    //
    private final BooleanProperty wasError = new SimpleBooleanProperty(false);
    private final StringProperty errorMessage = new SimpleStringProperty("");

    public URLTestViewModel() {
        notifications.subscribe(Notifications.EVENT_MODEL_UPDATE,
                                this,
                                this::update);
    }

    public StringProperty urlProperty() { return url; }

    public StringProperty statusCodeProperty() { return statusCode; }

    public StringProperty loadTimeProperty() { return loadTime; }

    public StringProperty errorMessageProperty() { return errorMessage; }

    public ReadOnlyBooleanProperty urlTestTaskRunningProperty() {
        return urlTestCommand.runningProperty();
    }

    public ReadOnlyStringProperty urlTestTaskMessageProperty() {
        return urlTestCommand.messageProperty();
    }

    public ReadOnlyDoubleProperty urlTestTaskProgressProperty() {
        return urlTestCommand.progressProperty();
    }

    //
    // Command to be invoked by View
    //
    public void test() {
       urlTestCommand.restart();
    }

    //
    // Responds to Model change
    //
    private void update(String event) {

        urlTestModel.getUrlTestObject().ifPresent(

            (testObject) -> {

                wasError.set( testObject.getWasError() );

                if( !testObject.getWasError() ) {

                    statusCode.set(
                            "Status code: " +
                            String.valueOf(testObject.getStatusCode())
                    );

                    loadTime.set(
                            String.valueOf(testObject.getLoadTime()) +
                                    " ms"
                    );

                    errorMessage.set(testObject.getErrorMessage());
                } else {
                    statusCode.set("");  // use empty TextField, not 0
                    loadTime.set("");  // use empty TextField, not 0
                    errorMessage.set( testObject.getErrorMessage() );
                }
            });
    }

    //
    // Async wrapper for command; uses ViewModel internals for parameter
    //
    private final Service<Void> urlTestCommand = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateProgress(0.1d, 1.0d);
                    updateMessage("Testing url " + url.get());
                    urlTestModel.testURL( url.get() );
                    return null;
                }
                protected void failed() {
                    getException().printStackTrace();  // just in case
                }
            };
        }
    };
}
