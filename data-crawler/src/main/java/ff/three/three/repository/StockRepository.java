package ff.three.three.repository;

import ff.three.three.domain.Stock;
import org.springframework.stereotype.Repository;

/**
 * @author Forest Wang
 * @package ff.three.three.repository
 * @class StockRepository
 * @email forest@magicwindow.cn
 * @date 2018/9/20 22:51
 * @description
 */
@Repository
public interface StockRepository extends BaseEntityRepository<Stock> {


    Stock findByCodeAndDeletedIsFalse(String code);


}
