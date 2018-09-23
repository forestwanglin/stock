package ff.three.three.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author Forest Wang
 * @package ff.three.three.domain
 * @class HammerStockCandidate
 * @email forest@magicwindow.cn
 * @date 2018/9/23 22:59
 * @description
 */
@Data
@Entity
@Table(name = "hammer_stock_candidate", indexes = {@Index(name = "idx_symbol", columnList = "symbol")})
public class HammerStockCandidate extends BaseEntity {

    @Column(name = "symbol", columnDefinition = "varchar(10)")
    private String symbol;

    @Column(name = "date", columnDefinition = "varchar(8)")
    private String date;

    private boolean confirm;
}
