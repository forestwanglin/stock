package ff.three.three.service.entity;

import cn.magicwindow.common.util.Preconditions;
import ff.three.three.domain.FallingStock;
import ff.three.three.repository.FallingStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Forest Wang
 * @package ff.three.three.service.entity
 * @class FallingStockService
 * @email forest@magicwindow.cn
 * @date 2018/9/23 12:03
 * @description
 */
@Service
public class FallingStockService extends BaseEntityService<FallingStock> {


    public List<FallingStock> queryByDate(String date) {
        return ((FallingStockRepository) baseEntityRepository).findAllByDateAndDeletedIsFalse(date);
    }

    public void clearByDate(String date) {
        List<FallingStock> list = this.queryByDate(date);
        if (Preconditions.isNotBlank(list)) {
            for (FallingStock fallingStock : list) {
                this.baseEntityRepository.delete(fallingStock);
            }
        }
    }


}
