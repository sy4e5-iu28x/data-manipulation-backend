package com.e8vu7t.datamanipulation.api;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
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
import org.springframework.web.context.WebApplicationContext;

import com.e8vu7t.datamanipulation.CustomMockMvcBuilder;
import com.e8vu7t.datamanipulation.TestBiConsumer;
import com.e8vu7t.datamanipulation.TestDefinition;
import com.e8vu7t.datamanipulation.TestRunnable;
import com.e8vu7t.datamanipulation.TestUtils;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("unit")
public class DataClassDataPropertyRelationApiTest {

    /**
     * テスト対象API名
     */
    private static final String TEST_API_NAME ="dataclass-property-relations";

    /**
     * データベース実値・期待値妥当性判定処理(共通)
     */
    private static final TestBiConsumer<IDataSet, CsvURLDataSet> dbContentsTest = (actualDataSet, expectedDataSet) -> {
        // テーブル実値 データクラス定義
        var actualTestTable = actualDataSet.getTable("dataclass_dataproperty_relation_definitions");
        // テーブル期待値 データクラス定義
        var expectedTestTable = expectedDataSet.getTable("dataclass_dataproperty_relation_definitions");
        // 判定
        Assertion.assertEquals(expectedTestTable, actualTestTable);
    };

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
        // 文字エンコーディング設定済みカスタムMockMvc
        mockMvc = CustomMockMvcBuilder.build(webApplicationContext);
    }    

    /**
     * データクラス作成テスト
     * @param requestBody リクエストボディ
     * @param expectedBody レスポンスボディ期待値
     * @param functionFolderName テスト対象API機能 DBリソースフォルダ名
     * @param testCaseFolderName テストケース DBリソースフォルダ名
     * @throws Exception テスト失敗時
     */
    @ParameterizedTest
    @MethodSource("createTestProvider")
    public void createTest(String requestBody, String expectedBody, String functionFolderName, String testCaseFolderName) throws Exception {
        // テスト実行内容
        TestRunnable testRunnable = () -> {
            // リクエスト生成
            var request = MockMvcRequestBuilders.post("/relations/dataclass-dataproperty")
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

        // テスト定義
        TestDefinition testDefinition = new TestDefinition();
        testDefinition.setDataSource(dataSource);
        testDefinition.setRequestAndExpectRunnable(testRunnable);
        testDefinition.setDatabaseAssertionBiPredicate(dbContentsTest);
        testDefinition.setApiFolderName(TEST_API_NAME);
        testDefinition.setFunctionFolderName(functionFolderName);
        testDefinition.setTestCaseFolderName(testCaseFolderName);

        // テスト実行
        TestUtils.test(testDefinition);
    }

    /**
     * データクラス更新テスト
     * @param requestBody リクエストボディ
     * @param expectedBody レスポンスボディ期待値
     * @param functionFolderName テスト対象API機能 DBリソースフォルダ名
     * @param testCaseFolderName テストケース DBリソースフォルダ名
     * @throws Exception テスト失敗時
     */
    @ParameterizedTest
    @MethodSource("updateTestProvider")
    public void updateTest(String requestBody, String expectedBody, String functionFolderName, String testCaseFolderName) throws Exception {
        // テスト実行内容
        TestRunnable testRunnable = () -> {
            // 更新対象ID
            final var ID = 2;
            // リクエスト生成
            var request = MockMvcRequestBuilders.post(String.format("/relations/dataclass-dataproperty/%1$d",ID))
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

        // テスト定義
        TestDefinition testDefinition = new TestDefinition();
        testDefinition.setDataSource(dataSource);
        testDefinition.setDatabaseAssertionBiPredicate(dbContentsTest);
        testDefinition.setRequestAndExpectRunnable(testRunnable);
        testDefinition.setApiFolderName(TEST_API_NAME);
        testDefinition.setFunctionFolderName(functionFolderName);
        testDefinition.setTestCaseFolderName(testCaseFolderName);

        // テスト実行
        TestUtils.test(testDefinition);
    }

    /**
     * データクラス一覧取得テスト
     * @param expectedBody レスポンスボディ期待値
     * @param functionFolderName テスト対象API機能 DBリソースフォルダ名
     * @param testCaseFolderName テストケース DBリソースフォルダ名
     * @throws Exception テスト失敗時
     */
    @ParameterizedTest
    @MethodSource("findAllTestProvider")
    public void findAllTest(String expectedBody, String functionFolderName, String testCaseFolderName) throws Exception {
        // テスト実行内容
        TestRunnable testRunnable = () -> {
            // リクエスト生成
            var request = MockMvcRequestBuilders.get("/relations/dataclass-dataproperty");
                
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

        // テスト定義
        TestDefinition testDefinition = new TestDefinition();
        testDefinition.setDataSource(dataSource);
        testDefinition.setRequestAndExpectRunnable(testRunnable);
        testDefinition.setDatabaseAssertionBiPredicate(dbContentsTest);
        testDefinition.setApiFolderName(TEST_API_NAME);
        testDefinition.setFunctionFolderName(functionFolderName);
        testDefinition.setTestCaseFolderName(testCaseFolderName);

        // テスト実行
        TestUtils.test(testDefinition);
    }

    /**
     * データクラスIDをキーとした一覧取得テスト
     * @param expectedBody レスポンスボディ期待値
     * @param functionFolderName テスト対象API機能 DBリソースフォルダ名
     * @param testCaseFolderName テストケース DBリソースフォルダ名
     * @throws Exception テスト失敗時
     */
    @ParameterizedTest
    @MethodSource("findByDataClassIdTestProvider")
    public void findByDataClassIdTest(String expectedBody, String functionFolderName, String testCaseFolderName) throws Exception {
        // テスト実行内容
        TestRunnable testRunnable = () -> {
            // 問い合わせデータクラスID
            final var ID = 2;

            // リクエスト生成
            var request = MockMvcRequestBuilders.get(String.format("/relations/dataclass-dataproperty?dataclassid=%1$d", ID));
                
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

        // テスト定義
        TestDefinition testDefinition = new TestDefinition();
        testDefinition.setDataSource(dataSource);
        testDefinition.setRequestAndExpectRunnable(testRunnable);
        testDefinition.setDatabaseAssertionBiPredicate(dbContentsTest);
        testDefinition.setApiFolderName(TEST_API_NAME);
        testDefinition.setFunctionFolderName(functionFolderName);
        testDefinition.setTestCaseFolderName(testCaseFolderName);

        // テスト実行
        TestUtils.test(testDefinition);
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
                        "dataclassId": 2,
                        "datapropertyId": 2
                    }
                """,
                """
                    {
                        "id": 1,
                        "dataclassId": 2,
                        "datapropertyId": 2
                    }
                        
                """,
                "create",
                "no-record"
            ),

            // ケース : レコード複数ありテーブルへ追加
            Arguments.arguments(
                """
                    {
                        "dataclassId": 3,
                        "datapropertyId": 4
                    }
                """,
                """
                    {
                        "id": 3,
                        "dataclassId": 3,
                        "datapropertyId": 4
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
                        "dataclassId": 3,
                        "datapropertyId": 4
                    }
                """,
                """
                    {
                        "id": 2,
                        "dataclassId": 3,
                        "datapropertyId": 4

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
                            "dataclassId": 1,
                            "datapropertyId": 1
                        }, 
                        {
                            "id": 2,
                            "dataclassId": 1,
                            "datapropertyId": 2
                        },
                        {
                            "id": 3,
                            "dataclassId": 2,
                            "datapropertyId": 2
                        }
                    ]

                """,
                "find-all",
                "multi-record"
            )
        );
    }

    /**
     * データクラスIDをキーとした一覧取得テスト実行データプロバイダ
     * @return テストデータ
     */
    private static Stream<Arguments> findByDataClassIdTestProvider(){
        return Stream.of(
            
            // ケース : レコード複数ありテーブルにて更新
            Arguments.arguments(
                """
                    [
                        {
                            "id": 3,
                            "dataclassId": 2,
                            "datapropertyId": 2
                        }, 
                        {
                            "id": 4,
                            "dataclassId": 2,
                            "datapropertyId": 3
                        },
                        {
                            "id": 7,
                            "dataclassId": 2,
                            "datapropertyId": 4
                        }
                    ]

                """,
                "find-by-dataclass-id",
                "multi-record"
            )
        );
    }
}
