package ff.three.three.service.entity;

import cn.magicwindow.common.exception.MwException;
import cn.magicwindow.common.util.DateUtils;
import cn.magicwindow.common.util.Preconditions;
import ff.three.three.domain.HammerStockCandidate;
import ff.three.three.repository.HammerStockCandidateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Forest Wang
 * @package ff.three.three.service.entity
 * @class HammerStockCandidateService
 * @email forest@magicwindow.cn
 * @date 2018/9/23 23:02
 * @description
 */
@Service
public class HammerStockCandidateService extends BaseEntityService<HammerStockCandidate> {


    public List<HammerStockCandidate> queryByDate(String date) {
        return ((HammerStockCandidateRepository) baseEntityRepository).findAllByDateAndDeletedIsFalse(date);
    }


    public void updateList(List<String> stockSymbols) throws MwException {
        String date = DateUtils.format(DateUtils.now(), DateUtils.FORMAT_D_3);
        List<HammerStockCandidate> list = this.queryByDate(date);
        if (Preconditions.isNotBlank(list)) {
            for (HammerStockCandidate item : list) {
                item.setConfirm(false);
                this.save(item);
            }
        }
        for (String symbol : stockSymbols) {
            this.updateItem(symbol.toUpperCase(), date);
        }
    }

    public void updateItem(String symbol, String date) throws MwException {
        HammerStockCandidate item = ((HammerStockCandidateRepository) baseEntityRepository).findBySymbolAndDateAndDeletedIsFalse(
                symbol.toUpperCase(), date);
        if (Preconditions.isBlank(item)) {
            item = new HammerStockCandidate();
            item.setSymbol(symbol.toUpperCase());
            item.setDate(date);
        }
        item.setConfirm(true);
        this.save(item);
    }
}
