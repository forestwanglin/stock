package ff.three.three.service.crawler;

import cn.magicwindow.common.bean.Constants;
import cn.magicwindow.common.exception.HttpServiceException;
import cn.magicwindow.common.exception.MwException;
import cn.magicwindow.common.service.IService;
import cn.magicwindow.common.util.AsyncHttpUtils;
import cn.magicwindow.common.util.DateUtils;
import cn.magicwindow.common.util.Preconditions;
import cn.magicwindow.common.util.ThreadUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ff.three.three.domain.Quotation;
import ff.three.three.domain.Stock;
import ff.three.three.service.entity.QuotationService;
import ff.three.three.service.entity.StockService;
import ff.three.three.type.CodeCategory;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static ff.three.three.bean.Constants.COOKIE;

/**
 * @author Forest Wang
 * @package ff.three.three.service.crawler
 * @class StockQuotationCrawlerService
 * @email forest@magicwindow.cn
 * @date 2018/9/20 23:41
 * @description
 */
@Service
public class StockQuotationCrawlerService implements IService {


    private static final Logger LOGGER = LoggerFactory.getLogger(StockQuotationCrawlerService.class);


    private static final int DEFAULT_DAYS = 10000;

    private static final int MAX_TRY_COUNT = 3;


    private static final int THREAD_COUNT = 8;

    @Autowired
    private QuotationService quotationService;

    @Autowired
    private StockService stockService;


    public void crawlStockQuotation() throws MwException {
        List<Stock> stocks = this.stockService.list();
        List<Stock> crawlStocks = new ArrayList<>();
        for (Stock stock : stocks) {
            if (stock.getCodeCategory() == CodeCategory.HU_A ||
                    stock.getCodeCategory() == CodeCategory.SHEN_A ||
                    stock.getCodeCategory() == CodeCategory.CHUANG_YE_BAN ||
                    stock.getCodeCategory() == CodeCategory.ZHONG_XIAO_BAN) {
                crawlStocks.add(stock);
            }
        }
        int length = crawlStocks.size() / THREAD_COUNT;
        List<List<Stock>> groups = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            int fromIndex = length * i;
            int toIndex = length * (i + 1);
            if (i == THREAD_COUNT - 1) {
                toIndex = crawlStocks.size();
            }
            List<Stock> item = new ArrayList<>();
            item.addAll(crawlStocks.subList(fromIndex, toIndex));
            groups.add(item);
        }

