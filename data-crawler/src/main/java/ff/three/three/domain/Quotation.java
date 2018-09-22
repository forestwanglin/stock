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
    @Column(name = "`open`", columnDefinition = "decimal(19, 8)")
    private BigDecimal open;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal high;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal low;
    @Column(name = "`close`", columnDefinition = "decimal(19, 8)")
    private BigDecimal close;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal chg;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal percent;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal turnoverrate;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal ma5;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal ma10;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal ma20;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal ma30;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal dea;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal dif;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal macd;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal ub;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal lb;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal kdjk;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal kdjd;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal kdjj;
    /**
     * RSI是相对强弱指标的缩写
     * RSI1一般是6日相对强弱指标
     * RSI2 一般是12日相对强弱指标
     * RSI3一般是24日相对强弱指标
     * 相对强弱值rsi＝N日内收盘价上涨幅度总和／上涨下跌幅度总和乘以100
     */
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal rsi1;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal rsi2;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal rsi3;

    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal wr6;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal wr10;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal bias1;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal bias2;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal bias3;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal cci;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal psy;
    @Column(columnDefinition = "decimal(19, 8)")
    private BigDecimal psyma;
}
