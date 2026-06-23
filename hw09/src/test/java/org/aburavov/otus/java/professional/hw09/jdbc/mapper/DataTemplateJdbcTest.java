package org.aburavov.otus.java.professional.hw09.jdbc.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.aburavov.otus.java.professional.hw09.core.repository.executor.DbExecutor;
import org.aburavov.otus.java.professional.hw09.crm.model.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DataTemplateJdbcTest {

    private final EntityClassMetaData<Client> classMetaData = new EntityClassMetaDataImpl<>(Client.class);
    private final EntitySQLMetaData sqlMetaData = new EntitySQLMetaDataImpl(classMetaData);

    @Test
    @DisplayName("findById возвращает смапленный объект")
    void shouldMapRowToObject() {
        var executor = new FakeDbExecutor();
        executor.rows = List.of(row("id", 42L, "name", "Vasya"));
        var template = new DataTemplateJdbc<>(executor, sqlMetaData, classMetaData);

        Optional<Client> result = template.findById(stubConnection(), 42L);

        assertThat(executor.lastSql).isEqualTo("select id, name from client where id = ?");
        assertThat(executor.lastParams).containsExactly(42L);
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(42L);
        assertThat(result.get().getName()).isEqualTo("Vasya");
    }

    @Test
    @DisplayName("findById возвращает пустой Optional, если строки нет")
    void shouldReturnEmptyWhenNotFound() {
        var executor = new FakeDbExecutor();
        executor.rows = List.of();
        var template = new DataTemplateJdbc<>(executor, sqlMetaData, classMetaData);

        Optional<Client> result = template.findById(stubConnection(), 1L);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findAll мапит все строки")
    void shouldMapAllRows() {
        var executor = new FakeDbExecutor();
        executor.rows = List.of(row("id", 1L, "name", "A"), row("id", 2L, "name", "B"));
        var template = new DataTemplateJdbc<>(executor, sqlMetaData, classMetaData);

        var result = template.findAll(stubConnection());

        assertThat(result).extracting(Client::getName).containsExactly("A", "B");
    }

    @Test
    @DisplayName("insert передаёт SQL и значения полей без id")
    void shouldPassInsertParams() {
        var executor = new FakeDbExecutor();
        var template = new DataTemplateJdbc<>(executor, sqlMetaData, classMetaData);

        template.insert(stubConnection(), new Client(100L, "Petya"));

        assertThat(executor.lastSql).isEqualTo("insert into client(name) values (?)");
        assertThat(executor.lastParams).containsExactly("Petya");
    }

    @Test
    @DisplayName("update передаёт значения полей, id идёт последним параметром")
    void shouldPassUpdateParams() {
        var executor = new FakeDbExecutor();
        var template = new DataTemplateJdbc<>(executor, sqlMetaData, classMetaData);

        template.update(stubConnection(), new Client(7L, "Updated"));

        assertThat(executor.lastSql).isEqualTo("update client set name = ? where id = ?");
        assertThat(executor.lastParams).containsExactly("Updated", 7L);
    }

    private static Map<String, Object> row(String k1, Object v1, String k2, Object v2) {
        var map = new LinkedHashMap<String, Object>();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    /** Соединение в этих тестах не используется (вся работа уходит в FakeDbExecutor). */
    private static Connection stubConnection() {
        return (Connection) Proxy.newProxyInstance(
                DataTemplateJdbcTest.class.getClassLoader(),
                new Class<?>[] {Connection.class},
                (proxy, method, methodArgs) -> null);
    }

    /** Простой fake без mockito: запоминает sql/params и подставляет заранее заданные строки в rsHandler. */
    private static final class FakeDbExecutor implements DbExecutor {
        private String lastSql;
        private List<Object> lastParams;
        private List<Map<String, Object>> rows = List.of();

        @Override
        public long executeStatement(Connection connection, String sql, List<Object> params) {
            this.lastSql = sql;
            this.lastParams = new ArrayList<>(params);
            return 1L;
        }

        @Override
        public <T> Optional<T> executeSelect(
                Connection connection, String sql, List<Object> params, Function<ResultSet, T> rsHandler) {
            this.lastSql = sql;
            this.lastParams = new ArrayList<>(params);
            return Optional.ofNullable(rsHandler.apply(fakeResultSet(rows)));
        }
    }

    /** ResultSet через Proxy: поддерживает только next() и getObject(String). */
    private static ResultSet fakeResultSet(List<Map<String, Object>> rows) {
        var cursor = new int[] {-1};
        return (ResultSet) Proxy.newProxyInstance(
                DataTemplateJdbcTest.class.getClassLoader(),
                new Class<?>[] {ResultSet.class},
                (proxy, method, methodArgs) -> switch (method.getName()) {
                    case "next" -> ++cursor[0] < rows.size();
                    case "getObject" -> rows.get(cursor[0]).get((String) methodArgs[0]);
                    default -> null;
                });
    }
}
