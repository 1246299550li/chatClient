package priv.liheyu.chat.ui.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * @author xunmi
 * @Title: ChatItem
 * @ProjectName java
 * @Description: TODO
 * @date 2019/6/30 0:38
 */
public class ChatItem extends StackPane {
    private boolean mouseEntered = false;
    private boolean selected = false;
    private ImageView imageView = new ImageView();
    private HBox infoBox = new HBox();
    private Label name = new Label();

    ChatItem() {
        initComponent();
        iniEvent();
    }

    private void initComponent() {
        this.getChildren().add(infoBox);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        Rectangle clip = new Rectangle(
                imageView.getFitWidth(), imageView.getFitHeight()
        );
        clip.setArcWidth(50);
        clip.setArcHeight(50);
        imageView.setClip(clip);


        name.setFont(Font.font(20));
        infoBox.getChildren().add(imageView);

        infoBox.getChildren().add(new StackPane(name));
        this.updateBackground();
    }

    private void iniEvent() {
        this.setOnMouseEntered((Event event) -> {
            mouseEntered = true;
            updateBackground();
        });
        this.setOnMouseExited((Event event) -> {
            mouseEntered = false;
            updateBackground();
        });
        this.setOnMouseClicked(new Select());
    }

    void setText(String text) {
        name.setText(text);
    }

    void setImage(Image image) {
        imageView.setImage(image);
    }

    private void updateBackground() {
        if (selected) {
            this.setStyle("-fx-background-color:rgba(255, 255, 255, 0.8);-fx-background-radius: 3;");
        } else {
            if (mouseEntered) {
                this.setStyle("-fx-background-color:rgba(255, 255, 255, 0.3);-fx-background-radius: 3;");
            } else {
                this.setStyle("-fx-background-color:null;");
            }
        }
    }

    private class Select implements EventHandler<Event> {

        @Override
        public void handle(Event event) {
            MainViewController.instance.itemList.forEach((key, value) -> {
                value.setSelected(false);
            });
            setSelected(true);
            MainViewController.instance.chatUI.getChildren().remove(1);
            System.out.println(name.getText());
            Node cv = MainViewController.instance.chatUIList.get(name.getText());
            MainViewController.instance.chatUI.getChildren().add(cv);
            updateBackground();
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        if (this.selected != selected) {
            this.selected = selected;
            updateBackground();
        }
    }

}
