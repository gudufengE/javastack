package com.javastack.productservice.product.mq;

import lombok.Data;

import java.io.Serializable;

//user信息
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nickname;                                // 昵称
    private String avatar;                                  // 用户头像
}
