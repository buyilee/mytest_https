import com.lee.bean.Customer;
import com.lee.dao.CustomerMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;


/**
 * @author Lee
 * @Description: ${todo}
 * @date 2019/1/14 0014 下午 4:17
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestSpringDataJpaSpecification {
    @Autowired
    private CustomerMapper customerMapper;


    @Test
    public void testFindAllEqCustName(){
        Specification<Customer> specification=new Specification<Customer>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Customer> custName = root.get("custName");
                Predicate predicate = criteriaBuilder.equal(custName, "王八");
                return predicate;
            }
        };
        List<Customer>  customers =  customerMapper.findAll(specification);
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void testFindAllLikeCustName(){
        Specification<Customer> specification=new Specification<Customer>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Customer> custName = root.get("custName");
                Predicate predicate = criteriaBuilder.like(custName.as(String.class), "王%");
                return predicate;
            }
        };
        List<Customer>  customers =  customerMapper.findAll(specification);
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }


    @Test
    public void testFindAllLikeCustNameAndEqCustIndustry(){
        Specification<Customer> specification=new Specification<Customer>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Customer> custName = root.get("custName");
                Predicate predicate1 = criteriaBuilder.like(custName.as(String.class), "王%");
                Path<Object> custIndustry = root.get("custIndustry");
                Predicate predicate2 = criteriaBuilder.equal(custIndustry, "小东海");
                Predicate predicate = criteriaBuilder.and(predicate1, predicate2);
                return predicate;
            }
        };
        List<Customer>  customers =  customerMapper.findAll(specification);
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }


    @Test
    public void testFindAllLikeCustNameAndEqCustIndustrySort(){
        Specification<Customer> specification=new Specification<Customer>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Customer> custName = root.get("custName");
                Predicate predicate1 = criteriaBuilder.like(custName.as(String.class), "王%");
                Path<Object> custIndustry = root.get("custIndustry");
                Predicate predicate2 = criteriaBuilder.equal(custIndustry, "小东海");
                Predicate predicate = criteriaBuilder.and(predicate1, predicate2);
                return predicate;
            }
        };

        Sort sort=new Sort(Sort.Direction.DESC,"custId");
        List<Customer>  customers =  customerMapper.findAll(specification,sort);
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }


    @Test
    public void testFindAllLikeCustNameAndEqCustIndustrySortPage(){
        Specification<Customer> specification=new Specification<Customer>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Customer> custName = root.get("custName");
                Predicate predicate1 = criteriaBuilder.like(custName.as(String.class), "王%");
                Path<Object> custIndustry = root.get("custIndustry");
                Predicate predicate2 = criteriaBuilder.equal(custIndustry, "小东海");
                Predicate predicate = criteriaBuilder.and(predicate1, predicate2);
                return predicate;
            }
        };

        Sort sort=new Sort(Sort.Direction.DESC,"custId");
        Pageable pa=new PageRequest(0,2,sort);
        Page<Customer>  page =  customerMapper.findAll(specification,pa);
        for (Customer customer : page.getContent()) {
            System.out.println(customer);
        }
    }



}
