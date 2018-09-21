package ff.three.three.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import ff.three.three.type.CodeCategory;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Forest Wang
 * @package ff.three.three.domain
 * @class Stock
 * @email forest@magicwindow.cn
 * @date 2018/9/20 21:40
 * @description
 */
@Data
@Entity
@Table(name = "stock", indexes = {@Index(name = "idx_code", columnList = "code"),
        @Index(name = "idx_symbol", columnList = "symbol")})
public class Stock extends BaseEntity {


    @Column(name = "code", columnDefinition = "varchar(6)")
    private String code;


    private String name;

    @Enumerated
    @JSONField(name = "code_category")
    @JsonProperty("code_category")
    @Column(name = "codeCategory")
    private CodeCategory codeCategory;


    @Column(name = "symbol", columnDefinition = "varchar(10)")
    private String symbol;

}
