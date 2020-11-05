package com.ldx.dao;

import com.ldx.pojo.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * JpaRepository<操作的实体类型，主键类型>，封装了CRUD
 * JpaSpecificationExecutor<操作的实体类型>，封装了分页、排序等复杂查询
 */
public interface ResumeDao extends JpaRepository<Resume, Long>, JpaSpecificationExecutor<Resume> {

    //jpql查询
    @Query("from Resume where id=?1") //注意此处操作的是实体类和属性，而不是表和字段
    public Resume findByJpql(Long id);

    @Query("from Resume where id=?1 and name=?2")
    public Resume findByJpql2(Long id, String name);

    // 原生SQL查询
    @Query(value = "select * from tb_resume where name like ?1 and address like ?2", nativeQuery = true)
    public List<Resume> findBySql(String name, String address);

    /**
     * 方法命名规则查询
     * 方法名： findBy + 属性名（首字母大写） + 查询方式（模糊查询、等价查询，如果不写，默认等价）
     */
    public List<Resume> findByNameLike(String name);

}


