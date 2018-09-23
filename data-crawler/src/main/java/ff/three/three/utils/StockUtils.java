package ff.three.three.utils;

import ff.three.three.domain.Stock;
import ff.three.three.type.CodeCategory;

/**
 * @author Forest Wang
 * @package ff.three.three.utils
 * @class StockUtils
 * @email forest@magicwindow.cn
 * @date 2018/9/23 21:38
 * @description
 */
public class StockUtils {


    public static boolean need2Deal(Stock stock) {
        return stock.getCodeCategory() == CodeCategory.HU_A ||
                stock.getCodeCategory() == CodeCategory.SHEN_A ||
                stock.getCodeCategory() == CodeCategory.CHUANG_YE_BAN ||
                stock.getCodeCategory() == CodeCategory.ZHONG_XIAO_BAN;
    }
}
