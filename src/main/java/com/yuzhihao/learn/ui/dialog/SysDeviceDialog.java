package com.yuzhihao.learn.ui.dialog;

import com.gluonhq.charm.glisten.control.Alert;
import com.gluonhq.charm.glisten.control.Dialog;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.yuzhihao.learn.config.SpringUtils;
import com.yuzhihao.learn.h2.entity.SysDevice;
import com.yuzhihao.learn.h2.entity.SysDict;
import com.yuzhihao.learn.h2.service.ISysDictService;
import com.yuzhihao.learn.ui.util.UiUtil;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 设备增改弹窗
 *
 * @author yuzhihao
 */
@Log4j2
public class SysDeviceDialog {

    private final Image cameraIcon = new Image("/images/icons/camera.png");
    private final boolean isUpdate;

    private final SysDevice sysDevice;

    private final Dialog<ButtonType> dialog = new Dialog<>();
    private final TextField nameField = new TextField();
    private final FlowPane firmField = new FlowPane();
    private final ToggleGroup firmGroup = new ToggleGroup();
    private final FlowPane typeField = new FlowPane();
    private final ToggleGroup typeGroup = new ToggleGroup();
    private final TextField ipField = new TextField();
    private final TextField passwordField = new TextField();
    private final TextField accountField = new TextField();
    private final TextArea urlField = new TextArea();

    private final Label nameLabel = UiUtil.label("名称", MaterialDesignIcon.STARS.graphic());

    private final Label urlLabel = UiUtil.label("地址", MaterialDesignIcon.STARS.graphic());

    private final Label typeLabel = UiUtil.label("类型", MaterialDesignIcon.STARS.graphic());

    private final Label firmLabel = UiUtil.label("厂商", MaterialDesignIcon.STARS.graphic());

    private final Label ipLabel = UiUtil.label("IP", MaterialDesignIcon.STARS.graphic());

    private final Label accountLabel = UiUtil.label("账号", MaterialDesignIcon.STARS.graphic());

    private final Label passwordLabel = UiUtil.label("密码", MaterialDesignIcon.STARS.graphic());

    public void labelInit() {
        nameLabel.setMinWidth(50);
        urlLabel.setMinWidth(50);
        typeLabel.setMinWidth(50);
        firmLabel.setMinWidth(50);
        ipLabel.setMinWidth(50);
        accountLabel.setMinWidth(50);
        passwordLabel.setMinWidth(50);
        urlField.setMaxWidth(500);
        urlField.setWrapText(true);
        urlField.setPrefHeight(100);
    }

