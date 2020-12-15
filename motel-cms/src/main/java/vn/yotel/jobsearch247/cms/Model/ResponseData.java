package vn.yotel.jobsearch247.cms.Model;

import java.io.Serializable;

public class ResponseData implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private Integer returnCode;

    private String result;

    Object data;

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseData(Integer returnCode, String result, Object data) {
        super();
        this.returnCode = returnCode;
        this.result = result;
        this.data = data;
    }
}

