package com.example.ntb.ui.city.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by ccy.
 * Date: 2021/10/26
 * Describe :
 */
@Entity
public class MessageDBInfo {

    @Id
    private Long id;

    /**
     * 消息ID
     */
    private String msgId;

    @Generated(hash = 1798294289)
    public MessageDBInfo(Long id, String msgId) {
        this.id = id;
        this.msgId = msgId;
    }

    @Generated(hash = 2118457199)
    public MessageDBInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsgId() {
        return this.msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Override
    public String toString() {
        return "MessageDBInfo{" +
                "id=" + id +
                ", msgId='" + msgId + '\'' +
                '}';
    }
}

