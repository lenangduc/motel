package vn.yotel.jobsearch247.cms.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.yotel.jobsearch247.cms.Helper.TransactionModelHelper;
import vn.yotel.jobsearch247.cms.Model.TransactionModel;
import vn.yotel.jobsearch247.core.jpa.PostDetail;
import vn.yotel.jobsearch247.core.jpa.Transaction;
import vn.yotel.jobsearch247.core.service.PostDetailService;
import vn.yotel.jobsearch247.core.service.TransactionService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/transaction")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PostDetailService postDetailService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list (Model model) {
        List<Object[]> transactions = transactionService.findByPostId(null);
        List<TransactionModel> Transactions = TransactionModelHelper.parseTransactionModels(transactions);
        model.addAttribute("trans", Transactions);
        return "Transaction/List";
    }

    @RequestMapping(value = "/update-status", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String updateIsRental(@RequestParam("status") Integer status,
                                 @RequestParam("id") Long id) {
        Transaction transaction = transactionService.findOne(id);
        Integer oldStatus = transaction.getStatus();
        if (oldStatus != status && oldStatus == 0) {
            transaction.setStatus(status);
            transactionService.update(transaction);
            Date now = new Date();
            PostDetail postDetail = postDetailService.findOne(transaction.getPostId());
            Calendar calendar = Calendar.getInstance();
            Date toDate = calendar.getTime();
            calendar.add(Calendar.DATE, transaction.getDuration());
            calendar.set(Calendar.HOUR, 23);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date dateExpired = calendar.getTime();
            postDetail.setDatePost(new Date());
            postDetail.setDateExpired(dateExpired);
            postDetail.setStatus(1);
            postDetail.setIsPay(1);
            postDetailService.update(postDetail);
        }
        return "success";
    }

}
