package org.aburavov.otus.java.professional.hw15;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static int currentTurn = 1;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("HW15");

        List<Thread> threads = new ArrayList<>();

        Object lock = new Object();

        for (int i = 1; i <= 2; i++) {
            Thread thread = getThread(i, lock);
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        Thread.sleep(100);

        System.out.println("Interrupting threads");

        for (Thread thread : threads) {
            thread.interrupt();
        }

        System.out.println("Finish");
    }

    private static Thread getThread(int i, Object lock) {
        Printer printer = new Printer(1, 10);
        Runnable task = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    synchronized (lock) {
                        while (currentTurn != i) {
                            lock.wait();
                        }
                        printer.action();
                        currentTurn = (i == 1) ? 2 : 1;
                        lock.notifyAll();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        };
        Thread thread = new Thread(task);
        thread.setName("Thread-" + i);
        return thread;
    }
}
