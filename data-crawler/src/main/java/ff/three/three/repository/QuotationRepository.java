package ff.three.three.repository;

import ff.three.three.domain.Quotation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Forest Wang
 * @package ff.three.three.repository
 * @class QuotationRepository
 * @email forest@magicwindow.cn
 * @date 2018/9/21 01:04
 * @description
 */
@Repository
public interface QuotationRepository extends BaseEntityRepository<Quotation> {


    @Query(nativeQuery = true, value = "select max(date) from quotation where symbol = :symbol and deleted = 0")
    String queryLatestDateBySymbol(@Param("symbol") String symbol);

    Quotation findBySymbolAndDateAndDeletedIsFalse(String symbol, String date);

    List<Quotation> findAllBySymbolAndDeletedIsFalseOrderByDate(String symbol);

    @Query(nativeQuery = true, value = "select * from quotation where symbol = :symbol and date <= :date and deleted = 0 order by date desc limit :days")
    List<Quotation> queryLastNDaysBySymbolAndDate(@Param("symbol") String symbol,
                                                  @Param("date") String date,
                                                  @Param("days") int days);

}