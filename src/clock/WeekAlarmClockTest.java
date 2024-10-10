package clock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import alarm.Alarm;
import alarm.AlarmType;
import time.Time;
import time.TimeType;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class WeekAlarmClockTest {
    private WeekAlarmClock clock;

    @BeforeEach
    void setUp() {
        clock = new WeekAlarmClock();
    }

    @Test
    void tickTack() {
        // söndag 23:59:59
        TimeType time = new Time(6, 23, 59, 59); // Söndag
        clock.setTime(time);

        clock.tickTack();

        // Förväntad tid: Måndag 00:00:00
        TimeType expectedTime = new Time(0, 0, 0, 0); // Måndag

        assertEquals(expectedTime.toString(), clock.getTime().toString(), "Time should roll over to next day");
    }

    @Test
    void setTime() {
        TimeType time = new Time(3, 12, 30, 45); // Onsdag 12:30:45
        clock.setTime(time);

        // tiden ska vara korrekt inställd
        assertEquals(time.toString(), clock.getTime().toString(), "Time should be set correctly");
    }

    @Test
    void addAlarm() {

        TimeType alarmTime = new Time(1, 8, 0, 0); // Tisdag 08:00:00
        AlarmType alarm = new Alarm(alarmTime);

        clock.addAlarm(alarm);

        // Kontrollera att alarmet finns
        Collection<AlarmType> alarms = clock.getAlarms();
        assertTrue(alarms.contains(alarm), "Alarm should be added to the clock");
    }

    @Test
    void removeAlarm() {

        TimeType alarmTime = new Time(2, 9, 30, 0); // Onsdag 09:30:00
        AlarmType alarm = new Alarm(alarmTime);
        clock.addAlarm(alarm);


        clock.removeAlarm(alarm);

        // Kontrollera att alarmet inte längre finns sparat
        Collection<AlarmType> alarms = clock.getAlarms();
        assertFalse(alarms.contains(alarm));
    }

    @Test
    void removeAllAlarms() {

        AlarmType alarm1 = new Alarm(new Time(0, 7, 0, 0)); // Måndag 07:00:00
        AlarmType alarm2 = new Alarm(new Time(1, 8, 0, 0)); // Tisdag 08:00:00
        clock.addAlarm(alarm1);
        clock.addAlarm(alarm2);


        clock.removeAllAlarms();

        // inga alarm ska finnas kvar
        Collection<AlarmType> alarms = clock.getAlarms();
        assertTrue(alarms.isEmpty());
    }

    @Test
    void getAlarms() {

        AlarmType alarm = new Alarm(new Time(4, 12, 0, 0)); // Fredag 12:00:00
        clock.addAlarm(alarm);


        Collection<AlarmType> alarms = clock.getAlarms();


        assertTrue(alarms.contains(alarm));
        assertEquals(1, alarms.size());
    }

    @Test
    void getTime() {

        TimeType time = new Time(5, 15, 45, 30); // Lördag 15:45:30
        clock.setTime(time);

        // Hämta tiden från klockan
        TimeType currentTime = clock.getTime();

        // Kontrollera att tiden stämmer
        assertEquals(time.toString(), currentTime.toString());
    }

    @Test
    void testToString() {

        TimeType time = new Time(2, 10, 20, 30); // Onsdag 10:20:30
        clock.setTime(time);

        // Kontrollera  toString()
        assertEquals(time.toString(), clock.toString());
    }

    @Test
    void testAlarmTriggering() {

        TimeType alarmTime = new Time(0, 0, 0, 5); // Måndag 00:00:05
        AlarmType alarm = new Alarm(alarmTime);
        clock.addAlarm(alarm);


        clock.setTime(new Time(0, 0, 0, 0));


        for (int i = 0; i < 5; i++) {
            clock.tickTack();
        }

        assertTrue(alarm.isActive());
        // Testar om alarmet raderas efter att ha utlöst det
        assertEquals(0, clock.getAlarms().size());
    }
}
