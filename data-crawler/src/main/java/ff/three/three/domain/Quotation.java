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
 * @class Quotation
 * @email forest@magicwindow.cn
 * @date 2018/9/20 23:41
 * @description
 */
@Data
@Entity
@Table(name = "quotation", indexes = {
        @Index(name = "idx_date", columnList = "date"),
        @Index(name = "idx_symbol", columnList = "symbol")})
public class Quotation extends BaseEntity {

    @Column(name = "symbol", columnDefinition = "varchar(10)")
    private String symbol;

    @Column(name = "date", columnDefinition = "varchar(8)")
    private String date;


    private long timestamp;
    private long volume;
    @Column(name = "`open`", columnDefinition = "varchar(8)")
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    @Column(name = "`close`", columnDefinition = "varchar(8)")
    private BigDecimal close;
    private BigDecimal chg;
    private BigDecimal percent;
    private BigDecimal turnoverrate;
    private BigDecimal ma5;
    private BigDecimal ma10;
    private BigDecimal ma20;
    private BigDecimal ma30;
    private BigDecimal dea;
    private BigDecimal dif;
    private BigDecimal macd;
    private BigDecimal ub;
    private BigDecimal lb;
    private BigDecimal kdjk;
    private BigDecimal kdjd;
    private BigDecimal kdjj;
    private BigDecimal rsi1;
    private BigDecimal rsi2;
    private BigDecimal rsi3;
    private BigDecimal wr6;
    private BigDecimal wr10;
    private BigDecimal bias1;
    private BigDecimal bias2;
    private BigDecimal bias3;
    private BigDecimal cci;
    private BigDecimal psy;
    private BigDecimal psyma;
}
