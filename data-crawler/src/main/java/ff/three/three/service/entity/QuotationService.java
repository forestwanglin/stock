package ff.three.three.service.entity;

import cn.magicwindow.common.util.Preconditions;
import ff.three.three.domain.Quotation;
import ff.three.three.repository.QuotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Forest Wang
 * @package ff.three.three.service.entity
 * @class QuotationService
 * @email forest@magicwindow.cn
 * @date 2018/9/21 01:10
 * @description
 */
@Service
public class QuotationService extends BaseEntityService<Quotation> {

    @Autowired
    private TxnDayService txnDayService;

    public String getLatestDate(String symbol) {
        return ((QuotationRepository) baseEntityRepository).queryLatestDateBySymbol(symbol.toUpperCase());
    }


    public Quotation queryBySymbolAndDate(String symbol, String date) {
        return ((QuotationRepository) baseEntityRepository).findBySymbolAndDateAndDeletedIsFalse(symbol.toUpperCase(), date);
    }

    public List<Quotation> queryByDate(String date) {
        return ((QuotationRepository) baseEntityRepository).findAllByDateAndDeletedIsFalseOrderBySymbol(date);
    }


    public List<Quotation> queryBySymbol(String symbol) {
        return ((QuotationRepository) baseEntityRepository).findAllBySymbolAndDeletedIsFalseOrderByDate(symbol.toUpperCase());
    }

    public List<Quotation> queryNextNDaysBySymbolAndDate(String symbol, String date, int days) {
        List<String> dates = this.txnDayService.queryNextNDays(date, days);
        List<Quotation> list = new ArrayList<>();
        for (String d : dates) {
            list.add(((QuotationRepository) baseEntityRepository).findBySymbolAndDateAndDeletedIsFalse(symbol.toUpperCase(), d));
        }
        return list;
    }


    public List<Quotation> queryLastNDaysBySymbolAndDate(String symbol, String date, int days) {
        List<String> dates = this.txnDayService.queryLastNDays(date, days);
        List<Quotation> list = new ArrayList<>();
        for (String d : dates) {
            Quotation quotation = ((QuotationRepository) baseEntityRepository).findBySymbolAndDateAndDeletedIsFalse(symbol.toUpperCase(), d);
            if (Preconditions.isNotBlank(quotation)) {
                list.add(quotation);
            }
        }
        return list;
    }

    public List<String> queryAllExistSymbol() {
        return ((QuotationRepository) baseEntityRepository).queryAllExistSymbol();
    }

    public List<String> queryAllTxnDay() {
        return ((QuotationRepository) baseEntityRepository).queryAllTxnDay();
    }

    public List<String> queryLastNTxnDay(int days) {
        return ((QuotationRepository) baseEntityRepository).queryLastNTxnDay(days);
    }

}
