package fr.joupi.games.engine.economy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;

@Getter
@Setter
@AllArgsConstructor
public abstract class GameCurrency<N extends Number> implements GameEconomy<N> {

    protected final String name;
    protected final String symbol;

    protected N wallet;

    @Override
    public String getWalletFormated() {
        return wallet + " " + symbol;
    }

    @Override
    public String getWalletFormated(DecimalFormat decimalFormat) {
        return decimalFormat.format(wallet) + " " + symbol;
    }

}