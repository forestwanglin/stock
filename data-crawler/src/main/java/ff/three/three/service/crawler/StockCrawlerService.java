package ff.three.three.service.crawler;

import cn.magicwindow.common.bean.Constants;
import cn.magicwindow.common.exception.HttpServiceException;
import cn.magicwindow.common.exception.MwException;
import cn.magicwindow.common.service.IService;
import cn.magicwindow.common.util.AsyncHttpUtils;
import cn.magicwindow.common.util.Preconditions;
import cn.magicwindow.common.util.ThreadUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ff.three.three.domain.Stock;
import ff.three.three.service.entity.StockService;
import ff.three.three.type.CodeCategory;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ff.three.three.bean.Constants.COOKIE;

/**
 * @author Forest Wang
 * @package ff.three.three.service.entity
 * @class StockCrawler
 * @email forest@magicwindow.cn
 * @date 2018/9/20 23:03
 * @description
 */
@Service
public class StockCrawlerService implements IService {


    private static final Logger LOGGER = LoggerFactory.getLogger(StockCrawlerService.class);


    private static final int PAGE_SIZE = 100;


    private static final int MAX_TRY_COUNT = 3;


    @Autowired
    private StockService stockService;

    public void crawl() throws MwException {
        int page = 1;
        JSONObject summary = crawlPage(page);
        if (summary.getBoolean("success")) {
            int totalCount = summary.getJSONObject("count").getInteger("count");
            if (totalCount > 0) {
                int totalPage = totalCount / PAGE_SIZE + 1;
                for (; page <= totalPage; page++) {
                    LOGGER.info("page: {}", page);
                    JSONObject data = crawlPage(page);
                    List<JSONObject> stocks = data.getJSONArray("stocks").toJavaList(JSONObject.class);
                    for (JSONObject stockObject : stocks) {
                        String code = stockObject.getString("code").trim();
                        Stock stock = this.stockService.getByCode(code);
                        if (Preconditions.isBlank(stock)) {
                            stock = new Stock();
                            stock.setCode(code);
                            stock.setCodeCategory(CodeCategory.fromCode(code));
                            stock.setName(stockObject.getString("name").trim());
                            stock.setSymbol(stockObject.getString("symbol").toUpperCase().trim());
                            this.stockService.save(stock);
                        }
                    }
                    ThreadUtils.sleep(50L);
                }
            }
        } else {
            LOGGER.error("not success");
        }
    }

    public JSONObject crawlPage(int page) {
        return crawlPage(page, 1);
    }

    private JSONObject crawlPage(int page, int tryCount) {
        if (tryCount > MAX_TRY_COUNT) {
            LOGGER.error("more than max count: {}", MAX_TRY_COUNT);
            return new JSONObject();
        }

        Map<String, String> queryParams = new HashMap<>(5);
        queryParams.put("order", "desc");
        queryParams.put("orderby", "percent");
        queryParams.put("type", "11,12");
        queryParams.put("page", page + Constants.EMPTY_STRING);
        queryParams.put("size", PAGE_SIZE + Constants.EMPTY_STRING);

        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        httpHeaders.add("Cookie", COOKIE);

        try {
            String data = AsyncHttpUtils.syncGet("https://xueqiu.com/stock/cata/stocklist.json", queryParams, httpHeaders);
            return (JSONObject) JSON.parse(data);
        } catch (HttpServiceException e) {
            LOGGER.error("error", e);
            ThreadUtils.sleep(1000L);
            return crawlPage(page, ++tryCount);
        }
    }


}
