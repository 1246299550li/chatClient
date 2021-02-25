package priv.liheyu.chat.util;

import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * @author xunmi
 * @Title: DragUtil
 * @ProjectName java
 * @Description: TODO
 * @date 2019/7/3 3:12
 */

public class DragUtil {
    public static void addDragListener(Stage stage, Node root) {
        new DragListener(stage).enableDrag(root);
    }
}
