package ff.three.three.bean.pageable;

import cn.magicwindow.common.bean.BaseBean;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Forest Wang
 * @package cn.magicwindow.meta.bean.pageable
 * @class PageableResponse
 * @email forest@magicwindow.cn
 * @date 22/12/2016 16:25
 * @description
 */
@Data
public class PageableResponse<T extends Serializable> extends BaseBean {

    @JSONField(name = "total_count")
    @JsonProperty("total_count")
    private long totalCount;

    @JSONField(name = "total_pages")
    @JsonProperty("total_pages")
    private int totalPages;

    @JSONField(name = "list")
    @JsonProperty("list")
    private List<T> list;
}
