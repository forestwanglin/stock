package ff.three.three.domain;

import cn.magicwindow.common.util.Preconditions;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Forest Wang
 * @package io.merculet.domain
 * @class BaseEntity
 * @email forest@magicwindow.cn
 * @date 03/11/2016 22:47
 * @description
 */
@Data
@ApiModel(value = "抽象实体")
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @ApiModelProperty(value = "主键")
    @JSONField(name = "id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "date_created", nullable = false, updatable = false)
    @ApiModelProperty(value = "创建时间")
    @JsonIgnore
    @JSONField(serialize = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "last_updated", nullable = false)
    @ApiModelProperty(value = "最后更新时间")
    @JsonIgnore
    @JSONField(serialize = false)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastUpdated;

    @Column(name = "deleted", nullable = false)
    @ApiModelProperty(value = "删除状态")
    @JsonIgnore
    @JSONField(serialize = false)
    private boolean deleted = false;

    @Column(name = "version")
    @ApiModelProperty(value = "版本")
    @JSONField(name = "version")
    @JsonProperty("version")
    @Version
    private Long version;

    @Override
    public boolean equals(Object obj) {
        if (Preconditions.isNotBlank(obj) && obj instanceof BaseEntity && Preconditions.isNotBlank(this.getId())) {
            return this.getId().equals(((BaseEntity) obj).getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

}
