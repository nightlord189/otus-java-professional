package org.aburavov.otus.java.professional.hw09.jdbc.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Field;
import java.util.List;
import org.aburavov.otus.java.professional.hw09.crm.model.Client;
import org.aburavov.otus.java.professional.hw09.crm.model.Manager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EntityClassMetaDataImplTest {

    @Test
    @DisplayName("Разбирает Client: имя таблицы, конструктор, поля, id")
    void shouldParseClient() {
        var metaData = new EntityClassMetaDataImpl<>(Client.class);

        assertThat(metaData.getName()).isEqualTo("client");
        assertThat(metaData.getConstructor().getParameterTypes()).containsExactly(Long.class, String.class);
        assertThat(metaData.getIdField().getName()).isEqualTo("id");
        assertThat(fieldNames(metaData.getAllFields())).containsExactly("id", "name");
        assertThat(fieldNames(metaData.getFieldsWithoutId())).containsExactly("name");
    }

    @Test
    @DisplayName("Разбирает Manager с произвольным именем id-поля")
    void shouldParseManager() {
        var metaData = new EntityClassMetaDataImpl<>(Manager.class);

        assertThat(metaData.getName()).isEqualTo("manager");
        assertThat(metaData.getConstructor().getParameterTypes())
                .containsExactly(Long.class, String.class, String.class);
        assertThat(metaData.getIdField().getName()).isEqualTo("no");
        assertThat(fieldNames(metaData.getAllFields())).containsExactly("no", "label", "param1");
        assertThat(fieldNames(metaData.getFieldsWithoutId())).containsExactly("label", "param1");
    }

    @Test
    @DisplayName("Падает, если @Id отсутствует")
    void shouldFailWhenNoId() {
        assertThatThrownBy(() -> new EntityClassMetaDataImpl<>(NoId.class))
                .isInstanceOf(EntityClassMetaDataException.class)
                .hasMessageContaining("No field annotated with @Id");
    }

    @Test
    @DisplayName("Падает, если @Id больше одного")
    void shouldFailWhenMultipleIds() {
        assertThatThrownBy(() -> new EntityClassMetaDataImpl<>(TwoIds.class))
                .isInstanceOf(EntityClassMetaDataException.class)
                .hasMessageContaining("Exactly one @Id field expected");
    }

    @Test
    @DisplayName("Падает, если нет all-args конструктора")
    void shouldFailWhenNoAllArgsConstructor() {
        assertThatThrownBy(() -> new EntityClassMetaDataImpl<>(NoAllArgsConstructor.class))
                .isInstanceOf(EntityClassMetaDataException.class)
                .hasMessageContaining("No all-args constructor");
    }

    private static List<String> fieldNames(List<Field> fields) {
        return fields.stream().map(Field::getName).toList();
    }

    @SuppressWarnings("unused")
    static class NoId {
        private Long id;
        private String name;

        public NoId(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @SuppressWarnings("unused")
    static class TwoIds {
        @Id
        private Long id;

        @Id
        private Long secondId;

        private String name;

        public TwoIds(Long id, Long secondId, String name) {
            this.id = id;
            this.secondId = secondId;
            this.name = name;
        }
    }

    @SuppressWarnings("unused")
    static class NoAllArgsConstructor {
        @Id
        private Long id;

        private String name;

        public NoAllArgsConstructor(String name) {
            this.name = name;
        }
    }
}
