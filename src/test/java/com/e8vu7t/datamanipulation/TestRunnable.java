package com.e8vu7t.datamanipulation;

/**
 * テスト実行Runnable(例外スロー)
 */
public interface TestRunnable {
    
    /**
     * テスト実行メソッド
     * @throws Exception テスト失敗時
     */
    public void runTest() throws Exception;
}
