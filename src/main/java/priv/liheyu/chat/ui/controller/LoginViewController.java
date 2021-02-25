package priv.liheyu.chat.ui.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import priv.liheyu.chat.base.SessionInfo;
import priv.liheyu.chat.base.UiBaseService;
import priv.liheyu.chat.entity.ChatStatus;
import priv.liheyu.chat.entity.TransferInfo;
import priv.liheyu.chat.io.IOStream;
import priv.liheyu.chat.logic.ClientHandler;
import priv.liheyu.chat.ui.ControlledStage;
import priv.liheyu.chat.ui.StageController;
import priv.liheyu.chat.ui.container.ResourceContainer;
import priv.liheyu.chat.ui.R;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import priv.liheyu.chat.util.NumberUtil;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author xunmi
 * @Title: LoginViewController
 * @ProjectName java
 * @Description: TODO
 * @date 2019/5/25 9:37
 */


public class LoginViewController implements ControlledStage, Initializable {

    @FXML
    private Button login;
    @FXML
    private TextField userId;
    @FXML
    private PasswordField password;
    @FXML
    private ImageView closeBtn;
    @FXML
    private ImageView minBtn;
    @FXML
    private ProgressBar loginProgress;
    @FXML
    private Pane errorPane;
    @FXML
    private Label errorTips;

    @FXML
    private void gotoRegister() {
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        stageController.switchStage(R.id.RegisterView, R.id.LoginView);
    }

    @FXML
    public void login() {
        System.out.println("登录");
        loginProgress.setVisible(true);
        login.setVisible(false);
        final String useId = userId.getText();
        final String psw = password.getText();
        try {
            Socket socket = new Socket("127.0.0.1", 8888);
            TransferInfo tif = new TransferInfo();
            tif.setUserName(useId);
            tif.setPassword(psw);
            tif.setStatusEnum(ChatStatus.LOGIN);
            IOStream.writeMessage(socket, tif);
            Object obj = IOStream.readMessage(socket);
            if (obj instanceof TransferInfo) {
                TransferInfo tfi = (TransferInfo) obj;
                if (tfi.getLoginSuccessFlag()) {
                    SessionInfo.INSTANCE.setUserName(useId);
                    StageController stageController = UiBaseService.INSTANCE.getStageController();
                    stageController.switchStage(R.id.MainView, R.id.LoginView);
                    ClientHandler ch = new ClientHandler(socket);
                    SessionInfo.INSTANCE.setClientHandler(ch);
                    new Thread(ch).start();
                } else {
                    loginProgress.setVisible(false);
                    login.setVisible(true);
                    errorPane.setVisible(true);
                    errorTips.setText("登录失败");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeEntered() {
        System.out.println("进入");
        Image image = ResourceContainer.getClose_1();
        closeBtn.setImage(image);
    }

    @FXML
    private void closeExited() {
        System.out.println("出去");
        Image image = ResourceContainer.getClose();
        closeBtn.setImage(image);
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

    @FXML
    private void login_en() {
        System.out.println("进入");
        login.setStyle("-fx-background-radius:4;-fx-background-color: #097299");
    }

    @FXML
    private void login_ex() {
        System.out.println("出来");
        login.setStyle("-fx-background-radius:4;-fx-background-color: #09A3DC");
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
    private void backToLogin() {
        loginProgress.setVisible(false);
        errorPane.setVisible(false);
        login.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //验证规则：　userId非空　password非空
        login.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> userId.getText().length() == 0 ||
                                password.getText().length() == 0, userId.textProperty(), password.textProperty()
                )
        );
    }

    @Override
    public Stage getMyStage() {
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        return stageController.getStageBy(R.id.LoginView);
    }

    public void login_(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            if (NumberUtil.isInteger(userId.getText()) && password.getText().length() != 0 && userId.getText().length() != 0) {
                login();
            } else {
                errorTips.setText("账号需为纯数字且非空,密码非空");
                errorTips.setVisible(true);
            }
        }
    }
}

