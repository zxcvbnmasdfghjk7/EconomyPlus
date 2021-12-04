package io.github.zxcvbnmasdfghjk7.economyplus.storage;

import java.sql.Connection;

public interface Storage {

    Connection getConnection();

    void initialiseDatabase();
}
