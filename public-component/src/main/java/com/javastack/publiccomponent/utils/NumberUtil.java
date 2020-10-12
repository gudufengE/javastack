package com.javastack.publiccomponent.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 利用正则表达式判断字符串是否是数字,不以0开头的多个数字
     *
     * @param str
     * @return
     */
    public static boolean hasNumeric(String str) {
        Pattern pattern = Pattern.compile("^([1-9]\\d{0,9}[0-9]*)$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断金额，最多可小数点前后10位
     * 1==》true
     * 1.==》true
     * 1.1=》true
     * 1.11=》true
     * 1.111=》false
     * 0=》true
     * 0.=》true
     * 0.0=》true
     * 0.00000000000=》false
     * 5=》true
     *
     * @param str
     * @return
     */
    public static boolean isMoney(String str) {
        Pattern pattern = Pattern.compile("^\\d{0,10}\\.{0,1}(\\d{1,10})?$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

}
