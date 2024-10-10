package alarm;

import time.TimeType;

import javax.swing.*;

public class Alarm implements AlarmType
  {
  private boolean active;
  private TimeType time;
  
  public Alarm(TimeType time)
    {
    this.time = time;
    active = true;
    }
  
  @Override
  public void setActive(boolean state)
    {
    this.active = state;
    }


  @Override
  public boolean isActive()
    {
    return active;
    }

  @Override
  public TimeType getTime()
    {
    return time;
    }

  @Override
  public void doAlarm()
    {
    if(active)
      System.out.println("!ALARM ALARM!\n" + time.toString());
      SwingUtilities.invokeLater(() -> {
        JOptionPane.showMessageDialog(null, "Alarm! " + time.toString(), "Alarm",
                JOptionPane.INFORMATION_MESSAGE);
      });
    }
  
  public String toString()
    {
    return time.toString();
    }
  }
