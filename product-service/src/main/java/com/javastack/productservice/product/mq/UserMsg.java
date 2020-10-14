package com.javastack.productservice.product.mq;

import com.google.common.base.MoreObjects;

/**
 * 用户消息
 *
 */
public class UserMsg {
    /** 消息类型：更新，值为: {@value} */
    public static final String UA_UPDATE = "update";
    /** 消息类型：删除，值为: {@value} */
    public static final String UA_DELETE = "delete";

    private String action;
    private Long userId;
    private String traceId;
    private User user;
    public UserMsg() {  }

    public UserMsg(String action, Long userId, String traceId, User user) {
        this.action = action;
        this.userId = userId;
        this.traceId = traceId;
        this.user = user;
    }


    @Override
    public String toString() {
        return this.toStringHelper().toString();
    }

    protected MoreObjects.ToStringHelper toStringHelper() {
        return MoreObjects.toStringHelper(this)
                .add("action", this.getAction())
                .add("userId", this.getUserId())
                .add("traceId", this.getTraceId())
                .add("user", this.getUser());
    }

    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTraceId() {
        return traceId;
    }
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
