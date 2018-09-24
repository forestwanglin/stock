package ff.three.three.service.entity;

import cn.magicwindow.common.exception.MwException;
import cn.magicwindow.common.util.Preconditions;
import ff.three.three.domain.TxnDay;
import ff.three.three.repository.TxnDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Forest Wang
 * @package ff.three.three.service.entity
 * @class TxnDayService
 * @email forest@magicwindow.cn
 * @date 2018/9/25 00:15
 * @description
 */
@Service
public class TxnDayService extends BaseEntityService<TxnDay> {

    @Autowired
    private QuotationService quotationService;


    public TxnDay queryByDate(String date) {
        return ((TxnDayRepository) baseEntityRepository).findByDateAndDeletedIsFalse(date);
    }

    public void txnDatesUpdate() throws MwException {
        List<String> dates = this.quotationService.queryLastNTxnDay(33);
        for (String date : dates) {
            TxnDay txnDay = this.queryByDate(date);
            if (Preconditions.isBlank(txnDay)) {
                txnDay = new TxnDay();
                txnDay.setDate(date);
                this.save(txnDay);
            }
        }
    }

    public List<String> queryNextNDays(String date, int days) {
        List<TxnDay> txnDays = ((TxnDayRepository) baseEntityRepository).queryNextNDays(date, days);
        return obj2String(txnDays);
    }

    public List<String> queryLastNDays(String date, int days) {
        List<TxnDay> txnDays = ((TxnDayRepository) baseEntityRepository).queryLastNDays(date, days);
        Collections.reverse(txnDays);
        return obj2String(txnDays);
    }


    private List<String> obj2String(List<TxnDay> list) {
        List<String> dates = new ArrayList<>();
        for (TxnDay txnDay : list) {
            dates.add(txnDay.getDate());
        }
        return dates;
    }

}
