package com.e8vu7t.datamanipulation;

import java.net.URL;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.csv.CsvURLDataSet;

/**
 * テストユーティリティ
 */
public class TestUtils {
    /**
     * テスト定義に基づきテストを実行する。
     * @param testDefinition テスト定義
     * @throws Exception テスト失敗時
     */
    public static void test(TestDefinition testDefinition) throws Exception {
        // テスト実施前提データをデータベースへ適用
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(testDefinition.getDataSource(),"unit");
        var databaseConfig = databaseTester.getConnection().getConfig();
        databaseConfig.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);

        // 前提データ読み込みパス
        final String givenResourePath = String.format("/%1$s/%2$s/%3$s/given/", 
            testDefinition.getApiFolderName(),
            testDefinition.getFunctionFolderName(), 
            testDefinition.getTestCaseFolderName());
        
        URL givenUrl = TestUtils.class.getResource(givenResourePath);
        databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
        databaseTester.onSetup();

        // テスト実行
        testDefinition.getRequestAndExpectRunnable().runTest();
        
        // データベース期待値 読み込みパス
        final String expectedResourcePath = String.format("/%1$s/%2$s/%3$s/expected/",
            testDefinition.getApiFolderName(),
            testDefinition.getFunctionFolderName(), 
            testDefinition.getTestCaseFolderName());

        var expectedUrl = TestUtils.class.getResource(expectedResourcePath);

        // データベース期待値
        var expectedDataSet = new CsvURLDataSet(expectedUrl);

        // データベース実値
        var actualDataSet = databaseTester.getConnection().createDataSet();

        // テスト実施後データベース実値・期待値妥当性判定処理
        testDefinition.getDatabaseAssertionBiPredicate().judge(actualDataSet, expectedDataSet);
    }
}
