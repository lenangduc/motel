package vn.yotel.jobsearch247.cms.enums;

public enum UserStatusEnum {
    INACTIVE(Byte.valueOf("0")), ACTIVE(Byte.valueOf("1"));
    private Byte value;

    UserStatusEnum(Byte value) {
        this.value = value;
    }

    public Byte value() {
        return this.value;
    }
}
