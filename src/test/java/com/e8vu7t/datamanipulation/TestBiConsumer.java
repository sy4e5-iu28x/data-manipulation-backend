package com.e8vu7t.datamanipulation;

/**
 * テスト用実値・期待値妥当性判定Predicate(例外スロー)
 */
public interface TestBiConsumer<T, U> {
    /**
     * 判定処理
     * @param actual 実際の値
     * @param expected 期待値
     * @throws Exception 判定失敗時
     */
    public void judge(T actual, U expected) throws Exception;
}
