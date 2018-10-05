package ff.three.three.service.analysis;

import cn.magicwindow.common.exception.MwException;
import cn.magicwindow.common.service.IService;
import cn.magicwindow.common.util.DateUtils;
import cn.magicwindow.common.util.ThreadUtils;
import ff.three.three.domain.Stock;
import ff.three.three.domain.TxQuotation;
import ff.three.three.service.entity.StockService;
import ff.three.three.service.entity.TxQuotationService;
import ff.three.three.utils.ListUtils;
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
 * @package ff.three.three.service.analysis
 * @class TxAnalysisService
 * @email forest@magicwindow.cn
 * @date 2018/10/3 15:56
 * @description
 */
@Service
public class TxAnalysisService implements IService {


    private static final Logger LOGGER = LoggerFactory.getLogger(TxAnalysisService.class);


    private static final int THREAD_COUNT = 8;

    @Autowired
    private TxQuotationService txQuotationService;

    @Autowired
    private StockService stockService;

    public void analyze() throws MwException {
        String date = DateUtils.format(DateUtils.now(), DateUtils.FORMAT_D_3);
        this.ma250(date);
    }

    public void ma250() throws MwException {
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
                    LOGGER.info("ma250 for symbol: {}", stock.getSymbol());
                    try {
                        this.ma250(stock.getSymbol());
                    } catch (MwException e) {
                        LOGGER.warn("ma250 error", e);
                    }
                }
                LOGGER.info("thread finished");
            });
            ThreadUtils.sleep(1000L);
        }
    }

    public void ma250(String symbol) throws MwException {
        List<TxQuotation> txQuotations = this.txQuotationService.listBySymbol(symbol);
        LOGGER.info("ma250 for symbol {}", symbol);
        for (int i = 0; i < txQuotations.size(); i++) {
            TxQuotation txQuotation = txQuotations.get(i);
            if (i < 250) {
                txQuotation.setMa250(BigDecimal.ZERO);
            } else {
                BigDecimal sum = BigDecimal.ZERO;
                for (int j = i - 250; j < i; j++) {
                    sum = sum.add(txQuotations.get(j).getClose());
                }
                txQuotation.setMa250(sum.divide(BigDecimal.valueOf(250L)));
            }
            this.txQuotationService.save(txQuotation);
        }
    }

    public void analyzeAll() {
    }
}
