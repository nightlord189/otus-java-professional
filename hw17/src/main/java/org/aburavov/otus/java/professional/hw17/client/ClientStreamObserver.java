package org.aburavov.otus.java.professional.hw17.client;

import io.grpc.stub.StreamObserver;
import java.util.concurrent.atomic.AtomicLong;
import org.aburavov.otus.java.professional.hw17.NumberResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientStreamObserver implements StreamObserver<NumberResponse> {

    private static final Logger log = LoggerFactory.getLogger(ClientStreamObserver.class);

    private static final long NO_VALUE = 0;

    private final AtomicLong lastValue = new AtomicLong(NO_VALUE);

    @Override
    public void onNext(NumberResponse response) {
        log.info("new value:{}", response.getValue());
        lastValue.set(response.getValue());
    }

    @Override
    public void onError(Throwable t) {
        log.error("request failed: {}", t.getMessage());
    }

    @Override
    public void onCompleted() {
        log.info("request completed");
    }

    public long takeLastValue() {
        return lastValue.getAndSet(NO_VALUE);
    }
}
