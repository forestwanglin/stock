package ff.three.three.bean.pageable;

import cn.magicwindow.common.bean.BaseBean;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Forest Wang
 * @package cn.magicwindow.meta.bean.pageable
 * @class SortField
 * @email forest@magicwindow.cn
 * @date 22/12/2016 16:20
 * @description
 */
@Data
public class SortField extends BaseBean {

    public SortField() {
    }

    public SortField(String fieldName) {
        this(SortDirection.ASC, fieldName);
    }

    public SortField(SortDirection direction, String fieldName) {
        this.direction = direction;
        this.fieldName = fieldName;
    }


    @JsonProperty("direction")
    @JSONField(name = "direction")
    private SortDirection direction;

    @JsonProperty("field_name")
    @JSONField(name = "field_name")
    private String fieldName;

}
