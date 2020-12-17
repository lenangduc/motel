package vn.yotel.jobsearch247.cms.Contants;

public class Constant {
    public static final String REGEX_PHONE = "^[0-9]{10}$";
    public static final String REGEX_EMAIL = "^([a-zA-Z0-9_\\.+-])+@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,4})$";
    public static final String REGEX_PASSWORD = "^.{6,15}$";
    public static final String REGEX_USER_NAME = "^[a-zA-Z0-9@._]{4,20}$";

    public static int DEFAULT_CANDIDATE_PAGE_NUMBER = 0;
    public static int DEFAULT_CANDIDATE_PAGE_SIZE = 20;
    public static int DEFAULT_CANDIDATE_TOP_PAGE_SIZE = 20;
}
