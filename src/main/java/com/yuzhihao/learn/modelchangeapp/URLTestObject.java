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

/**
 * @author carl
 */
public class URLTestObject {

    private final Integer statusCode;
    private final Integer loadTime;
    private final Boolean wasError;
    private final String errorMessage;

    public URLTestObject(Integer statusCode,
                         Integer loadTime) {
        this.statusCode = statusCode;
        this.loadTime = loadTime;
        wasError = false;
        errorMessage = "";
    }

    public URLTestObject(String errorMessage) {
        this.statusCode = null;
        this.loadTime = null;
        wasError = true;
        this.errorMessage = errorMessage;
    }

    public Integer getLoadTime() {
        return loadTime;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public Boolean getWasError() {
        return wasError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
