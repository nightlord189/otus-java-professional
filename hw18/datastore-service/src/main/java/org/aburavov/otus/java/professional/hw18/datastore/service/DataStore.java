package org.aburavov.otus.java.professional.hw18.datastore.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.aburavov.otus.java.professional.hw18.datastore.domain.Message;

public interface DataStore {

    Mono<Message> saveMessage(Message message);

    Flux<Message> loadMessages(String roomId);

    Flux<Message> loadAllMessages();
}
