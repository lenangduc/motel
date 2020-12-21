package vn.yotel.jobsearch247.core.service;

import vn.yotel.commons.bo.GenericBo;
import vn.yotel.jobsearch247.core.jpa.Transaction;

import java.util.List;

public interface TransactionService extends GenericBo<Transaction, Long> {
    List<Object[]> findByPostId(String post, Long ownerId);
}
