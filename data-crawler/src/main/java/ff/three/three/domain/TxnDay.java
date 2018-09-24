package ff.three.three.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author Forest Wang
 * @package ff.three.three.domain
 * @class TxnDay
 * @email forest@magicwindow.cn
 * @date 2018/9/25 00:12
 * @description
 */
@Data
@Entity
@Table(name = "txn_day", indexes = {@Index(name = "idx_date", columnList = "date")})
public class TxnDay extends BaseEntity {

    @Column(name = "date", columnDefinition = "varchar(8)")
    private String date;

}
