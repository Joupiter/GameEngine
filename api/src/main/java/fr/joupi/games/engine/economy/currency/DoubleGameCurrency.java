package fr.joupi.games.engine.economy.currency;

import fr.joupi.games.engine.economy.GameCurrency;

public class DoubleGameCurrency extends GameCurrency<Double> {

    public DoubleGameCurrency(String name, String symbol, Double wallet) {
        super(name, symbol, wallet);
    }

    @Override
    public void add(Double amount) {
        this.wallet += amount;
    }

    @Override
    public void remove(Double amount) {
        this.wallet = Math.max(wallet - amount, 0);
    }

    @Override
    public boolean has(Double amount) {
        return wallet >= amount;
    }

}