package priv.liheyu.chat.base;

/**
 * @author xunmi
 * @Title: UiBaseService
 * @ProjectName java
 * @Description: TODO
 * @date 2019/6/1 9:36
 */
import priv.liheyu.chat.ui.StageController;

public enum UiBaseService {

    INSTANCE;

    private StageController stageController = new StageController();

    public StageController getStageController() {
        return stageController;
    }

}
