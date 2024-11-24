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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

/**
 * @author carl
 */
public class URLTestModel {

    private final Notifications notifications =
            new Notifications();

    private Optional<URLTestObject> urlTestObject =
            Optional.empty();

    public Optional<URLTestObject> getUrlTestObject() {
        return urlTestObject;
    }

    public Optional<URLTestObject> testURL(String url) {

        try {
            long startTimeMillis = System.currentTimeMillis();
            HttpURLConnection urlConnection =
                    (HttpURLConnection) new URL(url).openConnection();
            try (
                    InputStream is = urlConnection.getInputStream();
            ) {
                while (is.read() != -1) {
                }
            }
            long endTimeMillis = System.currentTimeMillis();

            URLTestObject uto = new URLTestObject(
                    urlConnection.getResponseCode(),
                    (int) (endTimeMillis - startTimeMillis)
            );

            urlTestObject = Optional.of(uto);

        } catch(Exception exc) {
            URLTestObject uto = new URLTestObject(exc.getMessage());
            urlTestObject = Optional.of(uto);
        }

        notifications.publish(Notifications.EVENT_MODEL_UPDATE);

        return urlTestObject;
    }
}
