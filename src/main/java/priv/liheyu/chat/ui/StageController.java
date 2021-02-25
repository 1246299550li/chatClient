package priv.liheyu.chat.ui;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import priv.liheyu.chat.util.DragUtil;
import java.util.HashMap;
import java.util.Map;


/**
 * @author xunmi
 * @Title: StageController
 * @ProjectName java
 * @Description: TODO
 * @date 2019/6/1 9:34
 */
public class StageController {

    private Map<String, Stage> stages = new HashMap<>();

    private Map<String, ControlledStage> controllers = new HashMap<>();

    private void addStage(String name, Stage stage) {
        stages.put(name, stage);
    }

    public Stage getStageBy(String name) {
        return this.stages.get(name);
    }


    public void setPrimaryStage(String name, Stage stage) {
        addStage(name, stage);
    }

    public Stage loadStage(String name, String resource, StageStyle... styles) {
        Stage result = null;
        try {
            FXMLLoader loader = new FXMLLoader(Thread.currentThread().getContextClassLoader().getResource(resource));
            ControlledStage controlledStage = loader.getController();
            controllers.put(name, controlledStage);
            result = new Stage();
            Parent pane = loader.load();

            result.setScene(new Scene(pane));
            DragUtil.addDragListener(result, pane);

            for (StageStyle style : styles) {
                result.initStyle(style);
            }
            addStage(name, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setStage(String name) {
        Stage stage = getStageBy(name);
        if (stage == null) {
            return;
        }
        stage.show();
    }

    public void switchStage(String toShow, String toClose) {
        System.out.println("switchStage1" + toClose);
        getStageBy(toClose).close();
        System.out.println("switchStage2");
        setStage(toShow);

    }

    public void closeStage(String name) {
        Stage target = getStageBy(name);
        target.close();
    }

    public ControlledStage getController(String name) {
        return this.controllers.get(name);
    }

}