package ff.three.three.service.entity;

import cn.magicwindow.common.exception.MwException;
import cn.magicwindow.common.util.Preconditions;
import cn.magicwindow.common.util.RandomUtils;
import ff.three.three.bean.pageable.PageableRequest;
import ff.three.three.bean.pageable.PageableResponse;
import ff.three.three.bean.pageable.SortDirection;
import ff.three.three.bean.pageable.SortField;
import ff.three.three.domain.BaseEntity;
import ff.three.three.repository.BaseEntityRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Forest Wang
 * @package io.merculet.service
 * @class BaseEntityService
 * @email forest@magicwindow.cn
 * @date 15/12/2016 12:06
 * @description
 */
public abstract class BaseEntityService<T extends BaseEntity> implements IBaseEntityService<T> {

    private static final Log logger = LogFactory.getLog(BaseEntityService.class);

    @Autowired
    protected BaseEntityRepository<T> baseEntityRepository;

    /**
     * 获得对象
     *
     * @param id
     * @return
     * @throws MwException
     */
    @Override
    public T get(Long id) throws MwException {
        return encapsulateEntireEntity(id);
    }

    @Override
    public T getWithoutEntireFields(Long id) throws MwException {
        logger.debug("get item without cache");
        T item = null;
        if (Preconditions.isNotBlank(id)) {
            item = baseEntityRepository.getOne(id);
        }
        if (Preconditions.isBlank(item)) {
            logger.warn("not found item with id {}: " + id);
        }
        return item;
    }

    @Transactional
    @Override
    public T save(T item) throws MwException {
        logger.debug("save");
        beforeSave(item);
        T savedItem = baseEntityRepository.saveAndFlush(item);
        savedItem = encapsulateEntireEntity(savedItem.getId());
        afterSaved(savedItem);
        return savedItem;
    }

    /**
     * @param id
     * @return
     * @throws MwException
     */
    @Override
    public boolean delete(Long id) throws MwException {
        T item = get(id);
        if (Preconditions.isNotBlank(item)) {
            beforeDelete(item);
            item.setDeleted(true);
            item = save(item);
            afterDeleted(item);
            return true;
        } else {
            return false;
        }
    }


    /**
     * 物理删除
     *
     * @param id
     * @return
     * @throws MwException
     */
    protected boolean physicalDelete(Long id) throws MwException {
        logger.debug("physical delete");
        if (Preconditions.isNotBlank(id)) {
            baseEntityRepository.deleteById(id);
            return true;
        } else {
            logger.info("id is null");
            return false;
        }
    }

    /**
     * @return
     * @throws MwException
     */
    @Override
    public List<T> list() throws MwException {
        return fillTransientProperties(baseEntityRepository.findByDeleted(false));
    }

    /**
     * @param pageable
     * @return
     * @throws MwException
     */
    @Override
    public PageableResponse<T> list(PageableRequest pageable) throws MwException {
        Page<T> page = baseEntityRepository.findByDeleted(false, PageableConverter.toPageRequest(pageable));
        PageableResponse<T> response = new PageableResponse<>();
        response.setList(fillTransientProperties(page.getContent()));
        response.setTotalCount(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        return response;
    }


    /**
     * 直返这个domain的内容
     *
     * @return
     * @throws MwException
     */
    @Override
    public List<T> lightList() throws MwException {
        return baseEntityRepository.findByDeleted(false);
    }

    /**
     * 直返这个domain的内容
     *
     * @param pageable
     * @return
     * @throws MwException
     */
    @Override
    public PageableResponse<T> lightList(PageableRequest pageable) throws MwException {
        Page<T> page = baseEntityRepository.findByDeleted(false, PageableConverter.toPageRequest(pageable));
        PageableResponse<T> response = new PageableResponse<>();
        response.setList(page.getContent());
        response.setTotalCount(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        return response;
    }

    public static class PageableConverter {

        public static PageRequest toPageRequest(PageableRequest pageableRequest) {
            Sort sort = null;
            if (Preconditions.isNotBlank(pageableRequest.getSortFields())) {
                List<Sort.Order> orders = new ArrayList<>();
                for (SortField sf : pageableRequest.getSortFields()) {
                    Sort.Direction direction = sf.getDirection() == SortDirection.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
                    Sort.Order order = new Sort.Order(direction, sf.getFieldName());
                    orders.add(order);
                }
                sort = new Sort(orders);
            }
            return Preconditions.isNotBlank(sort)
                    ? PageRequest.of(pageableRequest.getPageNumber() - 1, pageableRequest.getPageSize(), sort)
                    : PageRequest.of(pageableRequest.getPageNumber() - 1, pageableRequest.getPageSize());
        }
    }


    /**
     * @param item
     * @return true need filter   default false
     * @throws MwException
     */
    @Override
    public boolean filterEntity(T item) throws MwException {
        return Preconditions.isBlank(item) || (Preconditions.isNotBlank(item) && item.isDeleted());
    }

    /**
     * 删除前需要处理的逻辑
     *
     * @param item
     * @return
     */
    protected void beforeDelete(T item) throws MwException {
        logger.debug("before delete");
    }

    /**
     * 保存后需要处理的逻辑
     *
     * @return
     */
    protected void afterDeleted(T item) throws MwException {
        logger.debug("after deleted");
    }

    /**
     * 保存前需要处理的逻辑
     *
     * @param item
     * @return
     */
    protected void beforeSave(T item) throws MwException {
        logger.debug("before save");
    }


    /**
     * 保存后需要处理的逻辑
     *
     * @param item
     * @return
     */
    protected void afterSaved(T item) throws MwException {
        logger.debug("after saved");
    }


    /**
     * 返回key
     *
     * @return
     */
    protected String generateKey() {
        return RandomUtils.generateUpperString(8);
    }

    /**
     * 设置缓存中需要的对象
     *
     * @param item
     * @return
     */
    protected void setTransientProperties(T item) throws MwException {
        // 不需要存入数据库的字段在这个方法里面设置
    }

    protected List<T> fillTransientProperties(List<T> list) throws MwException {
        if (Preconditions.isNotBlank(list)) {
            for (T t : list) {
                setTransientProperties(t);
            }
        }
        return list;
    }


    /**
     * 组织整个Entity, 不取缓存的时候才会执行这个方法
     *
     * @param id
     * @return
     */
    protected T encapsulateEntireEntity(Long id) throws MwException {
        T item = getWithoutEntireFields(id);
        if (Preconditions.isNotBlank(item)) {
            setTransientProperties(item);
        }
        return item;
    }

    /**
     * @param item
     */
    public void syncOtherRelatedCaches(T item) throws MwException {
        // need override in subclass
    }
}
