package com.javastack.productservice.bus;

import com.google.common.base.MoreObjects;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;


/**
 * 自定义发布事件
 *
 * @author CD826(CD826Dong@gmail.com)
 * @since 1.0.0
 */
public class MyBusEvent extends RemoteApplicationEvent {
    private static final long serialVersionUID = 1L;

    // ========================================================================
    // fields =================================================================
    private String eventType;                               // 事件内容

    public MyBusEvent() {
        super();
    }

    public MyBusEvent(Object source, String originService, String destinationService, String eventType) {
        super(source, originService, destinationService);
        this.eventType = eventType;
    }

    public MyBusEvent(String eventType) {
        super();
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return this.toStringHelper().toString();
    }

    protected MoreObjects.ToStringHelper toStringHelper() {
        return MoreObjects.toStringHelper(this)
                .add("eventType", this.getEventType());
    }

    // ========================================================================
    // setter/getter ==========================================================
    public String getEventType() {
        return eventType;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}