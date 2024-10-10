package clock;

import alarm.AlarmManager;
import alarm.AlarmType;
import controlAndObserve.Observable;
import controlAndObserve.Observer;
import counter.Counter24;
import counter.Counter60;
import counter.Counter7;
import counter.SettableCounter;
import time.Time;
import time.TimeType;

import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class WeekAlarmClock implements AlarmClockType, Observable {

    private List<Observer> observers;

    // Alarm manager
    private AlarmManager manager;

    // Counters
    private SettableCounter day;
    private SettableCounter hour;
    private SettableCounter min;
    private SettableCounter sec;

    public WeekAlarmClock() {
        observers = new LinkedList<>();

        manager = new AlarmManager();

        day = new Counter7();
        hour = new Counter24(day);
        min = new Counter60(hour);
        sec = new Counter60(min);

        setCurrentTime();
    }

    public void tickTack() {
        sec.count();
        manager.checkForAlarm(getTime());
        updateObservers();
    }

    public void setTime(TimeType time) {
        sec.setCount(time.getSecond());
        min.setCount(time.getMinute());
        hour.setCount(time.getHour());
        day.setCount(time.getDay());

        updateObservers();
    }

    public void setCurrentTime() {
        LocalDateTime now = LocalDateTime.now();

        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int day = dayOfWeek.getValue() -1;

        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();

        TimeType currentTime = new Time(day, hour, minute, second);

        setTime(currentTime);
    }

    public void addAlarm(AlarmType alarm) {
        manager.addAlarm(alarm);
    }

    public void removeAlarm(AlarmType alarm) {
        manager.removeAlarm(alarm);
    }

    public void removeAllAlarms() {
        manager.removeAllAlarms();
    }

    public Collection<AlarmType> getAlarms() {
        return manager.getAlarms();
    }

    public TimeType getTime() {
        int second = sec.getCount();
        int minute = min.getCount();
        int hourly = hour.getCount();
        int daily = day.getCount();

        return new Time(daily, hourly, minute, second);
    }

    @Override
    public String toString() {
        return getTime().toString();
    }

    @Override
    public void updateObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    @Override
    public void remove(Observer o) {
        observers.remove(o);
    }
}
