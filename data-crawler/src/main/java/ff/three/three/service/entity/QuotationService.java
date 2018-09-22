package ff.three.three.service.entity;

import cn.magicwindow.common.util.DateUtils;
import cn.magicwindow.common.util.Preconditions;
import ff.three.three.domain.Quotation;
import ff.three.three.repository.QuotationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<Quotation> queryBySymbol(String symbol) {
        return ((QuotationRepository) baseEntityRepository).findAllBySymbolAndDeletedIsFalseOrderByDate(symbol.toUpperCase());
    }

    public List<Quotation> queryLatestDaysBySymbolAndDate(String symbol, String date, int days) {
        Date endDate = DateUtils.parse(date, DateUtils.FORMAT_D_3);
        Date startDate = DateUtils.addDays(endDate, -(days + 33));
        List<Quotation> quotations = ((QuotationRepository) baseEntityRepository).findAllBySymbolAndDateBetweenAndDeletedIsFalseOrderByDate(symbol.toUpperCase(),
                DateUtils.format(startDate, DateUtils.FORMAT_D_3), date);
        List<Quotation> result = new ArrayList<>();
        if (Preconditions.isNotBlank(quotations)) {
            if (quotations.size() >= days) {
                result = quotations.subList(quotations.size() - days - 1 >= 0 ? quotations.size() - days - 1 : 0, quotations.size());
            }
        }
        return result;
    }

}
