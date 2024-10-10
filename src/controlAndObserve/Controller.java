package controlAndObserve;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import alarm.Alarm;
import alarm.AlarmType;
import clock.WeekAlarmClock;
import gui.AlarmActionListener;
import gui.DigitalClockPanel;
import gui.SetTimePanel;
import time.Time;
import time.TimeType;
import gui.AlarmPanel;
import gui.AnalogClockPanel;

public class Controller implements Observer, AlarmActionListener {

    private WeekAlarmClock clock;
    private AnalogClockPanel analogClock;
    private DigitalClockPanel digitalClockPanel;
    private AlarmPanel alarmPanel;
    private SetTimePanel setTimePanel;

    public Controller(WeekAlarmClock clock, AnalogClockPanel analog, DigitalClockPanel digital, AlarmPanel alarm,
                      SetTimePanel setTime) {
        this.clock = clock;
        this.analogClock = analog;
        this.digitalClockPanel = digital;
        this.alarmPanel = alarm;
        this.setTimePanel = setTime;

        this.clock.register(this);

        Timer timer = new Timer(1000, e -> this.clock.tickTack());
        timer.start();

        alarmPanel.setAlarmActionListener(this);

        alarmPanel.addAlarmListener(new AddAlarmListener());
        alarmPanel.removeAllAlarmsListener(new RemoveAllAlarmsListener());

        setTimePanel.addSetTimeListener(new SetTimeListener());
    }

    @Override
    public void onToggleAlarm(AlarmType alarm) {
        alarm.setActive(!alarm.isActive());
        alarmPanel.updateAlarmVisualStatus(alarm);
    }

    @Override
    public void onRemoveAlarm(AlarmType alarm) {
        clock.removeAlarm(alarm);
        alarmPanel.removeAlarmRow(alarm);
    }

    class AddAlarmListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int dayIndex = alarmPanel.getSelectedDay();
            int hour = alarmPanel.getSelectedHour();
            int minute = alarmPanel.getSelectedMinute();
            int second = alarmPanel.getSelectedSecond();

            TimeType alarmTime = new Time(dayIndex, hour, minute, second);
            AlarmType newAlarm = new Alarm(alarmTime);

            clock.addAlarm(newAlarm);
            alarmPanel.addAlarmRow(newAlarm);
        }
    }

    class RemoveAllAlarmsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clock.removeAllAlarms();
            alarmPanel.clearAlarms();
        }
    }

    class SetTimeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int dayIndex = setTimePanel.getSelectedDay();
            int hour = setTimePanel.getSelectedHour();
            int minute = setTimePanel.getSelectedMinute();
            int second = setTimePanel.getSelectedSecond();

            TimeType newTime = new Time(dayIndex, hour, minute, second);
            clock.setTime(newTime);

            setTimePanel.displayTimeSetConfirmation(newTime);
        }
    }

    private void checkAlarms(TimeType currentTime) {
        for (AlarmType alarm : clock.getAlarms()) {
            if (alarm.isActive() && alarm.getTime().equals(currentTime)) {
                alarm.setActive(false);
                alarmPanel.displayAlarmNotification(currentTime);
                alarmPanel.updateAlarmVisualStatus(alarm);
            }
        }
    }

    @Override
    public void update() {
        TimeType currentTime = clock.getTime();
        analogClock.updateTime(currentTime);
        digitalClockPanel.updateTime(currentTime);

        checkAlarms(currentTime);
    }
}
