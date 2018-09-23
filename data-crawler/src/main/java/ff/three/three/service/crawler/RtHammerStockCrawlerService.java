package ff.three.three.service.crawler;

import cn.magicwindow.common.exception.HttpServiceException;
import cn.magicwindow.common.exception.MwException;
import cn.magicwindow.common.service.IService;
import cn.magicwindow.common.util.AsyncHttpUtils;
import cn.magicwindow.common.util.DateUtils;
import cn.magicwindow.common.util.Preconditions;
import cn.magicwindow.common.util.ThreadUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ff.three.three.domain.FallingStock;
import ff.three.three.service.analysis.HammerLineService;
import ff.three.three.service.entity.FallingStockService;
import ff.three.three.service.entity.HammerStockCandidateService;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ff.three.three.bean.Constants.COOKIE;

/**
 * @author Forest Wang
 * @package ff.three.three.service.crawler
 * @class RtHammerStockCrawlerService
 * @email forest@magicwindow.cn
 * @date 2018/9/23 22:19
 * @description
 */
@Service
public class RtHammerStockCrawlerService implements IService {


    private static final Logger LOGGER = LoggerFactory.getLogger(RtHammerStockCrawlerService.class);

    private static final int MAX_TRY_COUNT = 3;

    @Autowired
    private FallingStockService fallingStockService;

    @Autowired
    private HammerLineService hammerLineService;

    @Autowired
    private HammerStockCandidateService hammerStockCandidateService;

    public void crawl() throws MwException {
        String lastDate = DateUtils.format(DateUtils.addDays(DateUtils.now(), -1), DateUtils.FORMAT_D_3);
        this.crawl(lastDate);
    }

    public void crawl(String date) throws MwException {
        List<FallingStock> list = this.fallingStockService.queryByDate(date);
        if (Preconditions.isNotBlank(list)) {
            for (FallingStock fallingStock : list) {
                JSONObject data = crawlOneStock(fallingStock.getSymbol());
                if (data.getInteger("error_code") == 0) {
                    List<JSONObject> items = data.getJSONObject("data").getJSONArray("items").toJavaList(JSONObject.class);
                    List<Double> prices = new ArrayList<>();
                    double low = 0;
                    double high = 0;
                    for (JSONObject jsonObject : items) {
                        double price = jsonObject.getDouble("current");
                        prices.add(price);
                        if (low == 0 && high == 0) {
                            low = price;
                            high = price;
                        } else {
                            if (low >= price) {
                                low = price;
                            }
                            if (high <= price) {
                                high = price;
                            }
                        }
                    }
                    double open = prices.get(0);
                    double close = prices.get(prices.size() - 1);
                    List<String> hammerStockSymbols = new ArrayList<>();
                    if (this.hammerLineService.isHammerDay(open, close, low, high)) {
                        LOGGER.info("BUY!!! {}", fallingStock.getSymbol());
                        hammerStockSymbols.add(fallingStock.getSymbol());
                    }
                    this.hammerStockCandidateService.updateList(hammerStockSymbols);
                } else {
                    LOGGER.error("network error for stock {}", fallingStock.getSymbol());
                }
            }
        }
    }

    public JSONObject crawlOneStock(String symbol) {
        return crawlOneStock(symbol, 1);
    }

    private JSONObject crawlOneStock(String symbol, int tryCount) {
        if (tryCount > MAX_TRY_COUNT) {
            LOGGER.error("more than max count: {}", MAX_TRY_COUNT);
            return new JSONObject();
        }

        Map<String, String> queryParams = new HashMap<>(2);
        queryParams.put("symbol", symbol);
        queryParams.put("period", "1d");

        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        httpHeaders.add("Cookie", COOKIE);

        try {
            String data = AsyncHttpUtils.syncGet("https://stock.xueqiu.com/v5/stock/chart/minute.json", queryParams, httpHeaders);
            return (JSONObject) JSON.parse(data);
        } catch (HttpServiceException e) {
            LOGGER.error("error", e);
            ThreadUtils.sleep(100L);
            return crawlOneStock(symbol, ++tryCount);
        }
    }
}
