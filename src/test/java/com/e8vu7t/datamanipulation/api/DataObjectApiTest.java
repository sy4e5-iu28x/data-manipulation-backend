package com.e8vu7t.datamanipulation.api;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.csv.CsvURLDataSet;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
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
import com.e8vu7t.datamanipulation.TestSupplier;
import com.e8vu7t.datamanipulation.TestUtils;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("unit")
public class DataObjectApiTest {

    /**
     * テスト対象API名
     */
    private static final String TEST_API_NAME ="dataobjects";

    /**
     * データベース実値・期待値妥当性判定処理(共通)
     */
    private static final TestBiConsumer<IDataSet, CsvURLDataSet> dbContentsTest = (actualDataSet, expectedDataSet) -> {
        // 検証対象テーブルマップ(キー:テーブル名、値:期待値テーブルインスタンスサプライヤ)
        final Map<String,TestSupplier<ITable>> targetTableNames = new HashMap<>();
        
        // データ値定義
        final String VALUE_TABLE_NAME = "datavalue_definitions";
        targetTableNames.put(VALUE_TABLE_NAME, 
            () -> {
                var table = expectedDataSet.getTable(VALUE_TABLE_NAME);
                // 判定除外テーブル列
                final var excludedColumnNames = new String[]{"saved_date_time"};
                // 列除外の適用
                return DefaultColumnFilter.excludedColumnsTable(table, excludedColumnNames);
            });

        // データプロパティ・データ値・関係定義
        final String PROPERTY_VALUE_RELATIONS_TABLE_NAME = "dataproperty_value_relation_definitions";
        targetTableNames.put(PROPERTY_VALUE_RELATIONS_TABLE_NAME,
            () -> {
                var table = expectedDataSet.getTable(PROPERTY_VALUE_RELATIONS_TABLE_NAME);
                // 判定除外テーブル列
                final var excludedColumnNames = new String[]{"saved_date_time"};
                // 列除外の適用
                return DefaultColumnFilter.excludedColumnsTable(table, excludedColumnNames);
            });

        // データオブジェクト・データプロパティ・データ値関係定義
        final String OBJECT_PROPERTY_VALUE_RELATIONS_TABLE_NAME = "dataobject_dataproperty_value_relation_definitions";
        targetTableNames.put(OBJECT_PROPERTY_VALUE_RELATIONS_TABLE_NAME,
            () -> {
                var table = expectedDataSet.getTable(OBJECT_PROPERTY_VALUE_RELATIONS_TABLE_NAME);
                // 判定除外テーブル列
                final var excludedColumnNames = new String[]{"saved_date_time"};
                // 列除外の適用
                return DefaultColumnFilter.excludedColumnsTable(table, excludedColumnNames);
            });

        // データプロパティ定義
        final String PROPERTY_TABLE_NAME = "dataproperty_definitions";
        targetTableNames.put(PROPERTY_TABLE_NAME, () -> expectedDataSet.getTable(PROPERTY_TABLE_NAME));

        // データオブジェクト定義
        final String OBJECT_TABLE_NAME = "dataobject_definitions";
        targetTableNames.put(OBJECT_TABLE_NAME, () -> expectedDataSet.getTable(OBJECT_TABLE_NAME));
        
        // データクラス定義
        final String CLASS_TABLE_NAME = "dataclass_definitions";
        targetTableNames.put(CLASS_TABLE_NAME, () -> expectedDataSet.getTable(CLASS_TABLE_NAME));

        // テーブル内容の判定
        for(var targetTableName: targetTableNames.entrySet()){
            // テーブル実値 データクラス定義
            var actualTestTable = actualDataSet.getTable(targetTableName.getKey());
            // テーブル期待値 データクラス定義
            var expectedTestTable = targetTableName.getValue().get();
            // 期待値テーブルと同じ列のみ抽出(テーブル実値から、判定対象外の列を除外)
            var filteredActualTestTable = DefaultColumnFilter.includedColumnsTable(actualTestTable, expectedTestTable.getTableMetaData().getColumns());
            // 判定
            Assertion.assertEquals(expectedTestTable, filteredActualTestTable);
        }
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
     * データオブジェクト作成テスト
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
            var request = MockMvcRequestBuilders.post("/dataobjects")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);
            
            // ステータス結果検証
            ResultMatcher statusResultMatcher = (result) -> {
                MockMvcResultMatchers.status().isOk();
            };
            
