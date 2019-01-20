package lee.lee.test;

import lee.lee.bean.Customer;
import lee.lee.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

/**
 * @author Lee
 * @Description: ${todo}
 * @date 2019/1/12 0012 下午 4:54
 */
public class TestJpa {
    @Test
    public void testSave(){
        Customer customer=new Customer();
        customer.setCustAddress("烈士陵园");
        customer.setCustLevel("2");
        customer.setCustPhone("110");
        customer.setCustName("赵六");
        customer.setCustSource("hh");
        customer.setCustIndustry("小东海");

        //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager = JpaUtils.getEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(customer);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }

    @Test
    public void testUpdate() {
        //Customer customer = new Customer();
        //customer.setCustId(1L);
        //customer.setCustAddress("壮士陵园");
        //customer.setCustLevel("2");
        //customer.setCustName("李四");
        //customer.setCustSource("hh");

        //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager = JpaUtils.getEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            Customer customer = entityManager.find(Customer.class, 1L);
            customer.setCustName("王五");
            entityManager.merge(customer);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Test
    //find是立即检索,getReference是延迟检索
    public void testFind() {

        //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager = JpaUtils.getEntityManager();


        Customer customer = entityManager.find(Customer.class, 1L);
        System.out.println(customer);

        entityManager.close();
    }


    @Test
    //find是立即检索,getReference是延迟检索
    public void testGet() {

        //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager = JpaUtils.getEntityManager();


        Customer customer = entityManager.getReference(Customer.class, 1L);
        System.out.println(customer);
        entityManager.close();

    }

    @Test
    public void testDelete() {
        //Customer customer = new Customer();
        //customer.setCustId(1L);
        //customer.setCustAddress("壮士陵园");
        //customer.setCustLevel("2");
        //customer.setCustName("李四");
        //customer.setCustSource("hh");

        //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager = JpaUtils.getEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            Customer customer = entityManager.find(Customer.class, 2L);
            entityManager.remove(customer);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }


    /**
     * 用jpql语句查询所有
     */
    @Test
    public void testFindAll() {

        //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager = JpaUtils.getEntityManager();
        String jpql="from Customer";
        Query query = entityManager.createQuery(jpql);
        List<Customer> resultList = query.getResultList();
        for (Customer customer : resultList) {
            System.out.println(customer);
        }

        //Customer customer = entityManager.find(Customer.class, 1L);
        //System.out.println(customer);

        entityManager.close();
    }

    /**
     * 用jpql语句查询分页
     */
    @Test
    public void testLimit() {

        //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager = JpaUtils.getEntityManager();
        String jpql="from Customer";
        Query query = entityManager.createQuery(jpql);
        query.setFirstResult(2);
        query.setMaxResults(2);
        List<Customer> resultList = query.getResultList();
        for (Customer customer : resultList) {
            System.out.println(customer);
        }

        //Customer customer = entityManager.find(Customer.class, 1L);
        //System.out.println(customer);

        entityManager.close();
    }

    /**
     * 用jpql语句查询where条件
     */
    @Test
    public void testWhere() {

        //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager = JpaUtils.getEntityManager();
        String jpql="from Customer where custIndustry like ?";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(1,"%东海%");
        query.setFirstResult(0);
        query.setMaxResults(2);
        List<Customer> resultList = query.getResultList();
        for (Customer customer : resultList) {
            System.out.println(customer);
        }

        //Customer customer = entityManager.find(Customer.class, 1L);
        //System.out.println(customer);

        entityManager.close();
    }


    /**
     * 用jpql语句查询排序条件
     */
    @Test
    public void testOrderBy() {

        //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager = JpaUtils.getEntityManager();
        String jpql="from Customer where custIndustry like ? order by custId desc";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(1,"%东海%");
        query.setFirstResult(0);
        query.setMaxResults(2);
        List<Customer> resultList = query.getResultList();
        for (Customer customer : resultList) {
            System.out.println(customer);
        }

        //Customer customer = entityManager.find(Customer.class, 1L);
        //System.out.println(customer);

        entityManager.close();
    }


    /**
     * 用jpql语句查询聚合函数条件 count(*)
     */
    @Test
    public void testCount() {

        //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager = JpaUtils.getEntityManager();
        String jpql="select count(*) from Customer where custIndustry like ? order by custId desc";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(1,"%东海%");
        Long count = (Long) query.getSingleResult();
        System.out.println(count);

        //Customer customer = entityManager.find(Customer.class, 1L);
        //System.out.println(customer);

        entityManager.close();
    }

}
