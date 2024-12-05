package com.yuzhihao.learn.config;

import com.gluonhq.attach.display.DisplayService;
import com.gluonhq.attach.display.impl.DummyDisplayService;
import com.gluonhq.attach.lifecycle.LifecycleEvent;
import com.gluonhq.attach.lifecycle.LifecycleService;
import com.gluonhq.attach.lifecycle.impl.DummyLifecycleService;
import com.gluonhq.attach.util.Services;
import com.gluonhq.attach.util.impl.ServiceFactory;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Dimension2D;

import java.util.*;

/**
 * @author yuzhihao
 */
public class ServicesRegister {

    public static void registerServices(){
        Services.registerServiceFactory(new ServiceFactory<DisplayService>() {
            @Override
            public Class<DisplayService> getServiceType() {
                return DisplayService.class;
            }

            @Override
            public Optional<DisplayService> getInstance() {
                return Optional.of(new DummyDisplayService() {
                    @Override
                    public boolean isPhone() {
                        return false;
                    }

                    @Override
                    public boolean isTablet() {
                        return false;
                    }

                    @Override
                    public boolean isDesktop() {
                        return true;
                    }

                    @Override
                    public Dimension2D getScreenResolution() {
                        return new Dimension2D(1120, 720);
                    }

                    @Override
                    public Dimension2D getDefaultDimensions() {
                        return new Dimension2D(1120, 720);
                    }

                    @Override
                    public float getScreenScale() {
                        return 0;
                    }

                    @Override
                    public boolean isScreenRound() {
                        return false;
                    }

                    @Override
                    public boolean hasNotch() {
                        return false;
                    }

                    @Override
                    public ReadOnlyObjectProperty<Notch> notchProperty() {
                        return new SimpleObjectProperty<>(Notch.UNKNOWN);
                    }
                });
            }
        });

    }


}
