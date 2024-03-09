package com.e8vu7t.datamanipulation;

import javax.sql.DataSource;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvURLDataSet;

import lombok.Data;

/**
 * テスト定義
 */
@Data
public class TestDefinition {
    /**
     * リクエストとレスポンス期待値判定処理
     */
    private TestRunnable requestAndExpectRunnable;

    /**
     * データベース実値・期待値妥当性判定処理
     */
    private TestBiConsumer<IDataSet, CsvURLDataSet> databaseAssertionBiPredicate;

    /**
     * データソース
     */
    private DataSource dataSource;

    /**
     * テスト対象API DBリソースフォルダ名
     */
    private String apiFolderName;

    /**
     * テスト対象APIの機能 DBリソースフォルダ名
     */
    private String FunctionFolderName;

    /*
     * テストケース DBリソースフォルダ名
     */
    private String testCaseFolderName;
}
