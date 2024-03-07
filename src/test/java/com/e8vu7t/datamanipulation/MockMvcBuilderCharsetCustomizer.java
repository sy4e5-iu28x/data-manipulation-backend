package com.e8vu7t.datamanipulation;

import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;

/**
 * MockMvc文字コードカスタマイザー
 * 日本語文字化け防止のため、Responseの文字エンコーディングをUTF-8に設定する。
 */
@Component
class MockMvcBuilderCharsetCustomizer implements MockMvcBuilderCustomizer {

    @Override
    public void customize(ConfigurableMockMvcBuilder<?> builder) {
        builder.alwaysDo(result -> result.getResponse().setCharacterEncoding("UTF-8"));
    }
    
}