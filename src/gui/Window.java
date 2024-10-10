package gui;

import clock.WeekAlarmClock;
import controlAndObserve.Controller;

import javax.swing.*;

public class Window extends JFrame {

    private JTabbedPane tabPanel;
    private WeekAlarmClock weekAlarmClock;
    private AnalogClockPanel analogClockPanel;
    private DigitalClockPanel digitalClockPanel;
    private AlarmPanel alarmPanel;
    private SetTimePanel setTimePanel;



    public Window() {
        super("Clock");

        tabPanel = new JTabbedPane();
        add(tabPanel);

        analogClockPanel = new AnalogClockPanel();
        tabPanel.add("AnalogClock", analogClockPanel);

        digitalClockPanel = new DigitalClockPanel();
        tabPanel.add("DigitalClock", digitalClockPanel);

        alarmPanel = new AlarmPanel();
        tabPanel.add("Alarm", alarmPanel);

        setTimePanel = new SetTimePanel();
        tabPanel.add("SetTime", setTimePanel);

        weekAlarmClock = new WeekAlarmClock();
        new Controller(weekAlarmClock, analogClockPanel, digitalClockPanel, alarmPanel, setTimePanel);


        setSize(800,600);
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
