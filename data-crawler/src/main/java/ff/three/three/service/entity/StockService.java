package ff.three.three.service.entity;

import ff.three.three.domain.Stock;
import ff.three.three.repository.StockRepository;
import org.springframework.stereotype.Service;

/**
 * @author Forest Wang
 * @package ff.three.three.service.entity
 * @class StockService
 * @email forest@magicwindow.cn
 * @date 2018/9/20 22:52
 * @description
 */
@Service
public class StockService extends BaseEntityService<Stock> {


    public Stock getByCode(String code) {
        return ((StockRepository) baseEntityRepository).findByCodeAndDeletedIsFalse(code);
    }


}
