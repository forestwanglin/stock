package ff.three.three.repository;

import ff.three.three.domain.TxnDay;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Forest Wang
 * @package ff.three.three.repository
 * @class TxnDayRepository
 * @email forest@magicwindow.cn
 * @date 2018/9/25 00:15
 * @description
 */
@Repository
public interface TxnDayRepository extends BaseEntityRepository<TxnDay> {


    TxnDay findByDateAndDeletedIsFalse(String date);


    /**
     * 包括当天
     *
     * @param date
     * @param days
     * @return
     */
    @Query(nativeQuery = true, value = "select * from txn_day where date <= :date and deleted = 0 order by date desc limit :days")
    List<TxnDay> queryLastNDays(@Param("date") String date,
                                @Param("days") int days);


    /**
     * 包括当前天
     */
    @Query(nativeQuery = true, value = "select * from txn_day where date >= :date and deleted = 0 order by date limit :days")
    List<TxnDay> queryNextNDays(@Param("date") String date,
                                @Param("days") int days);

}
