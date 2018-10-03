package ff.three.three.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Forest Wang
 * @package ff.three.three.domain
 * @class TxQuotation
 * @email forest@magicwindow.cn
 * @date 2018/10/2 21:50
 * @description
 */

@Data
@Entity
@Table(name = "tx_quotation", indexes = {
        @Index(name = "idx_date", columnList = "date"),
        @Index(name = "idx_symbol", columnList = "symbol")})
public class TxQuotation extends BaseEntity {

    @Column(name = "symbol", columnDefinition = "varchar(10)")
    private String symbol;

    @Column(name = "date", columnDefinition = "varchar(8)")
    private String date;

    // 手
    private long volume;

    @Column(name = "`open`", columnDefinition = "decimal(19, 8)")
    private BigDecimal open;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal high;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal low;
    @Column(name = "`close`", columnDefinition = "decimal(19, 8)")
    private BigDecimal close;

}