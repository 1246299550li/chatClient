package priv.liheyu.chat.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xunmi
 * @Title: NumberUtil
 * @ProjectName java
 * @Description: TODO
 * @date 2019/6/1 10:22
 */
public class NumberUtil {
    public static boolean isInteger(String input){
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }
}
