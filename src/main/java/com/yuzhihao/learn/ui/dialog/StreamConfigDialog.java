package com.yuzhihao.learn.ui.dialog;

import com.gluonhq.charm.glisten.control.Dialog;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.control.Toast;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.yuzhihao.learn.config.SpringUtils;
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
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * 流URL配置改弹窗
 *
 * @author yuzhihao
 */
@Log4j2
public class StreamConfigDialog {

    private final boolean isUpdate;

    private final SysDict dict;

    private final Dialog<ButtonType> dialog = new Dialog<>();
    private final TextField nameField = new TextField();
    private final TextField urlField = new TextField();

    public StreamConfigDialog() {
        this(false);
    }

    public StreamConfigDialog(boolean isUpdate) {
        this.isUpdate = isUpdate;
        this.dict = new SysDict();
    }

    public StreamConfigDialog(SysDict dict) {
        this.isUpdate = true;
        this.nameField.setText(dict.getDictLabel());
        this.urlField.setText(dict.getRemark());
        this.dict = dict;
    }

    public Optional<ButtonType> show() {
        return showp();
    }

    public Optional<ButtonType> showp() {

        dialog.setAutoHide(false);

        ImageView gluonLogo = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/icons/camera.png")).toExternalForm()));
        gluonLogo.setSmooth(true);

        Label title = new Label(isUpdate ? "修改配置" : "添加配置");
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
        log.info("流配置名称:{},流配置URL:{}"
                , this.nameField.getText()
                , this.urlField.getText()
        );

        if (!StringUtils.hasLength(this.nameField.getText())) {
            new com.gluonhq.charm.glisten.control.Alert(Alert.AlertType.WARNING, "请输入流配置名称！").showAndWait();
            return;
        }
        if (!StringUtils.hasLength(this.urlField.getText())) {
            new com.gluonhq.charm.glisten.control.Alert(Alert.AlertType.WARNING, "请输入流配置URL！").showAndWait();
            return;
        }

        if(isUpdate){
            update();
        }else {
            insert();
        }
        dialog.setResult(ButtonType.OK);
        dialog.hide();
    }

    private void update() {
        try {
            dict.setDictLabel(nameField.getText());
            dict.setRemark(urlField.getText());
            SpringUtils.getBean(ISysDictService.class).manufacturerUpdate(dict);
        } catch (Exception e) {
            new Toast(e.getMessage()).show();
        }
    }

    private void insert() {
        SysDict dict = new SysDict();
        dict.setDictLabel(nameField.getText());
        dict.setDictValue(nameField.getText());
        dict.setDictType("sys_manufacturer");
        dict.setDictSort(1);
        dict.setRemark(urlField.getText());
        dict.setCreateTime(LocalDateTime.now());

        try {
            SpringUtils.getBean(ISysDictService.class).manufacturerInsert(dict);
        } catch (Exception e) {
            new Toast(e.getMessage()).show();
        }
    }

    public Node getContent() {
        VBox vBox = new VBox();

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(10));
        gp.setHgap(10);
        gp.setVgap(10);

        Label nameLabel = UiUtil.label("名称", MaterialDesignIcon.STARS.graphic());
        Label urlLabel = UiUtil.label("规则字符串", MaterialDesignIcon.STARS.graphic());

        gp.add(nameLabel, 0, 0);
        gp.add(nameField, 1, 0);
        gp.add(urlLabel, 0, 1);
        gp.add(urlField, 1, 1);

        vBox.getChildren().add(gp);
        VBox.setVgrow(gp, Priority.ALWAYS);

        return vBox;
    }


}
