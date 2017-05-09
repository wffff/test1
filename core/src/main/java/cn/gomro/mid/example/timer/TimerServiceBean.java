package cn.gomro.mid.example.timer;

import javax.ejb.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Created by momo on 16/4/1.
 */
public class TimerServiceBean implements TimerService {
    @Override
    public Timer createCalendarTimer(ScheduleExpression scheduleExpression) throws IllegalArgumentException, IllegalStateException, EJBException {
        return null;
    }

    @Override
    public Timer createCalendarTimer(ScheduleExpression scheduleExpression, TimerConfig timerConfig) throws IllegalArgumentException, IllegalStateException, EJBException {
        return null;
    }

    @Override
    public Timer createIntervalTimer(Date date, long l, TimerConfig timerConfig) throws IllegalArgumentException, IllegalStateException, EJBException {
        return null;
    }

    @Override
    public Timer createIntervalTimer(long l, long l1, TimerConfig timerConfig) throws IllegalArgumentException, IllegalStateException, EJBException {
        return null;
    }

    @Override
    public Timer createSingleActionTimer(Date date, TimerConfig timerConfig) throws IllegalArgumentException, IllegalStateException, EJBException {
        return null;
    }

    @Override
    public Timer createSingleActionTimer(long l, TimerConfig timerConfig) throws IllegalArgumentException, IllegalStateException, EJBException {
        return null;
    }

    @Override
    public Timer createTimer(long l, Serializable serializable) throws IllegalArgumentException, IllegalStateException, EJBException {
        return null;
    }

    @Override
    public Timer createTimer(long l, long l1, Serializable serializable) throws IllegalArgumentException, IllegalStateException, EJBException {
        return null;
    }

    @Override
    public Timer createTimer(Date date, Serializable serializable) throws IllegalArgumentException, IllegalStateException, EJBException {
        return null;
    }

    @Override
    public Timer createTimer(Date date, long l, Serializable serializable) throws IllegalArgumentException, IllegalStateException, EJBException {
        return null;
    }

    @Override
    public Collection<Timer> getTimers() throws IllegalStateException, EJBException {
        return null;
    }

    @Override
    public Collection<Timer> getAllTimers() throws IllegalStateException, EJBException {
        return null;
    }
}
