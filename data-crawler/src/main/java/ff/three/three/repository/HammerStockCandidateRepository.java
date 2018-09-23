package ff.three.three.repository;

import ff.three.three.domain.HammerStockCandidate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Forest Wang
 * @package ff.three.three.repository
 * @class HammerStockCandidateRepository
 * @email forest@magicwindow.cn
 * @date 2018/9/23 23:01
 * @description
 */
@Repository
public interface HammerStockCandidateRepository extends BaseEntityRepository<HammerStockCandidate> {

    List<HammerStockCandidate> findAllByDateAndDeletedIsFalse(String date);

    HammerStockCandidate findBySymbolAndDateAndDeletedIsFalse(String symbol, String date);

}
