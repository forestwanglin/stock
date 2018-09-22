package ff.three.three.service.analysis;

import cn.magicwindow.common.exception.MwException;
import cn.magicwindow.common.service.IService;
import cn.magicwindow.common.util.DateUtils;
import cn.magicwindow.common.util.Preconditions;
import ff.three.three.domain.Quotation;
import ff.three.three.domain.Stock;
import ff.three.three.service.entity.QuotationService;
import ff.three.three.service.entity.StockService;
import ff.three.three.type.CodeCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


    @Autowired
    private QuotationService quotationService;

    @Autowired
    private StockService stockService;


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

    public void findAll(String date) throws MwException {
        List<Stock> stocks = this.stockService.list();
        List<Stock> verifyStocks = new ArrayList<>();
        for (Stock stock : stocks) {
            if (stock.getCodeCategory() == CodeCategory.HU_A ||
                    stock.getCodeCategory() == CodeCategory.SHEN_A ||
                    stock.getCodeCategory() == CodeCategory.CHUANG_YE_BAN ||
                    stock.getCodeCategory() == CodeCategory.ZHONG_XIAO_BAN) {
                verifyStocks.add(stock);
            }
        }

        for (Stock stock : verifyStocks) {
            LOGGER.trace("check hammer line for stock: {}", stock.getSymbol());
            Quotation quotation = this.quotationService.queryBySymbolAndDate(stock.getSymbol(), date);
            if (this.isFalling(stock.getSymbol(), date) && this.isHammerDay(quotation)) {
                LOGGER.info("stock: {} date: {}", stock.getSymbol(), date);
            }
        }
    }

    public boolean isHammerDay(Quotation quotation) {
        double upperShadowHeight = 0;
        double lowerShadowHeight = 0;
        double totalHeight = quotation.getHigh().doubleValue() - quotation.getLow().doubleValue();
        double cylinderHeight = quotation.getClose().doubleValue() - quotation.getOpen().doubleValue();
        double cylinderHeightAbs = Math.abs(cylinderHeight);
        if (cylinderHeight >= 0) {
            upperShadowHeight = quotation.getHigh().doubleValue() - quotation.getClose().doubleValue();
            lowerShadowHeight = quotation.getOpen().doubleValue() - quotation.getLow().doubleValue();
        } else {
            upperShadowHeight = quotation.getHigh().doubleValue() - quotation.getOpen().doubleValue();
            lowerShadowHeight = quotation.getClose().doubleValue() - quotation.getLow().doubleValue();
        }
        boolean hammerDay = false;
        if ((upperShadowHeight < cylinderHeightAbs ||
                cylinderHeightAbs < totalHeight / 10)
                && lowerShadowHeight >= cylinderHeightAbs * 2) {
            hammerDay = true;
        }
        return hammerDay;

    }


    public boolean isFalling(String symbol, String date) {
        return isFalling(symbol, date, 5);
    }

    public boolean isFalling(String symbol, String date, int duration) {
        boolean falling = false;
        List<Quotation> list = this.quotationService.queryLatestDaysBySymbolAndDate(symbol, date, duration);
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
}
