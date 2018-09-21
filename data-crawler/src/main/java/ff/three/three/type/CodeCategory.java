package ff.three.three.type;

/**
 * @author Forest Wang
 * @package ff.three.three.type
 * @class CodeCategory
 * @email forest@magicwindow.cn
 * @date 2018/9/20 22:33
 * @description
 */
public enum CodeCategory {


    /**
     * 创业板
     * 创业板的代码是300打头的股票代码
     */
    CHUANG_YE_BAN(10, "创业板"),

    /**
     * 沪市A股
     * 沪市A股的代码是以600、601或603打头
     */
    HU_A(20, "沪市A股"),

    /**
     * 沪市B股的代码是以900打头
     */
    HU_B(25, "沪市B股"),


    /**
     * 深市A股
     * 深市A股的代码是以000打头
     */
    SHEN_A(30, "深市A股"),

    /**
     * 深圳B股
     * 深圳B股的代码是以200打头
     */
    SHEN_B(35, "深圳B股"),

    /**
     * 中小板
     * 中小板的代码是002打头
     */
    ZHONG_XIAO_BAN(40, "中小板"),

    /**
     * 新股申购
     * 沪市新股申购的代码是以730打头
     * 深市新股申购的代码与深市股票买卖代码一样
     */
    NEW(50, "新股申购"),

    /**
     * 配股代码
     * 沪市以700打头，
     * 深市以080打头 权证，
     * 沪市是580打头 深市是031打头
     */
    PEI_GU(60, "配股代码"),

    /**
     * 其他种类
     */
    OTHER(10000, "其他");

    private int value;

    private String txt;

    CodeCategory(int value, String txt) {
        this.value = value;
        this.txt = txt;
    }

    public static CodeCategory fromCode(String code) {
        if (code.length() == 6) {
            if (code.startsWith("300")) {
                return CHUANG_YE_BAN;
            }
            if (code.startsWith("600") || code.startsWith("601") || code.startsWith("603")) {
                return HU_A;
            }
            if (code.startsWith("900")) {
                return HU_B;
            }
            if (code.startsWith("000")) {
                return SHEN_A;
            }
            if (code.startsWith("200")) {
                return SHEN_B;
            }
            if (code.startsWith("002")) {
                return ZHONG_XIAO_BAN;
            }
            if (code.startsWith("730")) { //TODO
                return NEW;
            }
            if (code.startsWith("700") || code.startsWith("080") || code.startsWith("580") || code.startsWith("031")) {
                return PEI_GU;
            }
        }
        return OTHER;
    }

}
