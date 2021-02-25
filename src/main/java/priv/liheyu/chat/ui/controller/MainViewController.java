package priv.liheyu.chat.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import priv.liheyu.chat.base.SessionInfo;
import priv.liheyu.chat.base.UiBaseService;
import priv.liheyu.chat.entity.ChatStatus;
import priv.liheyu.chat.entity.TransferInfo;
import priv.liheyu.chat.io.IOStream;
import priv.liheyu.chat.ui.ControlledStage;
import priv.liheyu.chat.ui.R;
import priv.liheyu.chat.ui.StageController;
import priv.liheyu.chat.ui.container.ResourceContainer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * @author xunmi
 * @Title: MainViewController
 * @ProjectName java
 * @Description: TODO
 * @date 2019/6/2 18:09
 */
public class MainViewController implements ControlledStage, Initializable {
    @FXML
    public ImageView minBtn;
    @FXML
    public ImageView closeBtn;
    @FXML
    public VBox list;
    @FXML
    public HBox chatUI;
    @FXML
    public AnchorPane pane;
    @FXML
    public Label user;

    public Map<String, ChatView> chatUIList = new HashMap<>();
    public Map<String, ChatItem> itemList = new HashMap<>();

    public static MainViewController instance;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        instance = this;

        ChatItem all = new ChatItem();
        all.setText("All");
        Image img = ResourceContainer.getImage("image/p (" + 1 + ").png");
        all.setImage(img);
        all.setSelected(true);
        itemList.put("All", all);
        list.getChildren().add(all);

        ChatView cv = new ChatView();
        cv.setReceiver("All");
        chatUI.getChildren().add(cv);
        chatUIList.put("All", cv);
    }

    public void reFreshList(String[] userList) {
        user.setText(SessionInfo.INSTANCE.getUserName());
        for (String user : userList) {
            if (user.equals(SessionInfo.INSTANCE.getUserName())) {
                continue;
            }
            if (!chatUIList.containsKey(user)) {
                ChatView cv = new ChatView();
                cv.setReceiver(user);
                chatUIList.put(user, cv);
            }
        }
        Node all = list.getChildren().get(0);

        list.getChildren().clear();
        itemList.clear();

        list.getChildren().add(all);
        itemList.put("All", (ChatItem)all);

        for (String user : userList) {
            if (user.equals(SessionInfo.INSTANCE.getUserName())) {
                continue;
            }
            ChatItem cItem = new ChatItem();
            cItem.setText(user);
            Random r = new Random();
            int rand = r.nextInt(59) + 1;
            Image img = ResourceContainer.getImage("image/p (" + rand + ").png");
            cItem.setImage(img);
            itemList.put(user, cItem);
            list.getChildren().add(cItem);
        }

    }

    @Override
    public Stage getMyStage() {
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        return stageController.getStageBy(R.id.MainView);
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
    private void close() {
        TransferInfo tfi = new TransferInfo();
        tfi.setUserName(SessionInfo.INSTANCE.getUserName());
        tfi.setSender(SessionInfo.INSTANCE.getUserName());
        tfi.setStatusEnum(ChatStatus.QUIT);
        IOStream.writeMessage(SessionInfo.INSTANCE.getClientHandler().socket, tfi);
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
    private void minEntered() {
        Image image = ResourceContainer.getMin_1();
        minBtn.setImage(image);
    }

    @FXML
    private void minExited() {
        Image image = ResourceContainer.getMin();
        minBtn.setImage(image);
    }
}

