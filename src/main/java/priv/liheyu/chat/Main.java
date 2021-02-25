package priv.liheyu.chat;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import priv.liheyu.chat.base.UiBaseService;
import priv.liheyu.chat.ui.R;
import priv.liheyu.chat.ui.StageController;

/**
 * @author xunmi
 * @Title: Main
 * @ProjectName javaClient
 * @Description: TODO
 * @date 2019/5/25 9:39
 */


public class Main extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        stageController.setPrimaryStage("root", stage);

        Stage loginStage = stageController.loadStage(R.id.LoginView, R.layout.LoginView, StageStyle.UNDECORATED);
        loginStage.setTitle("title");

        stageController.loadStage(R.id.RegisterView, R.layout.RegisterView, StageStyle.UNDECORATED);
        Stage mainStage = stageController.loadStage(R.id.MainView, R.layout.MainView, StageStyle.UNDECORATED);
        mainStage.setTitle("title");


        stageController.setStage(R.id.LoginView);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
