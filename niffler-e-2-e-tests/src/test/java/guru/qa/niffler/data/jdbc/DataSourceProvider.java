package guru.qa.niffler.data.jdbc;

import guru.qa.niffler.data.DataBase;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum DataSourceProvider {
    INSTANCE;

    private final Map<DataBase, DataSource> store = new ConcurrentHashMap<>();

    private DataSource computeDataSource(DataBase dataBase) {
        return store.computeIfAbsent(dataBase, key -> {
            PGSimpleDataSource dataSource = new PGSimpleDataSource();
            dataSource.setUrl(dataBase.getJdbcUrl());
            dataSource.setPassword("secret");
            dataSource.setUser("postgres");
            return dataSource;
        });
    }

    public static DataSource dataSource(DataBase dataBase) {
        return DataSourceProvider.INSTANCE.computeDataSource(dataBase);
    }
}

