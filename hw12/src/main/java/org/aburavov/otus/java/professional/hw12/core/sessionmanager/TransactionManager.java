package org.aburavov.otus.java.professional.hw12.core.sessionmanager;

import org.aburavov.otus.java.professional.hw12.core.sessionmanager.TransactionAction;

public interface TransactionManager {

    <T> T doInTransaction(TransactionAction<T> action);

    <T> T doInReadOnlyTransaction(TransactionAction<T> action);
}
