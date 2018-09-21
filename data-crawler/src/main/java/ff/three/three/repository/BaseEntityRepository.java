package ff.three.three.repository;

import ff.three.three.domain.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * @author Forest Wang
 * @package io.merculet.repository
 * @class BaseEntityRepository
 * @email forest@magicwindow.cn
 * @date 2018/7/11 13:54
 * @description
 */
@NoRepositoryBean
public interface BaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

    List<T> findByDeleted(boolean deleted);

    Page<T> findByDeleted(boolean deleted, Pageable pageable);

}