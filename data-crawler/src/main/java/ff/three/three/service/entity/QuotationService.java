package ff.three.three.service.entity;

import ff.three.three.domain.Quotation;
import ff.three.three.repository.QuotationRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
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


    public String getLatestDate(String symbol) {
        return ((QuotationRepository) baseEntityRepository).queryLatestDateBySymbol(symbol.toUpperCase());
    }


    public Quotation queryBySymbolAndDate(String symbol, String date) {
        return ((QuotationRepository) baseEntityRepository).findBySymbolAndDateAndDeletedIsFalse(symbol.toUpperCase(), date);
    }

    public List<Quotation> queryNextNDaysBySymbolAndDate(String symbol, String date, int days) {
        return ((QuotationRepository) baseEntityRepository).queryNextNDaysBySymbolAndDate(symbol.toUpperCase(), date, days);
    }

    public List<Quotation> queryBySymbol(String symbol) {
        return ((QuotationRepository) baseEntityRepository).findAllBySymbolAndDeletedIsFalseOrderByDate(symbol.toUpperCase());
    }


    public List<Quotation> queryLastNDaysBySymbolAndDate(String symbol, String date, int days) {
        List<Quotation> quotations = ((QuotationRepository) baseEntityRepository)
                .queryLastNDaysBySymbolAndDate(symbol.toUpperCase(), date, days);
        Collections.reverse(quotations);
        return quotations;
    }

    public List<String> queryAllExistSymbol() {
        return ((QuotationRepository) baseEntityRepository).queryAllExistSymbol();
    }

}
