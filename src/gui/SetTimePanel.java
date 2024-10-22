package gui;

import time.TimeType;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SetTimePanel extends JPanel {

    private JComboBox<String> dayComboBox;
    private JComboBox<Integer> hourComboBox;
    private JComboBox<Integer> minuteComboBox;
    private JComboBox<Integer> secondComboBox;
    private JButton setTimeButton;

    public SetTimePanel() {

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

        setTimeButton = new JButton("Set Time");

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setPreferredSize(new Dimension(400, 50));
        inputPanel.setBackground(Color.gray);

        inputPanel.add(dayComboBox);
        inputPanel.add(new JLabel(":"));
        inputPanel.add(hourComboBox);
        inputPanel.add(new JLabel(":"));
        inputPanel.add(minuteComboBox);
        inputPanel.add(new JLabel(":"));
        inputPanel.add(secondComboBox);
        inputPanel.add(setTimeButton);

        add(inputPanel, BorderLayout.NORTH);
    }

    public void displayTimeSetConfirmation(TimeType newTime) {
        JOptionPane.showMessageDialog(this, "Time has been set to: " + newTime.toString(), "Set Time",
                JOptionPane.INFORMATION_MESSAGE);
    }


    public void addSetTimeListener(ActionListener listener) {
        setTimeButton.addActionListener(listener);
    }

    public int getSelectedDay() {
        return dayComboBox.getSelectedIndex();
    }

    public int getSelectedHour() {
        return (int) hourComboBox.getSelectedItem();
    }

    public int getSelectedMinute() {
        return (int) minuteComboBox.getSelectedItem();
    }

    public int getSelectedSecond() {
        return (int) secondComboBox.getSelectedItem();
    }
}
