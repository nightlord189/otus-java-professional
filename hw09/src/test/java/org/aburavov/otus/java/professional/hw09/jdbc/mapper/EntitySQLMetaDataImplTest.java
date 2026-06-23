package org.aburavov.otus.java.professional.hw09.jdbc.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.aburavov.otus.java.professional.hw09.crm.model.Client;
import org.aburavov.otus.java.professional.hw09.crm.model.Manager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EntitySQLMetaDataImplTest {

    @Test
    @DisplayName("Генерирует SQL для Client")
    void shouldBuildSqlForClient() {
        var sqlMetaData = new EntitySQLMetaDataImpl(new EntityClassMetaDataImpl<>(Client.class));

        assertThat(sqlMetaData.getSelectAllSql()).isEqualTo("select id, name from client");
        assertThat(sqlMetaData.getSelectByIdSql()).isEqualTo("select id, name from client where id = ?");
        assertThat(sqlMetaData.getInsertSql()).isEqualTo("insert into client(name) values (?)");
        assertThat(sqlMetaData.getUpdateSql()).isEqualTo("update client set name = ? where id = ?");
    }

    @Test
    @DisplayName("Генерирует SQL для Manager с несколькими колонками и нестандартным id")
    void shouldBuildSqlForManager() {
        var sqlMetaData = new EntitySQLMetaDataImpl(new EntityClassMetaDataImpl<>(Manager.class));

        assertThat(sqlMetaData.getSelectAllSql()).isEqualTo("select no, label, param1 from manager");
        assertThat(sqlMetaData.getSelectByIdSql()).isEqualTo("select no, label, param1 from manager where no = ?");
        assertThat(sqlMetaData.getInsertSql()).isEqualTo("insert into manager(label, param1) values (?, ?)");
        assertThat(sqlMetaData.getUpdateSql()).isEqualTo("update manager set label = ?, param1 = ? where no = ?");
    }
}
