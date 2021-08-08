package io.github.eddiediamondfire.economyplus.storage;

import java.sql.Connection;

public interface Storage {

    Connection getConnection();

    void initialiseDatabase();
}
