package ff.three.three.repository;

import ff.three.three.domain.FallingStock;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Forest Wang
 * @package ff.three.three.repository
 * @class FallingStockRepository
 * @email forest@magicwindow.cn
 * @date 2018/9/23 12:02
 * @description
 */
@Repository
public interface FallingStockRepository extends BaseEntityRepository<FallingStock> {


    List<FallingStock> findAllByDateAndDeletedIsFalse(String date);

}