            CustomComparator customComparator = new CustomComparator(
                JSONCompareMode.LENIENT,
                new Customization("datavalues[*].savedDateTime", (o1, o2) -> true));

            // レスポンス結果検証
            ResultMatcher responseResultMatcher = (result) -> {
                JSONAssert.assertEquals(
                    expectedBody,
                    result.getResponse().getContentAsString(),
                    customComparator);
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
            var request = MockMvcRequestBuilders.get("/dataobjects");
                
            // ステータス結果検証
            ResultMatcher statusResultMatcher = (result) -> {
                MockMvcResultMatchers.status().isOk();
            };

            CustomComparator customComparator = new CustomComparator(
                JSONCompareMode.LENIENT,
                new Customization("[*].datavalues[*].savedDateTime", (o1, o2) -> true));

            // レスポンス結果検証
            ResultMatcher responseResultMatcher = (result) -> {
                JSONAssert.assertEquals(
                    expectedBody,
                    result.getResponse().getContentAsString(),
                    customComparator);
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
                        "dataproperties": [
                            {
                                "id": 1
                            },
                            {
                                "id": 2
                            }
                        ],
                        "datavalues":[
                            {
                                "dataContent": "値1"
                            },
                            {
                                "dataContent": "値2"
                            }
                        ] 
                    }
                """,
                """
                    {
                        "dataobjectId": 1,
                        "dataclassId": 2,
                        "dataproperties": [
                            {
                                "id": 1,
                                "name": "プロパティ1",
                                "typeClassId": 1
                            },
                            {
                                "id": 2,
                                "name": "プロパティ2",
                                "typeClassId": 1
                            }
                        ],
                        "datavalues": [
                            {
                                "id": 1,
                                "dataContent": "値1",
                                "savedDateTime": ""
                            },
                            {
                                "id": 2,
                                "dataContent": "値2",
                                "savedDateTime": ""
                            }
                        ]
                    }
                        
                """,
                "create",
                "no-record"
            ),

            // ケース : レコード複数ありテーブルへ追加
            Arguments.arguments(
                """
                    {
                        "dataclassId": 1,
                        "dataproperties": [
                            {
                                "id": 2
                            },
                            {
                                "id": 1
                            }
                        ],
                        "datavalues":[
                            {
                                "dataContent": "値3"
                            },
                            {
                                "dataContent": "値4"
                            }
                        ] 
                    }
                """,
                """
                    {
                        "dataobjectId": 2,
                        "dataclassId": 1,
                        "dataproperties": [
                            {
                                "id": 2,
                                "name": "プロパティ2",
                                "typeClassId": 1
                            },
                            {
                                "id": 1,
                                "name": "プロパティ1",
                                "typeClassId": 1
                            }
                        ],
                        "datavalues": [
                            {
                                "id": 3,
                                "dataContent": "値3",
                                "savedDateTime": ""
                            },
                            {
                                "id": 4,
                                "dataContent": "値4",
                                "savedDateTime": ""
                            }
                        ]
                    }
                        
                """,
                "create",
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
            
            // ケース : レコード複数ありテーブル
            Arguments.arguments(
                """
                    [
                        {
                            "dataobjectId": 1,
                            "dataclassId": 2,
                            "dataproperties": [
                                {
                                    "id": 1,
                                    "name": "プロパティ1",
                                    "typeClassId": 1
                                },
                                {
                                    "id": 2,
                                    "name": "プロパティ2",
                                    "typeClassId": 1
                                }
                            ],
                            "datavalues": [
                                {
                                    "id": 1,
                                    "dataContent": "値1",
                                    "savedDateTime": ""
                                },
                                {
                                    "id": 2,
                                    "dataContent": "値2",
                                    "savedDateTime": ""
                                }
                            ]
                        },
                        {
                            "dataobjectId": 2,
                            "dataclassId": 1,
                            "dataproperties": [
                                {
                                    "id": 2,
                                    "name": "プロパティ2",
                                    "typeClassId": 1
                                },
                                {
                                    "id": 1,
                                    "name": "プロパティ1",
                                    "typeClassId": 1
                                }
                            ],
                            "datavalues": [
                                {
                                    "id": 3,
                                    "dataContent": "値3",
                                    "savedDateTime": ""
                                },
                                {
                                    "id": 4,
                                    "dataContent": "値4",
                                    "savedDateTime": ""
                                }
                            ]
                        }
                    ]

                """,
                "find-all",
                "multi-record"
            )
        );
    }
}
