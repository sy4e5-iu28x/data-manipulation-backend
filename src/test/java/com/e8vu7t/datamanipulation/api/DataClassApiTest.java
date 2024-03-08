package com.e8vu7t.datamanipulation.api;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.csv.CsvURLDataSet;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.e8vu7t.datamanipulation.TestRunnable;

import jakarta.servlet.Filter;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("unit")
public class DataClassApiTest {

    /**
     * <p>{@literal @BeforeEachで生成設定する}</p>
     */
    private MockMvc mockMvc;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * MockMvcの生成・設定
     */
    @BeforeEach
    public void setUp(){
        // レスポンス文字エンコーディング設定フィルタ
        Filter filter = (request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        };

        // フィルタの適用
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filter)
            .build();
    }    

    /**
     * データクラス作成テスト
     * @param requestBody リクエストボディ
     * @param expectedBody レスポンスボディ期待値
     * @param apiFolderName テスト対象API DBリソースフォルダ名
     * @param testCaseFolderName テストケース DBリソースフォルダ名
     * @throws Exception テスト失敗時
     */
    @ParameterizedTest
    @MethodSource("createTestProvider")
    public void createTest(String requestBody, String expectedBody, String apiFolderName, String testCaseFolderName) throws Exception {
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

        this.test(testRunnable, apiFolderName, testCaseFolderName);
    }

    /**
     * データクラス更新テスト
     * @param requestBody リクエストボディ
     * @param expectedBody レスポンスボディ期待値
     * @param apiFolderName テスト対象API DBリソースフォルダ名
     * @param testCaseFolderName テストケース DBリソースフォルダ名
     * @throws Exception テスト失敗時
     */
    @ParameterizedTest
    @MethodSource("updateTestProvider")
    public void updateTest(String requestBody, String expectedBody, String apiFolderName, String testCaseFolderName) throws Exception {
        // テスト実行内容
        TestRunnable testRunnable = () -> {
            // 更新対象ID
            final var ID = 2;
            // リクエスト生成
            var request = MockMvcRequestBuilders.post(String.format("/dataclasses/%1$d",ID))
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
                    result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                    false);
            };

            // テスト実行
            mockMvc.perform(request)
                .andExpect(statusResultMatcher)
                .andExpect(responseResultMatcher);
        };

        this.test(testRunnable, apiFolderName, testCaseFolderName);
    }

    /**
     * データクラス一覧取得テスト
     * @param expectedBody レスポンスボディ期待値
     * @param apiFolderName テスト対象API DBリソースフォルダ名
     * @param testCaseFolderName テストケース DBリソースフォルダ名
     * @throws Exception テスト失敗時
     */
    @ParameterizedTest
    @MethodSource("findAllTestProvider")
    public void findAllTest(String expectedBody, String apiFolderName, String testCaseFolderName) throws Exception {
        // テスト実行内容
        TestRunnable testRunnable = () -> {
            // リクエスト生成
            var request = MockMvcRequestBuilders.get("/dataclasses");
                
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

        this.test(testRunnable, apiFolderName, testCaseFolderName);
    }

    /**
     * テスト用共通メソッド
     * @param requestAndExpectRunnable リクエストと期待値判定処理
     * @param apiNameResourceFolderName テスト対象のAPI DBリソースフォルダ名
     * @param testCaseFolderName テストケース DBリソースフォルダ名
     * @throws Exception テスト失敗時
     */
    private void test(TestRunnable requestAndExpectRunnable, String apiFolderName, String testCaseFolderName) throws Exception {
        // テスト実施前提データ適用
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource,"unit");
        var databaseConfig = databaseTester.getConnection().getConfig();
        databaseConfig.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);

        final String givenResourePath = String.format("/dataclasses/%1$s/%2$s/given/", apiFolderName, testCaseFolderName);
        URL givenUrl = this.getClass().getResource(givenResourePath);
        databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
        databaseTester.onSetup();

        // テスト実行
        requestAndExpectRunnable.runTest();
        
        // テスト実施後データ検証
        var actualDataSet = databaseTester.getConnection().createDataSet();
        var actualTestTable = actualDataSet.getTable("dataclass_definitions");
        final String expectedResourcePath = String.format("/dataclasses/%1$s/%2$s/expected/", apiFolderName, testCaseFolderName);
        var expectedUrl = this.getClass().getResource(expectedResourcePath);
        var expectedDataSet = new CsvURLDataSet(expectedUrl);
        var expectedTestTable = expectedDataSet.getTable("dataclass_definitions");
        Assertion.assertEquals(expectedTestTable, actualTestTable);
    }

    /**
     * 作成テスト実行データプロバイダ
     * @return テストデータ
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
                "create",
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
                "create",
                "multi-record"
            )
        );
    }

    /**
     * 更新テスト実行データプロバイダ
     * @return テストデータ
     */
    private static Stream<Arguments> updateTestProvider(){
        return Stream.of(
            
            // ケース : レコード複数ありテーブルにて更新
            Arguments.arguments(
                """
                    {
                        "name": "更新後のクラス"
                    }
                """,
                """
                    {
                        "id": 2,
                        "name": "更新後のクラス"
                    }
                        
                """,
                "update",
                "multi-record"
            )
        );
    }

    /**
     * 一覧取得テスト実行データプロバイダ
     * @return テストデータ
     */
    private static Stream<Arguments> findAllTestProvider(){
        return Stream.of(
            
            // ケース : レコード複数ありテーブルにて更新
            Arguments.arguments(
                """
                    [
                        {
                            "id": 1,
                            "name": "クラス1"
                        }, 
                        {
                            "id": 2,
                            "name": "クラス2"
                        },
                        {
                            "id": 3,
                            "name": "クラス3"
                        }
                    ]

                """,
                "find-all",
                "multi-record"
            )
        );
    }
}
