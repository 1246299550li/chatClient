package priv.liheyu.chat.logic;


import javafx.application.Platform;
import javafx.scene.Node;
import priv.liheyu.chat.base.SessionInfo;
import priv.liheyu.chat.entity.ChatStatus;
import priv.liheyu.chat.entity.TransferInfo;
import priv.liheyu.chat.io.IOStream;
import priv.liheyu.chat.ui.controller.MainViewController;
import java.io.IOException;
import java.net.Socket;


/**
 * @author xunmi
 * @Title: ClientHandler
 * @ProjectName java
 * @Description: TODO
 * @date 2019/7/2 14:42
 */
public class ClientHandler extends Thread {

    public Socket socket;

    //聊天主窗体
    private MainViewController contrl = MainViewController.instance;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                //模拟一直拿消息，产生阻塞
                Object obj = IOStream.readMessage(socket);
                if (obj instanceof TransferInfo) {
                    TransferInfo tfi = (TransferInfo) obj;
                    if (tfi.getStatusEnum() == ChatStatus.CHAT) {
                        System.out.println("收到私人聊天信息");
                        chatResult(tfi);
                    } else if (tfi.getStatusEnum() == ChatStatus.NOTICE) {
                        System.out.println("收到全体聊天信息");
                        noticeResult(tfi);
                    } else if (tfi.getStatusEnum() == ChatStatus.ULIST) {
                        //刷新当前在线用户列表
                        onlineUsersResult(tfi);
                    } else if (tfi.getStatusEnum() == ChatStatus.QUIT) {
                        System.out.println("收到下线信息");
                        IOStream.writeMessage(SessionInfo.INSTANCE.getClientHandler().socket, tfi);
                        Thread.sleep(1000);
                        System.exit(1);
                    }
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 接收私人消息
     */
    private void chatResult(TransferInfo tfi) {

        String sender = tfi.getSender();
        String receiver = tfi.getReceiver();
        String to;
        if (sender.equals(SessionInfo.INSTANCE.getUserName())) {
            to = receiver;
        } else {
            to = sender;
        }
        Platform.runLater(() -> {
            contrl.chatUIList.get(to).receiveInfo(tfi.getContent(), sender);

            contrl.itemList.forEach((key, value) -> value.setSelected(false));
            contrl.itemList.get(to).setSelected(true);
            contrl.chatUI.getChildren().remove(1);
            Node cv = contrl.chatUIList.get(to);
            contrl.chatUI.getChildren().add(cv);
        });

    }

    /**
     * 接收全体消息
     */
    private void noticeResult(TransferInfo tfi) {
        String sender = tfi.getSender();
        Platform.runLater(() -> {
            contrl.chatUIList.get("All").receiveInfo(tfi.getNotice(), sender);
            contrl.itemList.forEach((key, value) -> value.setSelected(false));
            contrl.itemList.get("All").setSelected(true);
            contrl.chatUI.getChildren().remove(1);
            Node cv = contrl.chatUIList.get("All");
            contrl.chatUI.getChildren().add(cv);
        });

    }

    /**
     * 刷新当前界面的用户列表
     */
    private void onlineUsersResult(TransferInfo tfi) {
        String[] userOnlineArray = tfi.getUserOnlineArray();
        Platform.runLater(() -> contrl.reFreshList(userOnlineArray));
    }

}
