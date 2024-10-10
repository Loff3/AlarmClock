package controlAndObserve;

public interface Observable {
    void updateObservers();
    void register(Observer o);
    void remove(Observer o);
}
