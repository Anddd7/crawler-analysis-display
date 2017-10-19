package github.eddy.common;

import lombok.experimental.UtilityClass;

/**
 * @author edliao
 */
@UtilityClass
public class ThreadTools {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