        for (List<Stock> item : groups) {
            ThreadUtils.executePoolThread(() -> {
                for (Stock stock : item) {
                    LOGGER.info("crawl stock: {}", stock.getSymbol());
                    try {
                        crawlStockQuotation(stock.getSymbol());
                    } catch (MwException e) {
                        LOGGER.error("thread error", e);
                    }
                }
            });
            ThreadUtils.sleep(1000L);
        }
    }

    public void crawlStockQuotation(String symbol) throws MwException {
        String latestDate = this.quotationService.getLatestDate(symbol);
        long begin = DateUtils.now().getTime();

        int days = DEFAULT_DAYS;
        if (Preconditions.isNotBlank(latestDate)) {
            days = (int) ((begin - DateUtils.parse(latestDate, DateUtils.FORMAT_D_3).getTime()) / (24 * 3600 * 1000) + 1);
        }

        JSONObject data = crawlStockQuotation(symbol, begin, days);
        if (data.getInteger("error_code") == 0) {
            if (Preconditions.isBlank(data.getJSONObject("data"))) {
                LOGGER.warn("symbol {} not have data", symbol);
                return;
            }
            List<String> columns = data.getJSONObject("data").getJSONArray("column").toJavaList(String.class);
            List<JSONArray> items = data.getJSONObject("data").getJSONArray("item").toJavaList(JSONArray.class);

            for (JSONArray item : items) {
                String date = DateUtils.format(new Date(item.getLong(getIndex(columns, "timestamp"))), DateUtils.FORMAT_D_3);
                Quotation quotation = this.quotationService.queryBySymbolAndDate(symbol, date);
                if (Preconditions.isBlank(quotation)) {
                    quotation = new Quotation();
                    quotation.setSymbol(symbol.toUpperCase());
                    quotation.setDate(date);
                    quotation.setTimestamp(item.getLong(getIndex(columns, "timestamp")));
                    quotation.setVolume(item.getInteger(getIndex(columns, "volume")));
                    quotation.setOpen(item.getBigDecimal(getIndex(columns, "open")));
                    quotation.setHigh(item.getBigDecimal(getIndex(columns, "high")));
                    quotation.setLow(item.getBigDecimal(getIndex(columns, "low")));
                    quotation.setClose(item.getBigDecimal(getIndex(columns, "close")));
                    quotation.setChg(item.getBigDecimal(getIndex(columns, "chg")));
                    quotation.setPercent(item.getBigDecimal(getIndex(columns, "percent")));
                    quotation.setTurnoverrate(item.getBigDecimal(getIndex(columns, "turnoverrate")));
                    quotation.setMa5(item.getBigDecimal(getIndex(columns, "ma5")));
                    quotation.setMa10(item.getBigDecimal(getIndex(columns, "ma10")));
                    quotation.setMa20(item.getBigDecimal(getIndex(columns, "ma20")));
                    quotation.setMa30(item.getBigDecimal(getIndex(columns, "ma30")));
                    quotation.setDea(item.getBigDecimal(getIndex(columns, "dea")));
                    quotation.setDif(item.getBigDecimal(getIndex(columns, "dif")));
                    quotation.setMacd(item.getBigDecimal(getIndex(columns, "macd")));
                    quotation.setUb(item.getBigDecimal(getIndex(columns, "ub")));
                    quotation.setLb(item.getBigDecimal(getIndex(columns, "lb")));
//                    quotation.setMa20(item.getBigDecimal(getIndex(columns, "ma20")));
                    quotation.setKdjk(item.getBigDecimal(getIndex(columns, "kdjk")));
                    quotation.setKdjd(item.getBigDecimal(getIndex(columns, "kdjd")));
                    quotation.setKdjj(item.getBigDecimal(getIndex(columns, "kdjj")));
                    quotation.setRsi1(item.getBigDecimal(getIndex(columns, "rsi1")));
                    quotation.setRsi2(item.getBigDecimal(getIndex(columns, "rsi2")));
                    quotation.setRsi3(item.getBigDecimal(getIndex(columns, "rsi3")));
                    quotation.setWr6(item.getBigDecimal(getIndex(columns, "wr6")));
                    quotation.setWr10(item.getBigDecimal(getIndex(columns, "wr10")));
                    quotation.setBias1(item.getBigDecimal(getIndex(columns, "bias1")));
                    quotation.setBias2(item.getBigDecimal(getIndex(columns, "bias2")));
                    quotation.setBias3(item.getBigDecimal(getIndex(columns, "bias3")));
                    quotation.setCci(item.getBigDecimal(getIndex(columns, "cci")));
                    quotation.setPsy(item.getBigDecimal(getIndex(columns, "psy")));
                    quotation.setPsyma(item.getBigDecimal(getIndex(columns, "psyma")));
                    this.quotationService.save(quotation);
                }
            }
        } else {
            LOGGER.error("not success");
        }
    }


    public JSONObject crawlStockQuotation(String symbol, long begin, int day) {
        return crawlStockQuotation(symbol, begin, day, 1);
    }


    private JSONObject crawlStockQuotation(String symbol, long begin, int day, int tryCount) {
        if (tryCount > MAX_TRY_COUNT) {
            LOGGER.error("more than max count: {}", MAX_TRY_COUNT);
            return new JSONObject();
        }

        Map<String, String> queryParams = new HashMap<>(6);
        queryParams.put("symbol", symbol);
        queryParams.put("begin", begin + Constants.EMPTY_STRING);
        queryParams.put("period", "day");
        queryParams.put("type", "before");
        queryParams.put("count", "-" + day);
        queryParams.put("indicator", "kline,ma,macd,kdj,boll,rsi,wr,bias,cci,psy");

        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        httpHeaders.add("Cookie", COOKIE);

        try {
            String data = AsyncHttpUtils.syncGet("https://stock.xueqiu.com/v5/stock/chart/kline.json", queryParams, httpHeaders);
            return (JSONObject) JSON.parse(data);
        } catch (HttpServiceException e) {
            LOGGER.error("error", e);
            ThreadUtils.sleep(1000L);
            LOGGER.info("try {} times", tryCount + 1);
            return crawlStockQuotation(symbol, begin, day, ++tryCount);
        }

    }


    private int getIndex(List<String> columns, String name) {
        for (int i = 0; i < columns.size(); i++) {
            if (name.equalsIgnoreCase(columns.get(i))) {
                return i;
            }
        }
        return 0;
    }


}
