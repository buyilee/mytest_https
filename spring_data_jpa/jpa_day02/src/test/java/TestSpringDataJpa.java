import com.lee.bean.Customer;
import com.lee.dao.CustomerMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.lang.Nullable;
import org.springframework.test.annotation.Commit;
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
public class TestSpringDataJpa {
    @Autowired
    private CustomerMapper customerMapper;

    @Test
    public void testFindOne(){
        Optional<Customer> customer = customerMapper.findById(3L);
        Customer customer1=Optional.ofNullable(customer).map(c->c.get()).orElse(new Customer());
        System.out.println(customer1);
    }

    @Test
    public void testSave(){
        Customer customer=new Customer();
        customer.setCustName("大雄");
        customer.setCustIndustry("学生");
        customer.setCustAddress("野比");
        customerMapper.save(customer);
    }

    @Test
    public void testUpdate1(){
        //Customer customer2=customerMapper.findById(9L).get();
        //String custAddress = Optional.ofNullable(customer2).map(c -> c.getCustAddress()).orElse("对象为null");
        //System.out.println(custAddress);
        Customer customer = new Customer();
        customer.setCustId(8L);
        customer.setCustName("胖虎");
        customerMapper.save(customer);
    }

    @Test
    public void testUpdate(){
        Customer customer = customerMapper.findById(9L).get();
        customer.setCustName("胖虎");
        customerMapper.save(customer);
    }

    @Test
    public void testDelete(){
        //Customer customer = customerMapper.findById(8L).get();
        Customer customer=new Customer();
        customer.setCustId(9L);
        customerMapper.delete(customer);
    }

    @Test
    public void testFindAll(){
        //Customer customer = customerMapper.findById(8L).get();
        List<Customer> customerList = customerMapper.findAll();
        for (Customer customer : customerList) {
            System.out.println(customer);
        }
    }
    @Test
    public void testFindAllSort(){
        //Customer customer = customerMapper.findById(8L).get();
        Sort sort =new Sort(Sort.Direction.DESC,"custId");
        List<Customer> customerList = customerMapper.findAll(sort);
        for (Customer customer : customerList) {
            System.out.println(customer);
        }
    }

    @Test
    public void testFindAllPage(){
        //Customer customer = customerMapper.findById(8L).get();
        Sort sort =new Sort(Sort.Direction.DESC,"custId");
        Pageable page=new PageRequest(0,2,sort);
        Page<Customer> customerList = customerMapper.findAll(page);
        for (Customer customer : customerList.getContent()) {
            System.out.println(customer);
        }
    }

    @Test
    public void testCount(){
        //Customer customer = customerMapper.findById(8L).get();
        long count = customerMapper.count();
        System.out.println(count);
    }

    @Test
    public void testExists(){
        //Customer customer = customerMapper.findById(8L).get();
        boolean b = customerMapper.existsById(4L);
        System.out.println("Id为4的用户是否存在:"+b);
    }

    @Test
    @Transactional
    public void testGetOne(){
        //Customer customer = customerMapper.findById(8L).get();
        Customer one = customerMapper.getOne(4L);
        System.out.println(one);
    }

    @Test
    public void testFindAllCustomer(){
        //Customer customer = customerMapper.findById(8L).get();
        List<Customer> customers = customerMapper.findAllCustomer();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void testFindCustomerBy(){
        //Customer customer = customerMapper.findById(8L).get();
        Customer customer = customerMapper.findCustomerByCustName("大雄","野比");
        System.out.println(customer);
    }

    @Test
    public void testFindCustomerPage(){
        Pageable pageAble=new PageRequest(0,4);
        Page<Customer> customerPage = customerMapper.findCustomerPage(pageAble);
        List<Customer> customers = customerPage.getContent();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    @Transactional
    //@Commit
    @Rollback(false)
    public void testUpdateCustomer(){
        customerMapper.updateCustomer1(7L,"哆啦B梦");
    }


    @Test
    public void testFindAllSql(){
        List<Customer> customers = customerMapper.findAllSql();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void testFindByNameSql(){
        List<Customer> customers = customerMapper.findByNameSql("%啦%");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void testFindByName(){
        Customer customer = customerMapper.findByCustName("哆啦B梦");
        System.out.println(customer);
    }


    @Test
    public void testSpecificationQuery(){
        Specification specification=new Specification() {
            @Nullable
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //Path custId = root.get("custId");
                //Predicate predicate = criteriaBuilder.equal(custId, 4L);
                Path custName = root.get("custName");
                Predicate predicate1 = criteriaBuilder.like(custName, "王%");
                Path custIndustry = root.get("custIndustry");
                Predicate predicate2 = criteriaBuilder.equal(custIndustry, "小东海");
                Predicate predicate = criteriaBuilder.and(predicate1, predicate2);
                return predicate;
            }
        };
        //List<Customer> customers = customerMapper.findAll(specification);

        Sort sort=new Sort(Sort.Direction.DESC,"custId");
        Pageable p=new PageRequest(0,2,sort);
        Page<Customer> page  = customerMapper.findAll(specification, p);
        for (Customer customer : page.getContent()) {
            System.out.println(customer);
        }
    }


}
