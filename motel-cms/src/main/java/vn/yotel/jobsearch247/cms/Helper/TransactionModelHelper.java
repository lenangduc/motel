package vn.yotel.jobsearch247.cms.Helper;

import org.apache.commons.collections.CollectionUtils;
import vn.yotel.jobsearch247.cms.Model.TransactionModel;
import vn.yotel.jobsearch247.cms.util.TypeConvert;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransactionModelHelper {
    public static List<TransactionModel> parseTransactionModels(List<Object[]> objects) {
        if (CollectionUtils.isEmpty(objects)) {
            return new ArrayList<>();
        }

        List<TransactionModel> Transactions = new ArrayList<>();
        try {
            for (Object[] object : objects) {
                TransactionModel transactionModel = new TransactionModel();

                BigInteger id = TypeConvert.parseObj(object[0], BigInteger.class);
                transactionModel.setId(id == null ? null : id.longValue());
//                postListModel.setId(TypeConvert.parseObj(object[0], Long.class));
                transactionModel.setPostId(TypeConvert.parseObj(object[1], String.class));
                transactionModel.setDateRequest(TypeConvert.parseObj(object[2], Timestamp.class));
                transactionModel.setDuration(TypeConvert.parseObj(object[3], Integer.class));
                transactionModel.setMoney(TypeConvert.parseObj(object[4], Integer.class));
                transactionModel.setStatus(TypeConvert.parseObj(object[5], Integer.class));
                Transactions.add(transactionModel);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Transactions;
    }
}
