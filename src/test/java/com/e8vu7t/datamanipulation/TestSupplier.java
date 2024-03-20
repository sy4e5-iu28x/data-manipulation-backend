package com.e8vu7t.datamanipulation;

/**
 * テスト用 値Supplier(例外スロー)
 */
public interface TestSupplier<T> {
    /**
     * 値提供処理
     * @throws Exception 処理失敗時
     */
    public T get() throws Exception;
}
