package org.aburavov.otus.java.professional.hw17.server;

import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import org.aburavov.otus.java.professional.hw17.NumberResponse;
import org.aburavov.otus.java.professional.hw17.NumbersServiceGrpc;
import org.aburavov.otus.java.professional.hw17.SequenceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumbersServiceImpl extends NumbersServiceGrpc.NumbersServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(NumbersServiceImpl.class);

    private static final long GENERATION_DELAY_MS = 2000;

    @Override
    public void generateSequence(SequenceRequest request, StreamObserver<NumberResponse> responseObserver) {
        long firstValue = request.getFirstValue();
        long lastValue = request.getLastValue();
        log.info("server: generating sequence, firstValue:{}, lastValue:{}", firstValue, lastValue);

        try {
            for (long value = firstValue + 1; value <= lastValue; value++) {
                Thread.sleep(GENERATION_DELAY_MS);
                if (Context.current().isCancelled()) {
                    log.info("server: client is gone, generation stopped on value:{}", value);
                    return;
                }
                log.info("server: sending value:{}", value);
                responseObserver.onNext(
                        NumberResponse.newBuilder().setValue(value).build());
            }
            responseObserver.onCompleted();
            log.info("server: sequence completed");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            responseObserver.onError(e);
        }
    }
}
