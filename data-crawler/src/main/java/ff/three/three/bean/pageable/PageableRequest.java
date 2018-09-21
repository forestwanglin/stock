package ff.three.three.bean.pageable;

import cn.magicwindow.common.bean.BaseBean;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Forest Wang
 * @package cn.magicwindow.meta.bean.pageable
 * @class PageableRequest
 * @email forest@magicwindow.cn
 * @date 22/12/2016 16:19
 * @description
 */
@Data
public class PageableRequest extends BaseBean {

    public PageableRequest() {
    }

    public PageableRequest(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    // zero base
    @JsonProperty("page_number")
    @JSONField(name = "page_number")
    private int pageNumber;

    @JsonProperty("page_size")
    @JSONField(name = "page_size")
    private int pageSize;

    @JsonProperty("sort_fields")
    @JSONField(name = "sort_fields")
    private List<SortField> sortFields;

}
