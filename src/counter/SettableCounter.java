package counter;

public class SettableCounter extends CircularCounter implements SettableCounterType {

    public SettableCounter(int maxNROfCounts, Direction direction, CounterType nextCounter) {
        super(maxNROfCounts, direction, nextCounter);
    }

    @Override
    public void setCount(int value) {
        if(value >= 0 && value < MAX_NR_OF_COUNTS) {
            this.currentCount = value;
        }
    }
}
