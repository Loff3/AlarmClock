package alarm;
import time.TimeType;

public interface AlarmType
  {
  public void setActive(boolean state);
  public boolean isActive();
  public TimeType getTime();
  public void doAlarm();
  }
