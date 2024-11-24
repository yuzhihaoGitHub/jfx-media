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

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author carl
 */
public class Notifications {

    public final static String EVENT_MODEL_UPDATE = "modelUpdate";

    private final Map<String, List<SubscriberObject>> subscribers = new LinkedHashMap<>();

    private static Notifications instance = new Notifications();

    public void publish(String event) {

        Platform.runLater( () -> {
            List<SubscriberObject> subscriberList = instance.subscribers.get(event);

            if (subscriberList != null) {

                subscriberList.forEach(subscriberObject -> subscriberObject.getCb().accept(event));

                // event ends after last subscriber gets callback
            }
        } );
    }

    public void subscribe(String event, Object subscriber, Consumer<String> cb) {

        if( !instance.subscribers.containsKey(event) ) {
            List<SubscriberObject> slist = new ArrayList<>();
            instance.subscribers.put( event, slist );
        }

        List<SubscriberObject> subscriberList = instance.subscribers.get( event );

        subscriberList.add( new SubscriberObject(subscriber, cb) );
    }

    public void unsubscribe(String event, Object subscriber) {

        List<SubscriberObject> subscriberList = instance.subscribers.get( event );

        if (subscriberList == null) {
            subscriberList.remove( subscriber );
        }
    }

    static class SubscriberObject {

        private final Object subscriber;
        private final Consumer<String> cb;

        public SubscriberObject(Object subscriber,
                                Consumer<String> cb) {
            this.subscriber = subscriber;
            this.cb = cb;
        }

        public Object getSubscriber() {
            return subscriber;
        }

        public Consumer<String> getCb() {
            return cb;
        }

        @Override
        public int hashCode() {
            return subscriber.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return subscriber.equals(obj);
        }
    }
}
