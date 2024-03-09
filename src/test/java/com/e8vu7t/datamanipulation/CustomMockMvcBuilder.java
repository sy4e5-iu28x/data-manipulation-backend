package com.e8vu7t.datamanipulation;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.servlet.Filter;

/**
 * カスタムMockMvcビルダー
 */
public class CustomMockMvcBuilder {
    public static MockMvc build(WebApplicationContext webApplicationContext) {
        // レスポンス文字エンコーディング設定フィルタ
        Filter filter = (request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        };

        // フィルタの適用
        return MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filter)
            .build();
    }
}
