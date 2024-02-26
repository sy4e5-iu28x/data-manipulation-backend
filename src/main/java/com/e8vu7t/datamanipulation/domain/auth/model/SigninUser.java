package com.e8vu7t.datamanipulation.domain.auth.model;

import lombok.Data;

/**
 * サインインユーザー
 */
@Data
public class SigninUser {
    /**
     * ユーザー名
     */
    private String username;

    /**
     * パスワード
     */
    private String password;
}
