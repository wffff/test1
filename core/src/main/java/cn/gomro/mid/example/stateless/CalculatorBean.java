package cn.gomro.mid.example.stateless;

import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Created by momo on 2016/5/10.
 */
@Stateless
@Remote(ICalculator.class)
public class CalculatorBean implements ICalculator {

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int subtract(int a, int b) {
        return a - b;
    }
}
