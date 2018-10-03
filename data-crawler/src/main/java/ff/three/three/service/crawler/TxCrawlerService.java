package ff.three.three.service.crawler;

import cn.magicwindow.common.bean.Constants;
import cn.magicwindow.common.exception.HttpServiceException;
import cn.magicwindow.common.exception.MwException;
import cn.magicwindow.common.service.IService;
import cn.magicwindow.common.util.AsyncHttpUtils;
import cn.magicwindow.common.util.Preconditions;
import cn.magicwindow.common.util.ThreadUtils;
import ff.three.three.domain.Stock;
import ff.three.three.domain.TxQuotation;
import ff.three.three.service.entity.StockService;
import ff.three.three.service.entity.TxQuotationService;
import ff.three.three.utils.ListUtils;
import ff.three.three.utils.NumberFormatUtils;
import ff.three.three.utils.StockUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Forest Wang
 * @package ff.three.three.service.crawler.tx
 * @class TxCrawlerService
 * @email forest@magicwindow.cn
 * @date 2018/10/2 16:48
 * @description 价格都是不复权的价格
 */
@Service
public class TxCrawlerService implements IService {


    private static final Logger LOGGER = LoggerFactory.getLogger(TxCrawlerService.class);


    private static final int MAX_TRY_COUNT = 3;

    private static final int THREAD_COUNT = 8;

    /**
     * 第一个%s，年份，两位   98-1998， 13-2013
     * 第二个%s，股票代码，sz，sh开头 小写，后面+6位股票代码
     */
    private static final String YEAR_DAILY_URL_PATTERN = "http://data.gtimg.cn/flashdata/hushen/daily/%S/%s.js?visitDstTime=1";
    private static final String NOT_FOUND_404 = "404 Not Found";


    @Autowired
    private TxQuotationService txQuotationService;

    @Autowired
    private StockService stockService;

    public void crawlDaily() throws MwException {
        crawlDaily(false);
    }

    public void crawlDaily(boolean init) throws MwException {
        List<Stock> stocks = this.stockService.list();
        List<Stock> crawlStocks = new ArrayList<>();
        for (Stock stock : stocks) {
            if (StockUtils.need2Deal(stock)) {
                crawlStocks.add(stock);
            }
        }
        List<List<Stock>> groups = ListUtils.split2Group(crawlStocks, THREAD_COUNT);

        for (List<Stock> item : groups) {
            ThreadUtils.executePoolThread(() -> {
                for (Stock stock : item) {
                    LOGGER.info("crawl stock from tx: {}", stock.getSymbol());
                    this.crawlDaily(stock.getSymbol(), init);
                }
                LOGGER.info("thread finished");
            });
            ThreadUtils.sleep(1000L);
        }
    }

    public void crawlDaily(String symbol, boolean init) {
        for (int year = 0; year <= 99; year++) {
            crawlDaily(symbol, NumberFormatUtils.formatTxYear(year), init);
        }
    }

    public void crawlDaily(String symbol, String year, boolean init) {
        crawlDaily(symbol, year, init, 1);
    }

    public void crawlDaily(String symbol, String year, boolean init, int tryCount) {
        if (tryCount > MAX_TRY_COUNT) {
            LOGGER.error("more than max count: {}", MAX_TRY_COUNT);
        }
        try {
            String data = AsyncHttpUtils.syncGet(String.format(YEAR_DAILY_URL_PATTERN, year, symbol.toLowerCase()), null);
            if (data.contains(NOT_FOUND_404)) {
                LOGGER.info("not found data for symbol: {} in year {}", symbol, year);
                return;
            }
            String[] strs = data.split(Constants.EQUAL);
            if (strs.length == 2) {
                String value = strs[1].substring(1);
                String[] lines = value.split("\\\\n\\\\\n");
                List<TxQuotation> list = null;
                if (init) {
                    list = new ArrayList<>();
                }
                for (String line : lines) {
                    if (Preconditions.isNotBlank(line.trim())) {
                        String[] values = line.trim().split(" ");
                        if (values.length == 6) {
                            String date = StockUtils.formatTxDate(values[0]);
                            if (init) {
                                TxQuotation txQuotation = new TxQuotation();
                                txQuotation.setDate(date);
                                txQuotation.setSymbol(symbol.toUpperCase());
                                txQuotation.setVolume(Long.parseLong(values[5]));
                                txQuotation.setOpen(new BigDecimal(values[1]));
                                txQuotation.setClose(new BigDecimal(values[2]));
                                txQuotation.setHigh(new BigDecimal(values[3]));
                                txQuotation.setLow(new BigDecimal(values[4]));
                                list.add(txQuotation);
                            } else {
                                TxQuotation txQuotation = txQuotationService.queryBySymbolAndDate(symbol.toUpperCase(), date);
                                if (Preconditions.isBlank(txQuotation)) {
                                    txQuotation = new TxQuotation();
                                    txQuotation.setDate(date);
                                    txQuotation.setSymbol(symbol.toUpperCase());
                                    txQuotation.setVolume(Long.parseLong(values[5]));
                                    txQuotation.setOpen(new BigDecimal(values[1]));
                                    txQuotation.setClose(new BigDecimal(values[2]));
                                    txQuotation.setHigh(new BigDecimal(values[3]));
                                    txQuotation.setLow(new BigDecimal(values[4]));
                                    try {
                                        this.txQuotationService.save(txQuotation);
                                    } catch (MwException e) {
                                        LOGGER.warn("save error with stock {} line {}", symbol, line);
                                    }
                                }
                            }
                        } else {
                            LOGGER.error("length of values is not 6, {}", line);
                        }
                    }
                }
                if (init) {
                    this.txQuotationService.saveAll(list);
                }
            } else {
                LOGGER.info("length of strs is not 2.");
            }
        } catch (HttpServiceException e) {
            LOGGER.error("error", e);
            ThreadUtils.sleep(1000L);
            crawlDaily(symbol, year, init, ++tryCount);
        }
    }

}
