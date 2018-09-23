package ff.three.three.service.analysis;

import cn.magicwindow.common.exception.MwException;
import cn.magicwindow.common.service.IService;
import ff.three.three.domain.FallingStock;
import ff.three.three.domain.Stock;
import ff.three.three.service.entity.FallingStockService;
import ff.three.three.service.entity.StockService;
import ff.three.three.utils.StockUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static ff.three.three.bean.Constants.DEFAULT_CHECK_FALLING_DAYS;

/**
 * @author Forest Wang
 * @package ff.three.three.service.analysis
 * @class FallingStockAnalysisService
 * @email forest@magicwindow.cn
 * @date 2018/9/23 21:05
 * @description
 */
@Service
public class FallingStockAnalysisService implements IService {


    private static final Logger LOGGER = LoggerFactory.getLogger(FallingStockAnalysisService.class);

    @Autowired
    private StockService stockService;

    @Autowired
    private HammerLineService hammerLineService;

    @Autowired
    private FallingStockService fallingStockService;

    public void logFallingStock(String date) throws MwException {

        this.fallingStockService.clearByDate(date);

        List<Stock> stocks = this.stockService.list();
        for (Stock stock : stocks) {
            if (StockUtils.need2Deal(stock)) {
                if (this.hammerLineService.isFalling(stock.getSymbol(), date)) {
                    FallingStock fallingStock = new FallingStock();
                    fallingStock.setSymbol(stock.getSymbol());
                    fallingStock.setDate(date);
                    fallingStock.setDays(DEFAULT_CHECK_FALLING_DAYS);
                    this.fallingStockService.save(fallingStock);
                }
            }
        }
    }
}
