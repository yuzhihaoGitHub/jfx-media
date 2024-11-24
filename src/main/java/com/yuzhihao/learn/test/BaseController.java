package com.yuzhihao.learn.test;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

/**
 * @author yuzhihao
 */
@Component
public class BaseController {

    @FXML
    Button button;

    public void initialize(){
        System.out.println("BaseController initialize");
    }

    public void button(){

    }

}
