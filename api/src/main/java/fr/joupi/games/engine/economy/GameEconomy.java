package fr.joupi.games.engine.economy;

import fr.joupi.games.engine.utils.Utils;

import java.text.DecimalFormat;
import java.util.function.Function;

public interface GameEconomy<N extends Number> {

    String getName();
    String getSymbol();

    N getWallet();

    void add(N amount);
    void remove(N amount);

    void setWallet(N amount);

    boolean has(N amount);

    String getWalletFormated();
    String getWalletFormated(DecimalFormat decimalFormat);

    default void reset() {
        remove(getWallet());
    }

    default Function<Number, String> formatWallet() {
        return value -> {
            String[] arr = {"", "K", "M", "B", "T", "P", "E"};
            double doubleValue = value.doubleValue();
            int index = 0;

            while (Math.abs(doubleValue) >= 1000 && index < arr.length - 1) {
                doubleValue /= 1000;
                index++;
            }

            return Utils.decimalFormat.format(doubleValue) + arr[index];
        };
    }

}