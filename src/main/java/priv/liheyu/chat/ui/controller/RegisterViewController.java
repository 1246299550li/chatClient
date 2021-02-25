package priv.liheyu.chat.ui.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import priv.liheyu.chat.base.UiBaseService;
import priv.liheyu.chat.entity.ChatStatus;
import priv.liheyu.chat.entity.TransferInfo;
import priv.liheyu.chat.io.IOStream;
//import priv.liheyu.chat.logic.ClientHandler;
import priv.liheyu.chat.ui.ControlledStage;
import priv.liheyu.chat.ui.R;
import priv.liheyu.chat.ui.StageController;
import priv.liheyu.chat.ui.container.ResourceContainer;
import priv.liheyu.chat.util.NumberUtil;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author xunmi
 * @Title: RegisterViewController
 * @ProjectName java
 * @Description: TODO
 * @date 2019/6/1 13:39
 */

public class RegisterViewController implements ControlledStage, Initializable {

    @FXML
    private Button register;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorTips;
    @FXML
    private ImageView minBtn;
    @FXML
    private ImageView closeBtn;

    @FXML
    private void register() {
        String nickName = userName.getText();
        String psw = password.getText();
        try {
            Socket socket = new Socket("127.0.0.1", 8888);
            TransferInfo tif = new TransferInfo();
            tif.setUserName(nickName);
            tif.setPassword(psw);
            //本次处理的消息类型为注册
            tif.setStatusEnum(ChatStatus.REGISTER);
            IOStream.writeMessage(socket, tif);
            Object obj = IOStream.readMessage(socket);
            if (obj instanceof TransferInfo) {
                TransferInfo tfi = (TransferInfo) obj;
                if (tfi.getStatusEnum() != ChatStatus.REGISTER) {
                    errorTips.setText("用户已存在注册失败");
                    errorTips.setVisible(true);
                    return;
                } else {
                    errorTips.setText("注册成功");
                    errorTips.setVisible(true);
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void register_enter() {
        register.setStyle("-fx-background-radius:4;-fx-background-color: #097299");
    }

    @FXML
    private void register_exit() {
        register.setStyle("-fx-background-radius:4;-fx-background-color: #09A3DC");
    }

    @FXML
    private void close() {
        System.exit(1);
    }

    @FXML
    private void min() {
        Stage stage = getMyStage();
        if (stage != null) {
            stage.setIconified(true);
        }
    }

    @FXML
    private void closeEntered() {
        Image image = ResourceContainer.getClose_1();
        closeBtn.setImage(image);
    }

    @FXML
    private void closeExited() {
        Image image = ResourceContainer.getClose();
        closeBtn.setImage(image);
    }

    @FXML
    private void gotoLogin() {
        clearFields();
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        stageController.switchStage(R.id.LoginView, R.id.RegisterView);
    }

    private void clearFields() {
        userName.setText("");
        password.setText("");
        errorTips.setText("");
        errorTips.setVisible(false);
    }

    @FXML
    private void minEntered() {
        Image image = ResourceContainer.getMin_1();
        minBtn.setImage(image);
    }

    @FXML
    private void minExited() {
        Image image = ResourceContainer.getMin();
        minBtn.setImage(image);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //验证规则：　userName非空　password非空
        register.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> userName.getText().length() == 0 ||
                                password.getText().length() == 0, userName.textProperty(), password.textProperty()
                )
        );

    }

    @Override
    public Stage getMyStage() {
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        return stageController.getStageBy(R.id.RegisterView);
    }

    public void register_(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            if (NumberUtil.isInteger(userName.getText()) && password.getText().length() != 0 && userName.getText().length() != 0) {
                register();
            } else {
                errorTips.setText("账号需为纯数字且非空,密码非空");
                errorTips.setVisible(true);
            }
        }
    }

}