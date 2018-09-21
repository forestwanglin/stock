package ff.three.three.service.entity;

import cn.magicwindow.common.exception.MwException;
import ff.three.three.bean.pageable.PageableRequest;
import ff.three.three.bean.pageable.PageableResponse;
import ff.three.three.domain.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Forest Wang
 * @package io.merculet.service
 * @class BaseEntityService
 * @email forest@magicwindow.cn
 * @date 07/12/2016 14:20
 * @description
 */
public interface IBaseEntityService<T extends BaseEntity> {

    Logger logger = LoggerFactory.getLogger(IBaseEntityService.class);

    T get(Long id) throws MwException;

    T getWithoutEntireFields(Long id) throws MwException;

    T save(T item) throws MwException;

    List<T> list() throws MwException;

    PageableResponse<T> list(PageableRequest pageable) throws MwException;

    List<T> lightList() throws MwException;

    PageableResponse<T> lightList(PageableRequest pageable) throws MwException;

    /**
     * soft delete
     *
     * @param id
     * @return
     * @throws MwException
     */
    boolean delete(Long id) throws MwException;

    boolean filterEntity(T item) throws MwException;
}
