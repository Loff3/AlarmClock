package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import alarm.AlarmType;
import time.TimeType;

public class AlarmPanel extends JPanel {

    private JComboBox<String> dayComboBox;
    private JComboBox<Integer> hourComboBox;
    private JComboBox<Integer> minuteComboBox;
    private JComboBox<Integer> secondComboBox;
    private JButton addAlarmButton;
    private JButton removeAllAlarmsButton;
    private JPanel alarmListPanel;
    private ArrayList<AlarmRow> alarmRows;
    private AlarmActionListener alarmActionListener;

    public AlarmPanel() {

        String[] days = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };
        dayComboBox = new JComboBox<>(days);

        Integer[] hours = new Integer[24];
        for (int i = 0; i < 24; i++) {
            hours[i] = i;
        }
        hourComboBox = new JComboBox<>(hours);

        Integer[] minutesSeconds = new Integer[60];
        for (int i = 0; i < 60; i++) {
            minutesSeconds[i] = i;
        }
        minuteComboBox = new JComboBox<>(minutesSeconds);
        secondComboBox = new JComboBox<>(minutesSeconds);

        addAlarmButton = new JButton("Set Alarm");
        removeAllAlarmsButton = new JButton("Remove All Alarms");

        alarmRows = new ArrayList<>();

        alarmListPanel = new JPanel();
        alarmListPanel.setLayout(new BoxLayout(alarmListPanel, BoxLayout.Y_AXIS));

        JScrollPane alarmListScrollPane = new JScrollPane(alarmListPanel);
        alarmListScrollPane.setPreferredSize(new Dimension(600, 300));

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setPreferredSize(new Dimension(600, 50));
        inputPanel.setBackground(Color.gray);

        inputPanel.add(new JLabel("Day:"));
        inputPanel.add(dayComboBox);
        inputPanel.add(new JLabel(" Time: "));
        inputPanel.add(hourComboBox);
        inputPanel.add(new JLabel(":"));
        inputPanel.add(minuteComboBox);
        inputPanel.add(new JLabel(":"));
        inputPanel.add(secondComboBox);
        inputPanel.add(addAlarmButton);

        JPanel managePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        managePanel.add(removeAllAlarmsButton);

        add(inputPanel, BorderLayout.NORTH);
        add(alarmListScrollPane, BorderLayout.CENTER);
        add(managePanel, BorderLayout.SOUTH);
    }

    public void setAlarmActionListener(AlarmActionListener listener) {
        this.alarmActionListener = listener;
    }

    public void addAlarmRow(AlarmType alarm) {
        AlarmRow row = new AlarmRow(alarm);
        alarmRows.add(row);
        alarmListPanel.add(row);
        alarmListPanel.revalidate();
        alarmListPanel.repaint();
    }

    public void clearAlarms() {
        alarmListPanel.removeAll();
        alarmRows.clear();
        alarmListPanel.revalidate();
        alarmListPanel.repaint();
    }

    public void addAlarmListener(ActionListener listener) {
        addAlarmButton.addActionListener(listener);
    }

    public void removeAllAlarmsListener(ActionListener listener) {
        removeAllAlarmsButton.addActionListener(listener);
    }

    public int getSelectedDay() {
        return dayComboBox.getSelectedIndex();
    }

    public int getSelectedHour() {
        return (Integer) hourComboBox.getSelectedItem();
    }

    public int getSelectedMinute() {
        return (Integer) minuteComboBox.getSelectedItem();
    }

    public int getSelectedSecond() {
        return (Integer) secondComboBox.getSelectedItem();
    }

    public void updateAlarmVisualStatus(AlarmType alarm) {
        for (AlarmRow row : alarmRows) {
            if (row.getAlarm().equals(alarm)) {
                row.updateStatus();
                break;
            }
        }
    }

    public void removeAlarmRow(AlarmType alarm) {
        AlarmRow rowToRemove = null;
        for (AlarmRow row : alarmRows) {
            if (row.getAlarm().equals(alarm)) {
                rowToRemove = row;
                break;
            }
        }
        if (rowToRemove != null) {
            alarmListPanel.remove(rowToRemove);
            alarmRows.remove(rowToRemove);
            alarmListPanel.revalidate();
            alarmListPanel.repaint();
        }
    }

    public void displayAlarmNotification(TimeType alarmTime) {
        JOptionPane.showMessageDialog(this, "Alarm! The time is: " + alarmTime.toString(), "Alarm",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private class AlarmRow extends JPanel {
        private AlarmType alarm;
        private JLabel alarmLabel;
        private JButton activateButton;
        private JButton removeButton;

        public AlarmRow(AlarmType alarm) {
            this.alarm = alarm;
            setLayout(new GridLayout(1, 3, 10, 0));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            setBackground(Color.black);

            alarmLabel = new JLabel(alarm.getTime().toString());
            alarmLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(alarmLabel);

            activateButton = new JButton(alarm.isActive() ? "Deactivate" : "Activate");
            activateButton.setBackground(alarm.isActive() ? Color.green.darker().darker() : Color.RED.darker().darker());
            activateButton.setOpaque(true);
            activateButton.setBorderPainted(false);
            activateButton.addActionListener(e -> {
                if (alarmActionListener != null) {
                    alarmActionListener.onToggleAlarm(alarm);
                }
            });
            add(activateButton);

            removeButton = new JButton("Remove");
            removeButton.setOpaque(true);
            removeButton.setBorderPainted(false);
            removeButton.addActionListener(e -> {
                if (alarmActionListener != null) {
                    alarmActionListener.onRemoveAlarm(alarm);
                }
            });
            add(removeButton);
        }

        public AlarmType getAlarm() {
            return alarm;
        }

        public void updateStatus() {
            activateButton.setText(alarm.isActive() ? "Deactivate" : "Activate");
            activateButton.setBackground(alarm.isActive() ? Color.green.darker().darker() : Color.RED.darker().darker());
        }
    }
}
