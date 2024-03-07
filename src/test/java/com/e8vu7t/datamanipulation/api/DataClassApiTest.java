package com.e8vu7t.datamanipulation.api;

import java.net.URL;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.csv.CsvURLDataSet;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.e8vu7t.datamanipulation.TestRunnable;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("unit")
public class DataClassApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataSource dataSource;

    @ParameterizedTest
    @MethodSource("createTestProvider")
    public void createTest(String requestBody, String expectedBody, String dbPath) throws Exception {
        // テスト実行内容
        TestRunnable testRunnable = () -> {
            // リクエスト生成
            var request = MockMvcRequestBuilders.post("/dataclasses")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);
            
            // ステータス結果検証
            ResultMatcher statusResultMatcher = (result) -> {
                MockMvcResultMatchers.status().isOk();
            };

            // レスポンス結果検証
            ResultMatcher responseResultMatcher = (result) -> {
                JSONAssert.assertEquals(
                    expectedBody,
                    result.getResponse().getContentAsString(),
                    false);
            };

            // テスト実行
            mockMvc.perform(request)
                .andExpect(statusResultMatcher)
                .andExpect(responseResultMatcher);
        };

        this.test(testRunnable, dbPath);
    }

    /**
     * テスト用共通メソッド
     * @param requestAndExpectRunnable リクエストと期待値判定処理
     * @param dbPath データベース読み込みパス
     * @throws Exception テスト失敗時
     */
    private void test(TestRunnable requestAndExpectRunnable, String dbPath) throws Exception {
        // テスト実施前提データ適用
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource,"unit");
        var databaseConfig = databaseTester.getConnection().getConfig();
        databaseConfig.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);

        URL givenUrl = this.getClass().getResource(String.format("/dataclasses/create/%1$s/given/",dbPath));
        databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
        databaseTester.onSetup();

        // テスト実行
        requestAndExpectRunnable.runTest();
        
        // テスト実施後データ検証
        var actualDataSet = databaseTester.getConnection().createDataSet();
        var actualTestTable = actualDataSet.getTable("dataclass_definitions");
        var expectedUrl = this.getClass().getResource(String.format("/dataclasses/create/%1$s/expected/",dbPath));
        var expectedDataSet = new CsvURLDataSet(expectedUrl);
        var expectedTestTable = expectedDataSet.getTable("dataclass_definitions");
        Assertion.assertEquals(expectedTestTable, actualTestTable);
    }

    /**
     * テスト実行データプロバイダ
     * @return
     */
    private static Stream<Arguments> createTestProvider(){
        return Stream.of(
            // ケース：レコードなしテーブルへ追加
            Arguments.arguments(
                """
                    {
                        "name": "新しいクラス"
                    }
                """,
                """
                    {
                        "id": 1,
                        "name": "新しいクラス"
                    }
                        
                """,
                "no-record"
            ),

            // ケース : レコード複数ありテーブルへ追加
            Arguments.arguments(
                """
                    {
                        "name": "新しいクラス"
                    }
                """,
                """
                    {
                        "id": 3,
                        "name": "新しいクラス"
                    }
                        
                """,
                "multi-record"
            )
        );
    }
}
