package model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Figures, Integer> resultCart;

    public Cart() {
        resultCart = new HashMap<>();
        for (int i = 0; i < Figures.values().length; i ++) {
            resultCart.put(Figures.values()[i], null);
        }
    }

    public Map<Figures, Integer> getResultCart() {
        return resultCart;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Tabela wyników: ");
        for (int i = 0; i < Figures.values().length; i++) {
            Figures figure;
            figure = Figures.values()[i];
            out.append("[").append(figure.getMyName()).append(": ").append(resultCart.get(figure)).append("] ");
        }
        return out.toString();
    }

    public void addResult(Figures figure, Integer result) {
        resultCart.put(figure, result);
    }
}
