package org.aburavov.otus.java.professional.hw15;

public class Printer {
    private final int rangeFrom;
    private final int rangeTo;
    private Direction direction = Direction.Forward;
    private int counter;

    public Printer(int rangeFrom, int rangeTo) {
        if (rangeFrom >= rangeTo) {
            throw new IllegalArgumentException("rangeFrom must be less than rangeTo");
        }
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
        this.counter = rangeFrom-1;
    }

    public void action() {
        next();
        System.out.println(Thread.currentThread().getName()+": "+counter);
    }

    private void next() {
        if (direction == Direction.Forward) {
            if (counter == rangeTo) {
                direction = Direction.Backward;
                next();
                return;
            }
            counter++;
        } else {
            if (counter == rangeFrom) {
                direction = Direction.Forward;
                next();
                return;
            }
            counter--;
        }
    }
}
