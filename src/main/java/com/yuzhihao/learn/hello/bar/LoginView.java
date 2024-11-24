package com.yuzhihao.learn.hello.bar;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.yuzhihao.learn.hello.ApplicationView;
import com.yuzhihao.learn.hello.dialog.ApplicationDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;

/**
 * 登陆显示页面
 *
 * @author yuzhihao
 * @since 2024-11-24 13:23:57
 *
 */
@Log4j2
public class LoginView extends View {

    private final boolean isLogin = true;

    public LoginView() {
        FloatingActionButton fab = new FloatingActionButton();

        fab.showOn(this);
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon32.png"))));

        imageView.setPreserveRatio(true);

        appBar.setNavIcon(imageView);

        appBar.setTitleText("欢迎使用JVLC摄像头媒体播放器");

        appBar.setAutoHideProgressBar(true);

        if(isLogin){
            setCenter(loginShow());
        }else{
            setCenter(registerShow());
        }

    }

    /**
     * 创建文件和icon输入UI
     * @param username
     * @return
     */
    private  StackPane createTextFieldWithIcon(Node node,TextField username) {
        StackPane usernamep = new StackPane(node, username);
        StackPane.setMargin(node, new Insets(0, 0, 0,325 )); // 设置图标和输入框之间的间距
        return usernamep;
    }


    private Node loginShow(){
        //登陆页面
        Label info = new Label("JVLC MEDIA PLAYER");
        info.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField username = new TextField();
        username.setMaxWidth(300);
        username.setPromptText("请输入用户名(手机号)");
        TextField password = new TextField();
        password.setPromptText("请输入用户密码");
        password.setMaxWidth(300);

        Button login = new Button("登陆");
        login.setOnAction(e->{
            log.info("登陆：{},username:{},password:{}",e,username.getText(),password.getText());

            //登陆成功
            if("admin".equals(username.getText()) && "admin".equals(password.getText())){
                getAppManager().switchView(ApplicationView.INDEX_VIEW);
            }else{
                log.info("登陆失败");
                ApplicationDialog.showDialog("登陆提醒","用户名(手机)或密码错误");

            }

        });
        login.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        login.setPadding(new Insets(0,100,0,0));
        login.setMaxWidth(325);
        Hyperlink register = new Hyperlink("去注册");
        register.setOnAction(e->{
            log.info("点击注册：{}",e);
            setCenter(registerShow());
        });

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        StackPane usernamep = createTextFieldWithIcon(MaterialDesignIcon.VERIFIED_USER.graphic(),username);
        StackPane passwordp = createTextFieldWithIcon(MaterialDesignIcon.LOCK_OUTLINE.graphic(),password);

        VBox.setMargin(usernamep, new Insets(40, 0, 20, 0));
        VBox.setMargin(passwordp, new Insets(20, 0, 20, 0));
        VBox.setMargin(login, new Insets(20, 0, 10, 25));
        VBox.setMargin(register, new Insets(0, 0, 0, 25));

        vBox.getChildren().addAll(info,usernamep,passwordp,login,register);

        vBox.setBackground(new Background(new BackgroundImage(new Image("/images/back/bj2.png"),BackgroundRepeat.SPACE,BackgroundRepeat.SPACE,null,null)));

        return vBox;
    }

    private Node registerShow(){
        //登陆页面
        Label info = new Label("JVLC MEDIA PLAYER");
        info.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField iphone = new TextField();
        iphone.setMaxWidth(300);
        iphone.setPromptText("请输入手机号");

        //手机号
        StackPane iphonep = createTextFieldWithIcon(MaterialDesignIcon.PHONE_IPHONE.graphic(),iphone);
        VBox.setMargin(iphonep, new Insets(40, 0, 20, 0));

        //验证码
        TextField verificationCode = new TextField();
        verificationCode.setMaxWidth(200);
        verificationCode.setPromptText("请输入验证码");

        Button vbutton = new Button("获取验证码");
        vbutton.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        vbutton.setPrefWidth(100);
        vbutton.setPrefHeight(30);

        StackPane vcodep = new StackPane(vbutton, verificationCode);
        StackPane.setMargin(vbutton, new Insets(0, 0, 0,250 ));
        StackPane.setMargin(verificationCode, new Insets(0, 100, 0,0 ));
        VBox.setMargin(vcodep, new Insets(20, 0, 20, 0));

        //密码1
        TextField password1 = new TextField();
        password1.setPromptText("请输入第一次密码");
        password1.setMaxWidth(300);

        StackPane passwordp1 = createTextFieldWithIcon(MaterialDesignIcon.LOCK_OPEN.graphic(),password1);
        VBox.setMargin(passwordp1, new Insets(20, 0, 20, 0));

        //密码2
        TextField password2 = new TextField();
        password2.setPromptText("请输入第二次确认密码");
        password2.setMaxWidth(300);

        StackPane passwordp2 = createTextFieldWithIcon(MaterialDesignIcon.LOCK_OUTLINE.graphic(),password2);
        VBox.setMargin(passwordp2, new Insets(20, 0, 20, 0));

        //注册
        Button register = new Button("注册");
        register.setOnAction(e->{
            log.info("注册：{},username:{},code:{},password1:{},password1:{}",e,iphone.getText(),verificationCode.getText(),password1.getText(),password2.getText());
        });
        register.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        register.setPrefWidth(325);
        VBox.setMargin(register, new Insets(20, 0, 20, 25));

        Button login = new Button("去登陆");
        login.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        login.setPrefWidth(325);
        login.setOnAction(e->{
            log.info("进入登陆：{}",e);
            setCenter(loginShow());
        });
        VBox.setMargin(login, new Insets(0, 0, 0, 25));

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);




        vBox.getChildren().addAll(info,iphonep,vcodep,passwordp1,passwordp2,register,login);

        vBox.setBackground(new Background(new BackgroundImage(new Image("/images/back/bj2.png"),BackgroundRepeat.SPACE,BackgroundRepeat.SPACE,null,null)));

        return vBox;
    }

}
