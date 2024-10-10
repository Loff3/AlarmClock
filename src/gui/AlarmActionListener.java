package gui;

import alarm.AlarmType;

public interface AlarmActionListener {
    void onToggleAlarm(AlarmType alarm);
    void onRemoveAlarm(AlarmType alarm);
}
