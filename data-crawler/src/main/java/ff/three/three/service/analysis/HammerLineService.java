package ff.three.three.service.analysis;

import cn.magicwindow.common.exception.MwException;
import cn.magicwindow.common.service.IService;
import cn.magicwindow.common.util.DateUtils;
import cn.magicwindow.common.util.Preconditions;
import cn.magicwindow.common.util.ThreadUtils;
import ff.three.three.domain.HammerStockCandidate;
import ff.three.three.domain.Quotation;
import ff.three.three.domain.Stock;
import ff.three.three.service.entity.HammerStockCandidateService;
import ff.three.three.service.entity.QuotationService;
import ff.three.three.service.entity.StockService;
import ff.three.three.utils.ListUtils;
import ff.three.three.utils.NumberFormatUtils;
import ff.three.three.utils.StockUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static cn.magicwindow.common.bean.Constants.EMPTY_STRING;
import static ff.three.three.bean.Constants.DEFAULT_CHECK_FALLING_DAYS;

/**
 * @author Forest Wang
 * @package ff.three.three.service.analysis
 * @class HammerLineService
 * @email forest@magicwindow.cn
 * @date 2018/9/22 21:35
 * @description
 */
@Service
public class HammerLineService implements IService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HammerLineService.class);

    private static final int THREAD_COUNT = 8;

    @Autowired
    private QuotationService quotationService;

    @Autowired
    private StockService stockService;

    @Autowired
    private HammerStockCandidateService hammerStockCandidateService;


    public void xx() {
        List<Quotation> list = this.quotationService.queryBySymbol("SZ300482");
        for (Quotation quotation : list) {
            if (isHammerDay(quotation)) {
                LOGGER.info(quotation.getDate());
            }
        }
    }


    public void yy(String symbol, String date) {
        LOGGER.info(isFalling(symbol, date) + "");
    }

    public void findAll() throws MwException {
        this.findAll(DateUtils.format(DateUtils.now(), DateUtils.FORMAT_D_3));
    }

    /**
     * 查找某一天是否是锤子股
     *
     * @param date
     * @throws MwException
     */
    public void findAll(String date) throws MwException {
        List<Stock> stocks = this.stockService.list();
        List<Stock> verifyStocks = new ArrayList<>();
        for (Stock stock : stocks) {
            if (StockUtils.need2Deal(stock)) {
                verifyStocks.add(stock);
            }
        }
        LOGGER.info("verify stock count: {}", verifyStocks.size());

        List<List<Stock>> groups = ListUtils.split2Group(verifyStocks, THREAD_COUNT);

        for (List<Stock> item : groups) {
            ThreadUtils.executePoolThread(() -> {
                for (Stock stock : item) {
                    LOGGER.trace("check hammer line for stock: {}", stock.getSymbol());
                    Quotation quotation = this.quotationService.queryBySymbolAndDate(stock.getSymbol(), date);
                    if (this.isFalling(stock.getSymbol(), date) && this.isHammerDay(quotation)) {
                        LOGGER.info("stock: {} date: {}", stock.getSymbol(), date);
                        try {
                            this.hammerStockCandidateService.updateItem(stock.getSymbol(), date);
                        } catch (MwException e) {
                            LOGGER.error("error occurred", e);
                        }
                    }
                }
                LOGGER.info("HammerLine thread finished for {}", date);
            });
            ThreadUtils.sleep(1000L);
        }
    }

    public boolean isHammerDay(Quotation quotation) {
        if (Preconditions.isBlank(quotation)
                || Preconditions.isBlank(quotation.getOpen())
                || Preconditions.isBlank(quotation.getClose())
                || Preconditions.isBlank(quotation.getHigh())
                || Preconditions.isBlank(quotation.getLow())) {
            LOGGER.warn("stock data exception: {}", Preconditions.isNotBlank(quotation) ? quotation.getSymbol() : EMPTY_STRING);
            return false;
        }
        return isHammerDay(quotation.getOpen().doubleValue(),
                quotation.getClose().doubleValue(),
                quotation.getLow().doubleValue(),
                quotation.getHigh().doubleValue());
    }

    public boolean isHammerDay(double open, double close, double low, double high) {
        double upperShadowHeight;
        double lowerShadowHeight;
        double totalHeight = high - low;
        double cylinderHeight = close - open;
        double cylinderHeightAbs = Math.abs(cylinderHeight);
        if (cylinderHeight >= 0) {
            upperShadowHeight = high - close;
            lowerShadowHeight = open - low;
        } else {
            upperShadowHeight = high - open;
            lowerShadowHeight = close - low;
        }
        boolean hammerDay = false;
        // 下影线长度是涨跌幅两倍以上
        if (lowerShadowHeight >= cylinderHeightAbs * 2) {
            if (lowerShadowHeight >= upperShadowHeight * 2) {
                if (upperShadowHeight < cylinderHeightAbs ||
                        cylinderHeightAbs < totalHeight / 10) {
                    if ((open - low)
                            / open >= 0.04) {
                        hammerDay = true;
                    }
                }
            }
        }
        return hammerDay;

    }


    public boolean isFalling(String symbol, String date) {
        return isFalling(symbol, date, DEFAULT_CHECK_FALLING_DAYS);
    }

    public boolean isFalling(String symbol, String date, int duration) {
        boolean falling = false;
        List<Quotation> list = this.quotationService.queryLastNDaysBySymbolAndDate(symbol, date, duration + 1);
        int riseCount = 0;
        int fallCount = 0;
        int unknownCount = 0;
        Quotation tmp = null;
        for (Quotation quotation : list) {
            if (Preconditions.isNotBlank(tmp)) {
                if (Preconditions.isNotBlank(quotation.getMa5())
                        && Preconditions.isNotBlank(tmp.getMa5())) {
                    if (quotation.getMa5().doubleValue() - tmp.getMa5().doubleValue() >= 0) {
                        riseCount++;
                    } else {
                        fallCount++;
                    }
                } else {
                    unknownCount++;
                }
            }
            tmp = quotation;
        }
        if (fallCount / (double) (fallCount + riseCount + unknownCount) >= 0.8) {
            Quotation day1 = list.get(0);
            Quotation dayN = list.get(list.size() - 1);
            if (Math.abs((dayN.getClose().doubleValue() - day1.getClose().doubleValue()) / day1.getClose().doubleValue()) > 0.01 * duration) {
                falling = true;
            }
        }
        return falling;
    }


    /**
     * 分析
     **/
    public void analyzeFollowingDays(List<String> dates) {
        List<Double> upPercentD1Items = new ArrayList<>();
        List<Double> downPercentD1Items = new ArrayList<>();
        List<Double> highPercentD1Items = new ArrayList<>();
        for (String date : dates) {
            LOGGER.info("------------------------------------------------------------------");
            LOGGER.info("| date: {}", date);
            List<HammerStockCandidate> candidates = this.hammerStockCandidateService.queryByDate(date);
            LOGGER.info("|  SYMBOL  |   D1-H   |    D1    |    D2    |    D3    |    D4    |");
            if (Preconditions.isNotBlank(candidates)) {
                for (HammerStockCandidate candidate : candidates) {
                    List<Quotation> list = this.quotationService.queryNextNDaysBySymbolAndDate(candidate.getSymbol(), date, 5);
                    if (Preconditions.isBlank(list) || list.size() == 1) {
                        LOGGER.info("stock {} date {} is the last txn day", candidate.getSymbol(), date);
                    } else {
                        double close = list.get(0).getClose().doubleValue();
                        double closeD1 = list.get(1).getClose().doubleValue();
                        double closeD2 = list.get(2).getClose().doubleValue();
                        double closeD3 = list.get(3).getClose().doubleValue();
                        double closeD4 = list.get(4).getClose().doubleValue();
                        double highD1 = list.get(1).getHigh().doubleValue();
                        LOGGER.info("| {} | {} | {} | {} | {} | {} |",
                                candidate.getSymbol(),
                                NumberFormatUtils.percentFormat((highD1 - close) / close),
                                NumberFormatUtils.percentFormat((closeD1 - close) / close),
                                NumberFormatUtils.percentFormat((closeD2 - close) / close),
                                NumberFormatUtils.percentFormat((closeD3 - close) / close),
                                NumberFormatUtils.percentFormat((closeD4 - close) / close));
                        if (closeD1 - close >= 0) {
                            upPercentD1Items.add((closeD1 - close) / close);
                        } else {
                            downPercentD1Items.add((closeD1 - close) / close);
                        }
                        highPercentD1Items.add((highD1 - close) / close);
                    }
                }
            }
        }

        LOGGER.info("| Day1: Go up: {}({}), Go down: {}({})", upPercentD1Items.size(), NumberFormatUtils.percentFormat(upPercentD1Items.size() / (double) (upPercentD1Items.size() + downPercentD1Items.size())),
                downPercentD1Items.size(), NumberFormatUtils.percentFormat(downPercentD1Items.size() / (double) (upPercentD1Items.size() + downPercentD1Items.size())));
        double average = 0;
        for (double i : highPercentD1Items) {
            average += i;
        }
        average = average / highPercentD1Items.size();
        LOGGER.info("| Day1 high average: {}", NumberFormatUtils.percentFormat(average));
    }

}
