package priv.liheyu.chat.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import priv.liheyu.chat.base.SessionInfo;
import priv.liheyu.chat.entity.ChatStatus;
import priv.liheyu.chat.entity.TransferInfo;
import priv.liheyu.chat.io.IOStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xunmi
 * @Title: ChatView
 * @ProjectName java
 * @Description: TODO
 * @date 2019/7/2 18:00
 */
public class ChatView extends StackPane {

    private TextArea sendText = new TextArea();

    private Label receiver = new Label();

    private VBox chatAre = new VBox();

    private ScrollPane scrollPane = new ScrollPane();

    ChatView() {
        VBox vBox = new VBox();
        vBox.setPrefHeight(350);
        vBox.setPrefWidth(450);

        AnchorPane anchorPane1 = new AnchorPane();
        anchorPane1.setPrefHeight(40);
        anchorPane1.setPrefWidth(450);
        anchorPane1.setStyle("-fx-background-color: #DBE6F2;");

        receiver.setLayoutX(22);
        receiver.setLayoutY(6);
        receiver.setPrefHeight(30);
        receiver.setPrefWidth(133);
        receiver.setFont(Font.font(18));
        anchorPane1.getChildren().add(receiver);

        scrollPane.setPrefHeight(220);
        scrollPane.setPrefWidth(450);

        chatAre.setPrefWidth(430);
        chatAre.setPrefHeight(300);
        scrollPane.setContent(chatAre);

        AnchorPane anchorPane2 = new AnchorPane();
        anchorPane2.setPrefHeight(90);
        anchorPane2.setPrefWidth(450);

        sendText.setPrefHeight(90);
        sendText.setPrefWidth(450);
        sendText.setFont(Font.font(17));


        Button bt = new Button("发送");
        bt.setPrefHeight(28);
        bt.setPrefWidth(60);
        bt.setStyle("-fx-background-color: #4bb2ff;");
        bt.setLayoutX(368);
        bt.setLayoutY(48);
        bt.setOnAction(new Send());

        anchorPane2.getChildren().addAll(sendText, bt);

        vBox.getChildren().addAll(anchorPane1, scrollPane, anchorPane2);

        sendText.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER && sendText.isFocused()) {
                if (sendText.getText().equals("")) {
                    return;
                }
                TransferInfo tfi = new TransferInfo();
                tfi.setSender(SessionInfo.INSTANCE.getUserName());
                tfi.setReceiver(receiver.getText());
                if(receiver.getText().equals("All")){
                    tfi.setStatusEnum(ChatStatus.NOTICE);
                    tfi.setNotice(sendText.getText());
                }else {
                    tfi.setStatusEnum(ChatStatus.CHAT);
                    tfi.setContent(sendText.getText());
                }
                sendText.setText("");
                IOStream.writeMessage(SessionInfo.INSTANCE.getClientHandler().socket, tfi);
            }
        });


        this.getChildren().add(vBox);
    }

    public void receiveInfo(String info, String name) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        HBox hBox = new HBox();
        Text n = new Text(" " + name + ":");
        n.setFont(Font.font(15));
        HBox time = new HBox(new Text(dateStr), n);
        hBox.setStyle("-fx-background-color: #00FFFF;-fx-background-radius: 12;");
        hBox.setMaxWidth(220);
        hBox.setSpacing(6);
        HBox box = new HBox();
        box.getChildren().add(hBox);
        if (name.equals(SessionInfo.INSTANCE.getUserName())) {
            time.setPadding(new Insets(0, 10, 0, 0));
            time.setAlignment(Pos.CENTER_RIGHT);

            hBox.setPadding(new Insets(5, 15, 5, 15));
            box.setAlignment(Pos.CENTER_RIGHT);
            hBox.setAlignment(Pos.CENTER_RIGHT);
        } else {
            time.setPadding(new Insets(0, 0, 0, 10));
            time.setAlignment(Pos.CENTER_LEFT);

            hBox.setPadding(new Insets(5, 5, 5, 15));
            box.setAlignment(Pos.CENTER_LEFT);
        }
        Text text = new Text();
        text.setText(info);
        text.setFont(Font.font(18));
        text.setWrappingWidth(200);
        hBox.getChildren().add(text);
        chatAre.getChildren().addAll(time, box);
        scrollPane.setVvalue(1);
    }


    private class Send implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (sendText.getText().equals("")) {
                return;
            }
            TransferInfo tfi = new TransferInfo();
            tfi.setSender(SessionInfo.INSTANCE.getUserName());
            tfi.setReceiver(receiver.getText());
            if(receiver.getText().equals("All")){
                tfi.setStatusEnum(ChatStatus.NOTICE);
                tfi.setNotice(sendText.getText());
            }else {
                tfi.setStatusEnum(ChatStatus.CHAT);
                tfi.setContent(sendText.getText());
            }
            sendText.setText("");
            IOStream.writeMessage(SessionInfo.INSTANCE.getClientHandler().socket, tfi);
        }
    }

    void setReceiver(String receiver) {
        this.receiver.setText(receiver);
    }

}
