package com.rfq.dao.customer;

import com.rfq.dao.BaseRepository;
import com.rfq.entity.customer.Customer;

import java.util.Optional;


public interface CustomerRepository extends BaseRepository<Customer, Integer> {

    Optional<Customer> findByCardCodeAndDataId(String cardCode, String dataId);
}
