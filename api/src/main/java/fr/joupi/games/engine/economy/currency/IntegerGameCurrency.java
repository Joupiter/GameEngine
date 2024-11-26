package fr.joupi.games.engine.economy.currency;

import fr.joupi.games.engine.economy.GameCurrency;

public class IntegerGameCurrency extends GameCurrency<Integer> {

    public IntegerGameCurrency(String name, String symbol, Integer wallet) {
        super(name, symbol, wallet);
    }

    @Override
    public void add(Integer amount) {
        this.wallet += amount;
    }

    @Override
    public void remove(Integer amount) {
        this.wallet = Math.max(wallet - amount, 0);
    }

    @Override
    public boolean has(Integer amount) {
        return wallet >= amount;
    }

}