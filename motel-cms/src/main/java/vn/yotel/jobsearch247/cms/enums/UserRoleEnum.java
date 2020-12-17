package vn.yotel.jobsearch247.cms.enums;

public enum  UserRoleEnum {
    ROLE_RENTER(3L, "ROLE_RENTER");
    private Long value;
    private String name;

    UserRoleEnum(Long value, String name) {
        this.value = value;
        this.name = name;
    }

    public Long value() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
}
