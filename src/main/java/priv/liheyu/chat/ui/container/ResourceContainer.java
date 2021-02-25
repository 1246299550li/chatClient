package priv.liheyu.chat.ui.container;

/**
 * @author xunmi
 * @Title: ResourceContainer
 * @ProjectName java
 * @Description: TODO
 * @date 2019/5/25 9:36
 */

import javafx.scene.image.Image;

public class ResourceContainer {

    private static Image close = getImage("login/img/close.png");
    private static Image close_1 = getImage("login/img/close_1.png");
    private static Image min = getImage("login/img/min.png");
    private static Image min_1 = getImage("login/img/min_1.png");
    private static Image headImg = getImage("login/img/head.jpg");

    public static Image getClose() {
        return close;
    }

    public static Image getClose_1() {
        return close_1;
    }

    public static Image getMin() {
        return min;
    }

    public static Image getMin_1() {
        return min_1;
    }

    public static Image getHeadImg() {
        return headImg;
    }

    public static Image getImage(String resourcePath) {
        return new Image(String.valueOf(Thread.currentThread().getContextClassLoader().getResource(resourcePath)));
    }
}