package cn.gomro.mid.example.stateful;

import javax.ejb.Remote;
import javax.ejb.Stateful;

/**
 * Created by momo on 2016/5/10.
 */
@Stateful
@Remote(ICounter.class)
public class CounterBean implements ICounter {

    private int count = 0;

    @Override
    public void increment() {
        count++;
    }

    @Override
    public void decrement() {
        count--;
    }

    @Override
    public int getCount() {
        return count;
    }
}
