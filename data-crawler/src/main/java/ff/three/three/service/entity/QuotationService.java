package ff.three.three.service.entity;

import ff.three.three.domain.Quotation;
import ff.three.three.repository.QuotationRepository;
import org.springframework.stereotype.Service;

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


}
