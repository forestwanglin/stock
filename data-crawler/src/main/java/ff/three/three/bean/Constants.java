package ff.three.three.bean;

/**
 * @author Forest Wang
 * @package io.merculet.config
 * @class Constants
 * @email forest@magicwindow.cn
 * @date 2018/7/5 11:10
 * @description
 */
public interface Constants {

//    /String COOKIE = "_ga=GA1.2.1936212577.1537085820; device_id=36d2ef5cafd4ba1153dc2b8b63314bd6; s=el1vnrpseg; _gid=GA1.2.1013820095.1537622075; Hm_lvt_1db88642e346389874251b5a1eded6e3=1537085820,1537719037,1537719221,1537719224; xq_a_token=776387e115646e8a4dcf81553387afac7c5a0279; xq_a_token.sig=5SWm2kWrzAOTikx7CWCYDxJo-3o; xq_r_token=9aa288619afa6f30f122dbef0f3344cd50457099; xq_r_token.sig=OeiO8iiNsHe_ULfkOZX6eIdXtZI; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1537973359; u=611537973359555; _gat_gtag_UA_16079156_4=1";

    String COOKIE = "_ga=GA1.2.1936212577.{NOW}; device_id=36d2ef5cafd4ba1153dc2b8b63314bd6; s=el1vnrpseg; _gid=GA1.2.1013820095.{PRE2DAYS}; Hm_lvt_1db88642e346389874251b5a1eded6e3={NOW}; xq_a_token=776387e115646e8a4dcf81553387afac7c5a0279; xq_a_token.sig=5SWm2kWrzAOTikx7CWCYDxJo-3o; xq_r_token=9aa288619afa6f30f122dbef0f3344cd50457099; xq_r_token.sig=OeiO8iiNsHe_ULfkOZX6eIdXtZI; Hm_lpvt_1db88642e346389874251b5a1eded6e3={NEXT2DAYS}; u=611537973359555; _gat_gtag_UA_16079156_4=1";

    String PREFIX_HU = "SH";

    String PREFIX_SHEN = "SZ";


    String SH_INDEX_SYMBOL = "SH000001";
    String SZ_INDEX_SYMBOL = "SZ399001";
    String CYB_INDEX_SYMBOL = "SZ399006";


    int DEFAULT_CHECK_FALLING_DAYS = 5;

}
