import com.ldx.dao.ResumeDao;
import com.ldx.pojo.Resume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ResumeDaoTest {

    @Autowired
    private ResumeDao resumeDao;

    /**
     * --------针对查询的使用进行分析----
     * 方式一：调用集成的接口中的方法   findById(),findOne()
     * 方式二：引入jpql（jpa查询语言）
     * 方式三：引入原生sql
     * 方式四：在接口自定义方法，使用方法命名规则查询
     * 方式五：动态查询--service传入dao的条件不确定，把service条件封装为specification，传给dao
     *
     */


    @Test
    public void testFindById() {
        Optional<Resume> optional = resumeDao.findById(1L);
        Resume resume = optional.get();
        System.out.println(resume);
    }

    @Test
    public void testFindByJpql() {
        Resume jpql = resumeDao.findByJpql(1L);
        System.out.println(jpql);
    }

    @Test
    public void testFindBySql() {
        List<Resume> list = resumeDao.findBySql("李%", "上海%");
        for (int i = 0; i < list.size(); i++) {
            Resume resume = list.get(i);
            System.out.println(resume);
        }
    }

    // 测试排序
    @Test
    public void testSort() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<Resume> list = resumeDao.findAll(sort);
        for (int i = 0; i < list.size(); i++) {
            Resume resume = list.get(i);
            System.out.println(resume);
        }
    }

    // 测试分页
    @Test
    public void testPage() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<Resume> all = resumeDao.findAll(pageable);
        System.out.println(all);
    }

    @Test
    public void testMethdoName() {
        List<Resume> list = resumeDao.findByNameLike("李%");
        for (int i = 0; i < list.size(); i++) {
            Resume resume = list.get(i);
            System.out.println(resume);
        }
    }

    // 动态查询，指定单个条件
    @Test
    public void testSpecfication() {

        Specification<Resume> specification = new Specification<Resume>() {
            @Override
            public Predicate toPredicate(Root<Resume> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 获取name属性
                Path<Object> name = root.get("name");

                // 使用CriteriaBuilder针对name属性构建条件
                Predicate predicate = criteriaBuilder.equal(name, "张三");

                return predicate;
            }
        };

        Optional<Resume> optional = resumeDao.findOne(specification);
        Resume resume = optional.get();
        System.out.println(resume);
    }


    // 动态查询，指定多个条件
    @Test
    public void testSpecfications() {

        Specification<Resume> specification = new Specification<Resume>() {
            @Override
            public Predicate toPredicate(Root<Resume> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Path<Object> name = root.get("name");
                Path<Object> address = root.get("address");

                // 使用CriteriaBuilder针对name属性构建条件
                Predicate predicate1 = criteriaBuilder.equal(name, "张三");
                Predicate predicate2 = criteriaBuilder.like(address.as(String.class), "北%");

                //组合两个条件
                Predicate and = criteriaBuilder.and(predicate1, predicate2);

                return and;
            }
        };

        Optional<Resume> optional = resumeDao.findOne(specification);
        Resume resume = optional.get();
        System.out.println(resume);
    }


}