    public void firmFieldInit() {
        firmField.setMaxWidth(500);
        List<String> pairs = SpringUtils.getBean(ISysDictService.class).manufacturer().stream().map(Pair::getKey).toList();
        firmGroup.setUserData(pairs.get(0));
        pairs.forEach(e -> {
            RadioButton button = new RadioButton(e);
            button.setToggleGroup(firmGroup);
            if (firmGroup.getUserData().equals(e)) {
                button.setSelected(true);
            }
            button.setUserData(e);
            firmField.getChildren().add(button);
        });

        if (StringUtils.hasLength(this.sysDevice.getCompany())) {
            firmGroup.getToggles().forEach(e -> {
                if (this.sysDevice.getCompany().equals(e.getUserData())) {
                    e.setSelected(true);
                    typeGroup.setUserData(e.getUserData());
                }
            });
        }
        // 处理选择事件
        firmGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                firmGroup.setUserData(newValue.getUserData());
                this.sysDevice.setCompany(newValue.getUserData().toString());
                this.sysDevice.setMediaType(typeGroup.getUserData().toString());
                proxyUrl(this.sysDevice);
                this.urlField.setText(this.sysDevice.getProxyUrl());
            }
        });
    }

    public void typeFieldInit() {

        typeField.setMaxWidth(500);
        List<String> pairs = SpringUtils.getBean(ISysDictService.class).mediaType().stream().map(Pair::getKey).toList();
        typeGroup.setUserData(pairs.get(0));
        pairs.forEach(e -> {
            RadioButton button = new RadioButton(e);
            if (typeGroup.getUserData().equals(e)) {
                button.setSelected(true);
            }
            button.setToggleGroup(typeGroup);
            button.setUserData(e);
            typeField.getChildren().add(button);
        });

        if (StringUtils.hasLength(this.sysDevice.getMediaType())) {
            typeGroup.getToggles().forEach(e -> {
                if (this.sysDevice.getMediaType().equals(e.getUserData())) {
                    e.setSelected(true);
                    typeGroup.setUserData(e.getUserData());
                }
            });
        }

        // 处理选择事件
        typeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                visible(newValue.getUserData());
                typeGroup.setUserData(newValue.getUserData());
            }
        });

        visible(this.typeGroup.getUserData());
    }

    public SysDeviceDialog() {
        this(false);
    }

    public SysDeviceDialog(boolean isUpdate) {
        this(isUpdate, new SysDevice());
    }

    public SysDeviceDialog(SysDevice device) {
        this(true, device);
    }

    public SysDeviceDialog(boolean isUpdate, SysDevice device) {
        this.isUpdate = isUpdate;
        this.sysDevice = device;

        if (StringUtils.hasLength(this.sysDevice.getCustomName())) {
            this.nameField.setText(this.sysDevice.getCustomName());
        }
        if (StringUtils.hasLength(this.sysDevice.getIp())) {
            this.ipField.setText(this.sysDevice.getIp());
        }
        if (StringUtils.hasLength(this.sysDevice.getUsername())) {
            this.accountField.setText(this.sysDevice.getUsername());
        }
        if (StringUtils.hasLength(this.sysDevice.getPassword())) {
            this.passwordField.setText(this.sysDevice.getPassword());
        }
        if (StringUtils.hasLength(this.sysDevice.getProxyUrl())) {
            this.urlField.setText(this.sysDevice.getProxyUrl());
        }

        ipField.textProperty().addListener((observable, oldValue, newValue) -> {
            sysDevice.setIp(newValue);
        });
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            sysDevice.setPassword(newValue);
        });
        accountField.textProperty().addListener((observable, oldValue, newValue) -> {
            sysDevice.setUsername(newValue);
        });
        urlField.textProperty().addListener((observable, oldValue, newValue) -> {
            sysDevice.setProxyUrl(newValue);
        });

        this.labelInit();

        this.firmFieldInit();

        this.typeFieldInit();

    }

    private void visible(Object val) {
        boolean equals = "摄像头".equals(val);

        ipField.setVisible(equals);
        ipField.setManaged(equals);
        ipLabel.setVisible(equals);
        ipLabel.setManaged(equals);

        accountLabel.setVisible(equals);
        accountLabel.setManaged(equals);
        accountField.setVisible(equals);
        accountField.setManaged(equals);

        passwordLabel.setVisible(equals);
        passwordLabel.setManaged(equals);
        passwordField.setVisible(equals);
        passwordField.setManaged(equals);


        firmField.setVisible(equals);
        firmField.setManaged(equals);
        firmLabel.setVisible(equals);
        firmLabel.setManaged(equals);

    }

    public Optional<ButtonType> show() {
        return showp();
    }

    public Optional<ButtonType> showp() {
        dialog.setAutoHide(false);

        ImageView gluonLogo = new ImageView(cameraIcon);
        gluonLogo.setSmooth(true);

        Label title = new Label(isUpdate ? "修改设备信息" : "添加设备");
        title.setStyle("-fx-font-size: 2.5em;");
        title.setGraphic(gluonLogo);
        title.setContentDisplay(ContentDisplay.LEFT);

        dialog.setTitle(title);

        dialog.setContent(getContent());

        Button closeBtn = new Button("关闭");
        closeBtn.setOnAction((e) -> {
            dialog.setResult(ButtonType.CANCEL);
            dialog.hide();
        });

        Button confirmBtn = new Button(isUpdate ? "确认修改" : "确认添加");
        confirmBtn.setOnAction((e) -> confirm());

        dialog.getButtons().addAll(closeBtn, confirmBtn);

        return dialog.showAndWait();

    }

    public void confirm() {
        log.info("设备名称:{},设备厂商:{},设备类型:{},设备IP:{},设备账号:{},设备密码:{}"
                , this.nameField.getText()
                , this.firmGroup.getUserData()
                , this.typeGroup.getUserData()
                , this.ipField.getText()
                , this.accountField.getText()
                , this.passwordField.getText()
        );

        if (!StringUtils.hasLength(this.nameField.getText())) {
            new Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "请输入设备名称！").showAndWait();
            return;
        }

        if ("摄像头".equals(this.typeGroup.getUserData())) {
            if (!StringUtils.hasLength(this.ipField.getText())) {
                new Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "请输入设备IP！").showAndWait();
                return;
            }
            if (!StringUtils.hasLength(this.accountField.getText())) {
                new Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "请输入设备账号！").showAndWait();
                return;
            }
            if (!StringUtils.hasLength(this.passwordField.getText())) {
                new Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "请输入设备密码！").showAndWait();
                return;
            }
            this.sysDevice.setCompany(null);
        } else {
            if (!StringUtils.hasLength(this.urlField.getText())) {
                new Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "请输入播放地址！").showAndWait();
                return;
            }
        }

        if (isUpdate) {
            update();
        } else {
            insert();
        }
        dialog.setResult(ButtonType.OK);
        dialog.hide();
    }

    private void update() {
        sysDevice.setCustomName(nameField.getText());
        sysDevice.setIp(ipField.getText());
        sysDevice.setCompany(firmGroup.getUserData().toString());
        sysDevice.setUsername(accountField.getText());
        sysDevice.setPassword(passwordField.getText());
        sysDevice.setMediaType(typeGroup.getUserData().toString());
        sysDevice.setProxyUrl(urlField.getText());
        proxyUrl(sysDevice);
        sysDevice.updateById();
    }

    private void insert() {
        SysDevice sysDevice = new SysDevice();
        sysDevice.setDeviceId(UUID.randomUUID().toString().toUpperCase().replace("-", ""));
        sysDevice.setRegisterTime(LocalDateTime.now());
        sysDevice.setKeepaliveTime(LocalDateTime.now());
        sysDevice.setCustomName(nameField.getText());
        sysDevice.setIp(ipField.getText());
        sysDevice.setCompany(firmGroup.getUserData().toString());
        sysDevice.setUsername(accountField.getText());
        sysDevice.setPassword(passwordField.getText());
        sysDevice.setMediaType(typeGroup.getUserData().toString());
        sysDevice.setProxyUrl(urlField.getText());
        proxyUrl(sysDevice);
        sysDevice.insert();
    }

    private void proxyUrl(SysDevice device) {
        if ("摄像头".equals(device.getMediaType())) {
            SysDict sysDict = SpringUtils.getBean(ISysDictService.class).manufacturerGet(device.getCompany());
            String url = sysDict.getRemark()
                    .replace("${username}", StringUtils.hasLength(device.getUsername()) ? device.getUsername() : "${username}")
                    .replace("${password}", StringUtils.hasLength(device.getPassword()) ? device.getPassword() : "${password}")
                    .replace("${ip}", StringUtils.hasLength(device.getIp()) ? device.getIp() : "${ip}");
            device.setProxyUrl(url);
        }
    }


    public Node getContent() {
        VBox vBox = new VBox();

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(10));
        gp.setHgap(10);
        gp.setVgap(10);

        gp.add(typeLabel, 0, 0);
        gp.add(typeField, 1, 0);

        gp.add(nameLabel, 0, 1);
        gp.add(nameField, 1, 1);

        gp.add(ipLabel, 0, 2);
        gp.add(ipField, 1, 2);

        gp.add(accountLabel, 0, 3);
        gp.add(accountField, 1, 3);

        gp.add(passwordLabel, 0, 4);
        gp.add(passwordField, 1, 4);

        gp.add(firmLabel, 0, 5);
        gp.add(firmField, 1, 5);

        gp.add(urlLabel, 0, 6);
        gp.add(urlField, 1, 6);


        vBox.getChildren().add(gp);
        VBox.setVgrow(gp, Priority.ALWAYS);

        return vBox;
    }


}
