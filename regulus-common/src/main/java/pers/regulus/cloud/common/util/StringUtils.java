package pers.regulus.cloud.common.util;

/**
 * String Helper
 *
 * @author Regulus
 */
public class StringUtils {

    public static String getObjectValue(Object obj) {
        return obj == null ? "" : obj.toString();
    }

}
