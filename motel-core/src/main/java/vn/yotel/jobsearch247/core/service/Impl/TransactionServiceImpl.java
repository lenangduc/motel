package vn.yotel.jobsearch247.core.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vn.yotel.commons.bo.impl.GenericBoImpl;
import vn.yotel.jobsearch247.core.jpa.PostDetail;
import vn.yotel.jobsearch247.core.jpa.Transaction;
import vn.yotel.jobsearch247.core.repository.TransactionRepo;
import vn.yotel.jobsearch247.core.service.PostDetailService;
import vn.yotel.jobsearch247.core.service.TransactionService;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "transactionService")
@Slf4j
public class TransactionServiceImpl extends GenericBoImpl<Transaction, Long> implements TransactionService {

    @Resource
    private TransactionRepo transactionRepo;

    @Override
    public List<Object[]> findByPostId(String post) {
        return transactionRepo.findByPostId(post);
    }

    @Override
    public <E extends JpaRepository<Transaction, Long>> E getDAO() {
        return (E)transactionRepo;
    }
}
