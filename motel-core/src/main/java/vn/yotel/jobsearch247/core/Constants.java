package vn.yotel.jobsearch247.core;

public interface Constants {

    public interface HazelcastTopicName {
        public static final String ENABLE_THREAD_SCAN_POST = "_pubsub_events_enable_thread_scan_posts_by_account";
        public static final String DISABLE_THREAD_SCAN_POST = "_pubsub_events_disable_thread_scan_posts_by_account";

        public static final String ENABLE_THREAD_SCAN_COMMENT = "_pubsub_events_enable_thread_scan_comments_by_account";

        public static final String FORCE_SCAN_POST_RELOAD_KEYWORDS_RECRUIT = "_pubsub_scan_post_reload_keywords_recruit";
        String OFFER_SCAN_POST = "_pubsub_offer_scan_post";
    }

    public interface ElementsForScanPost {
        public static final String            CONTAINER_ALL_POST_ELEMENT                = "//section";
        public static final String              CONTAINER_POST_ELEMENTS                     = ".//article";

            public static final String              AUTHOR_AND_CONTENT_CONTAINER_ELEMENT        = ".//div[@class='dh']";
                public static final String              HEADER_ELEMENT                              = ".//header";
                    public static final String              AUTHOR_CONTAINER_ELEMENT                    = ".//h3";
                public static final String              CONTENT_CONTAINER_ELEMENT                   = ".//div[@class='dn' or @class='dm']";
                    public static final String              BTN_MORE_CONTENT_ELEMENT                    = ".//a[contains(text(),'More') or contains(text(),'Khác')]";

            public static final String              ACTION_CONTAINER_ELEMENT                    = ".//footer";
//                public static final String              POST_TIME_TITLE_ELEMENT                     = ".//div[@class='cq cr']";
                public static final String              POST_TIME_TITLE_ELEMENT                     = ".//abbr";
//                public static final String              FULL_STORY_ELEMENT                          = ".//a[contains(text(),'Full Story')]";
                public static final String              FULL_STORY_ELEMENT                          = ".//a[contains(@href,'?view=permalink&id=')]";

        public static final String            SEE_MORE_POSTS_ELEMENT                    = "//span[contains(text(),'See More Posts') or contains(text(),'Xem thêm bài viết')]";

        public static final String            CONTENT_FULL_POST_ELEMENT                 = "//div[@class='bv']";

    }

    public interface ElementsHomeGroup {
        public static final String      META_ELEMENT = "//meta[contains(@content, 'fb://group/?id=')]";
    }

    public interface TypeGetInfo {
        public static Integer GET_INFO_GROUP_FOR_ADD_GROUP = 1;
        public static Integer GET_INFO_GROUP_FOR_REPORT = 2;
        public static Integer GET_INFO_GROUP_DAILY = 3;
    }

    public interface HomePage {
        public static final String urlHomePage = "https://m.facebook.com/home.php";

        public static final String BTN_LOGOUT_ELEMENT = "//a[@id='mbasic_logout_button']";
        public static final String BTN_CONFIRM_LOGOUT_ELEMENTS = "//input[@type='submit']";
    }

    public interface Elements {
        public static final String BTN_HOME_ELEMENT = "//div[@id='u_0_f']";
    }

    public interface KeySysParam {
        public static final String LIST_KEYWORD_RECRUIT = "_LIST_KEYWORD_RECRUIT_";
        public static final String LIST_KEYWORD_BLACKLIST = "_LIST_KEYWORD_BLACKLIST_";
    }

    interface HzKey {
        String MAP_PARAMS = "jobsearch247_Hz_map_params";
        String TIME_MILLIS_LAST_LOGIN = "TIME_MILLIS_LAST_LOGIN";
        String PREFIX = "jobsHz_";
    }
}
