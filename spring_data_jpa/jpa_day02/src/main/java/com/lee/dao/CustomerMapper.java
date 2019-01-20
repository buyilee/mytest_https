package com.lee.dao;

import com.lee.bean.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Lee
 * @Description: ${todo}
 * @date 2019/1/14 0014 下午 4:14
 */
public interface CustomerMapper extends JpaRepository<Customer,Long>,JpaSpecificationExecutor<Customer>{

    @Query("from Customer")
    public List<Customer> findAllCustomer();


    @Query("from Customer where custName = ?1 and custAddress = ?2")
    public Customer findCustomerByCustName(String custName,String custAddress);

    @Query("from Customer c")
    public Page<Customer> findCustomerPage(Pageable pageable);

    @Query("update Customer set custName = ?2 where custId = ?1")
    @Modifying
    public void updateCustomer1(Long custId,String custName);


    @Query(value = "select * from cst_customer",nativeQuery = true)
    public List<Customer> findAllSql();


    @Query(value = "select * from cst_customer where cust_name LIKE ?1",nativeQuery = true)
    public List<Customer> findByNameSql(String custName);


    public Customer findByCustName(String custName);

}
