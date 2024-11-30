package com.yuzhihao.learn.ui.dialog;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import javafx.util.StringConverter;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 设备增改弹窗
 *
 * @author yuzhihao
 */
@Log4j2
public class SysDeviceDialog {

    private final List<Pair<String, String>> FIRM_LIST = new ArrayList<>();

    private final boolean isUpdate;

    private final SysDevice sysDevice;

    private final Dialog<ButtonType> dialog = new Dialog<>();
    private final TextField nameField = new TextField();
    private final ChoiceBox<Pair<String, String>> firmField = new ChoiceBox<>();
    private final TextField ipField = new TextField();
    private final TextField passwordField = new TextField();
    private final TextField accountField = new TextField();

    {
        FIRM_LIST.addAll(SpringUtils.getBean(ISysDictService.class).manufacturer());
    }

    {
        firmField.setPrefWidth(200);
        firmField.setValue(FIRM_LIST.get(0));
        firmField.getItems().addAll(FIRM_LIST);
        firmField.setConverter(new StringConverter<>() {
            @Override
            public String toString(Pair<String, String> object) {
                return object.getKey();
            }

            @Override
            public Pair<String, String> fromString(String string) {
                return FIRM_LIST.stream().filter(e -> e.getValue().equals(string)).findFirst().get();
            }
        });
    }

    public SysDeviceDialog() {
        this(false);
    }

    public SysDeviceDialog(boolean isUpdate) {
        this.isUpdate = isUpdate;
        this.sysDevice = new SysDevice();
    }

    public SysDeviceDialog(SysDevice device) {
        this.isUpdate = true;
        this.nameField.setText(device.getCustomName());
        this.ipField.setText(device.getIp());
        this.accountField.setText(device.getUsername());
        this.passwordField.setText(device.getPassword());
        this.firmField.setValue(FIRM_LIST.stream().filter(e -> e.getValue().equals(device.getCompany())).findFirst().get());
        this.sysDevice = device;
    }

    public Optional<ButtonType> show() {
        return showp();
    }

    public Optional<ButtonType> showp() {

        dialog.setAutoHide(false);

        ImageView gluonLogo = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/icons/camera.png")).toExternalForm()));
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
        log.info("设备名称:{},设备厂商:{},设备IP:{},设备账号:{},设备密码:{}"
                , this.nameField.getText()
                , this.firmField.getValue().getKey()
                , this.ipField.getText()
                , this.accountField.getText()
                , this.passwordField.getText()
        );

        if (!StringUtils.hasLength(this.nameField.getText())) {
            new com.gluonhq.charm.glisten.control.Alert(Alert.AlertType.WARNING, "请输入设备名称！").showAndWait();
            return;
        }
        if (!StringUtils.hasLength(this.ipField.getText())) {
            new com.gluonhq.charm.glisten.control.Alert(Alert.AlertType.WARNING, "请输入设备IP！").showAndWait();
            return;
        }
        if (!StringUtils.hasLength(this.accountField.getText())) {
            new com.gluonhq.charm.glisten.control.Alert(Alert.AlertType.WARNING, "请输入设备账号！").showAndWait();
            return;
        }
        if (!StringUtils.hasLength(this.passwordField.getText())) {
            new com.gluonhq.charm.glisten.control.Alert(Alert.AlertType.WARNING, "请输入设备密码！").showAndWait();
            return;
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
        sysDevice.setCompany(firmField.getValue().getValue());
        sysDevice.setUsername(accountField.getText());
        sysDevice.setPassword(passwordField.getText());
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
        sysDevice.setCompany(firmField.getValue().getValue());
        sysDevice.setUsername(accountField.getText());
        sysDevice.setPassword(passwordField.getText());
        proxyUrl(sysDevice);
        sysDevice.insert();
    }

    private void proxyUrl(SysDevice device) {
        SysDict sysDict = SpringUtils.getBean(ISysDictService.class).manufacturerGet(device.getCompany());
        String url = sysDict.getRemark()
                .replace("${username}", device.getUsername())
                .replace("${password}", device.getPassword())
                .replace("${ip}", device.getIp());
        device.setProxyUrl(url);
    }


    public Node getContent() {
        VBox vBox = new VBox();

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(10));
        gp.setHgap(10);
        gp.setVgap(10);

        Label nameLabel = UiUtil.label("设备名称", MaterialDesignIcon.STARS.graphic());
        Label firmLabel = UiUtil.label("设备厂商", MaterialDesignIcon.STARS.graphic());
        Label ipLabel = UiUtil.label("设备IP", MaterialDesignIcon.STARS.graphic());
        Label accountLabel = UiUtil.label("设备账号", MaterialDesignIcon.STARS.graphic());
        Label passwordLabel = UiUtil.label("设备密码", MaterialDesignIcon.STARS.graphic());

        gp.add(nameLabel, 0, 0);
        gp.add(nameField, 1, 0);
        gp.add(firmLabel, 0, 1);
        gp.add(firmField, 1, 1);
        gp.add(ipLabel, 0, 2);
        gp.add(ipField, 1, 2);
        gp.add(accountLabel, 0, 3);
        gp.add(accountField, 1, 3);
        gp.add(passwordLabel, 0, 4);
        gp.add(passwordField, 1, 4);

        vBox.getChildren().add(gp);
        VBox.setVgrow(gp, Priority.ALWAYS);

        return vBox;
    }


}
