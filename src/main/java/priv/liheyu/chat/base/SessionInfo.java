package priv.liheyu.chat.base;

import priv.liheyu.chat.logic.ClientHandler;


/**
 * @author xunmi
 * @Title: SessionInfo
 * @ProjectName java
 * @Description: TODO
 * @date 2019/7/2 14:47
 */
public enum SessionInfo {

    INSTANCE;

    private String userName;

    private ClientHandler clientHandler;

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
