package ff.three.three.service.crawler;

import cn.magicwindow.common.exception.HttpServiceException;
import cn.magicwindow.common.exception.MwException;
import cn.magicwindow.common.service.IService;
import cn.magicwindow.common.util.AsyncHttpUtils;
import cn.magicwindow.common.util.DateUtils;
import cn.magicwindow.common.util.ThreadUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ff.three.three.service.entity.TxnDayService;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ff.three.three.bean.Constants.COOKIE;
import static ff.three.three.bean.Constants.SH_INDEX_SYMBOL;

/**
 * @author Forest Wang
 * @package ff.three.three.service.crawler
 * @class TxnDayCrawlerService
 * @email forest@magicwindow.cn
 * @date 2018/9/25 10:32
 * @description
 */
@Service
public class TxnDayCrawlerService implements IService {


    private static final Logger LOGGER = LoggerFactory.getLogger(TxnDayCrawlerService.class);

    private static final int MAX_TRY_COUNT = 3;

    @Autowired
    private TxnDayService txnDayService;

    public void crawl() throws MwException {
        JSONObject data = crawlSH();
        if (data.getInteger("error_code") == 0) {
            List<JSONObject> items = data.getJSONObject("data").getJSONArray("items").toJavaList(JSONObject.class);
            Date latestDate = new Date(items.get(0).getTimestamp("timestamp").getTime());
            this.txnDayService.txnDayUpdate(DateUtils.format(latestDate, DateUtils.FORMAT_D_3));
        } else {
            LOGGER.error("network error for date");
        }
    }

    public JSONObject crawlSH() {
        return crawlSH(1);
    }

    private JSONObject crawlSH(int tryCount) {
        if (tryCount > MAX_TRY_COUNT) {
            LOGGER.error("more than max count: {}", MAX_TRY_COUNT);
            return new JSONObject();
        }

        Map<String, String> queryParams = new HashMap<>(2);
        queryParams.put("symbol", SH_INDEX_SYMBOL);
        queryParams.put("period", "1d");

        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        httpHeaders.add("Cookie", COOKIE);

        try {
            String data = AsyncHttpUtils.syncGet("https://stock.xueqiu.com/v5/stock/chart/minute.json", queryParams, httpHeaders);
            return (JSONObject) JSON.parse(data);
        } catch (HttpServiceException e) {
            LOGGER.error("error", e);
            ThreadUtils.sleep(100L);
            return crawlSH(++tryCount);
        }
    }
}