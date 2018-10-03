package ff.three.three.utils;

import cn.magicwindow.common.bean.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

import static cn.magicwindow.common.bean.Constants.EMPTY_STRING;

/**
 * @author Forest Wang
 * @package ff.three.three.utils
 * @class NumberFormatUtils
 * @email forest@magicwindow.cn
 * @date 2018/9/24 17:26
 * @description
 */
public class NumberFormatUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(NumberFormatUtils.class);

    /**
     * @param d
     * @return 0.03888   +3.89%
     */
    public static String percentFormat(double d) {
        DecimalFormat df = new DecimalFormat("##0.00%");
        String data = df.format(d);
        data = String.format("%s%s", d >= 0 ? " " : EMPTY_STRING, data);
        return String.format("%s%s", data.length() == 8 ? EMPTY_STRING : (data.length() == 7 ? " " : "  "), data);
    }

    public static String formatTxYear(int year) {
        if (year >= 0 && year <= 99) {
            if (year < 10) {
                return String.format("0%d", year);
            } else {
                return String.format("%s", year);
            }
        }
        return Constants.EMPTY_STRING;
    }

//    public static void main(String... args) {
//        LOGGER.info(percentFormat(0.03444));
//        LOGGER.info(percentFormat(0.3444));
//        LOGGER.info(percentFormat(1.03444));
//        LOGGER.info(percentFormat(-0.03444));
//        LOGGER.info(percentFormat(-0.3444));
//        LOGGER.info(percentFormat(-1.3444));
//    }
}
