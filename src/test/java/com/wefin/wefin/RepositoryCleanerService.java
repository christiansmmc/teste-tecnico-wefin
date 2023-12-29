package com.wefin.wefin;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Service
public class RepositoryCleanerService {

    private final EntityManager entityManager;
    private List<String> tableNames;

    public RepositoryCleanerService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostConstruct
    void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel()
                .getEntities()
                .stream()
                .map(entityType -> entityType.getJavaType().getAnnotation(Table.class))
                .filter(Objects::nonNull)
                .map(this::convertToTableName)
                .toList();
    }

    @Transactional
    public void resetDatabase() {
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE;").executeUpdate();

        tableNames.stream()
                .filter(this::hasData)
                .forEach(this::truncateTable);

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE;").executeUpdate();
    }

    private void truncateTable(String tableName) {
        entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
    }

    private boolean hasData(String tableName) {
        Object result = entityManager.createNativeQuery("SELECT EXISTS(SELECT 1 FROM " + tableName + ")").getSingleResult();
        if (Objects.isNull(result)) {
            return false;
        }

        if (result instanceof Boolean) {
            return (Boolean) result;
        }

        int found = ((BigInteger) result).intValue();
        return found != 0;
    }

    /**
     * Converts an (optional) schema and table on a {@link Table} annotation to sql table name.
     */
    private String convertToTableName(Table table) {
        String schema = table.schema();
        String tableName = table.name();

        String convertedSchema = StringUtils.hasText(schema) ? schema.toLowerCase() + "." : "";
        String convertedTableName = tableName.replaceAll("([a-z])([A-Z])", "$1_$2");

        return convertedSchema + convertedTableName;
    }
}