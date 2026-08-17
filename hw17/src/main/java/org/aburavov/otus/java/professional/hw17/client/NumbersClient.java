package org.aburavov.otus.java.professional.hw17.client;

import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import org.aburavov.otus.java.professional.hw17.NumbersServiceGrpc;
import org.aburavov.otus.java.professional.hw17.SequenceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumbersClient {

    private static final Logger log = LoggerFactory.getLogger(NumbersClient.class);

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws InterruptedException {
        log.info("numbers Client is starting...");

        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var streamObserver = new ClientStreamObserver();
        NumbersServiceGrpc.newStub(channel)
                .generateSequence(
                        SequenceRequest.newBuilder()
                                .setFirstValue(0)
                                .setLastValue(30)
                                .build(),
                        streamObserver);

        long currentValue = 0;
        long oldCurrentValue = 0;
        long lastValue = 0;
        for (int i = 0; i < 50; i++) {
            lastValue = streamObserver.takeLastValue();
            oldCurrentValue = currentValue;
            currentValue = currentValue + lastValue + 1;
            log.info("currentValue:{} [{} + {} + 1]", currentValue, oldCurrentValue, lastValue);
            Thread.sleep(1000);
        }

        channel.shutdownNow();
        channel.awaitTermination(5, TimeUnit.SECONDS);
        log.info("numbers Client stopped");
    }
}
