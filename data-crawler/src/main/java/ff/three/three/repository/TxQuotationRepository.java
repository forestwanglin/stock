package ff.three.three.repository;

import ff.three.three.domain.TxQuotation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Forest Wang
 * @package ff.three.three.repository
 * @class TxQuotationRepository
 * @email forest@magicwindow.cn
 * @date 2018/10/2 21:52
 * @description
 */
@Repository
public interface TxQuotationRepository extends BaseEntityRepository<TxQuotation> {


    TxQuotation findBySymbolAndDateAndDeletedIsFalse(String symbol, String date);

    List<TxQuotation> findAllBySymbolAndDeletedIsFalseOrderByDate(String symbol);
}
