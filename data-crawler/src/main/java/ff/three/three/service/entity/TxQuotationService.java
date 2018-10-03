package ff.three.three.service.entity;

import ff.three.three.domain.TxQuotation;
import ff.three.three.repository.TxQuotationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Forest Wang
 * @package ff.three.three.service.entity
 * @class TxQuotationService
 * @email forest@magicwindow.cn
 * @date 2018/10/2 21:53
 * @description
 */
@Service
public class TxQuotationService extends BaseEntityService<TxQuotation> {

    public TxQuotation queryBySymbolAndDate(String symbol, String date) {
        return ((TxQuotationRepository) baseEntityRepository).findBySymbolAndDateAndDeletedIsFalse(symbol.toUpperCase(), date);
    }


    public void saveAll(List<TxQuotation> txQuotations) {
        baseEntityRepository.saveAll(txQuotations);
    }

}
