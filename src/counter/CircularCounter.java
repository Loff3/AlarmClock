package counter;

import org.w3c.dom.css.Counter;

public abstract class CircularCounter implements CounterType {
    public enum Direction {
        INCREASING, DECREASING
    };

    protected int currentCount;
    private boolean isPaused;
    protected final int MAX_NR_OF_COUNTS;
    public Direction direction;
    private CounterType nextCounter;

    //Konstructor:
    public CircularCounter(int maxNROfCounts, Direction direction, CounterType nextCounter) {
        this.direction = direction;
        this.nextCounter = nextCounter;

        //Hantering av för lågt värde
        if (maxNROfCounts < 2) {
            this.MAX_NR_OF_COUNTS = 0;
        } else {
            this.MAX_NR_OF_COUNTS = maxNROfCounts;
        }

        //Om nedåträknare räkna baklänges
        if (this.direction == Direction.DECREASING && this.MAX_NR_OF_COUNTS > 0) {
            currentCount = MAX_NR_OF_COUNTS - 1;
        }

    }

    @Override
    public int getCount() {
        return currentCount;

    }

    @Override
    public void reset() {
        if (direction == Direction.DECREASING) {
            currentCount = MAX_NR_OF_COUNTS - 1;
        }else {
            currentCount = 0;
        }
    }

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = false;
    }

    @Override
    public void count() {
        if(!isPaused && this.MAX_NR_OF_COUNTS > 0) {
            if (direction == Direction.INCREASING) {
                currentCount ++;
                //Om räknat ett helt varv:
                if (currentCount >= MAX_NR_OF_COUNTS) {
                    currentCount = 0;

                    if (nextCounter != null) {
                        nextCounter.count();
                    }
                }
            else if (direction == Direction.DECREASING) {
                    currentCount --;
                    if (currentCount < 0) {
                        currentCount = MAX_NR_OF_COUNTS - 1;
                        if (nextCounter != null) {
                            nextCounter.count();
                        }
                    }
                }

            }
        }
    }

    @Override
    public String toString() {
        return "Current count: " + currentCount;
    }

}
