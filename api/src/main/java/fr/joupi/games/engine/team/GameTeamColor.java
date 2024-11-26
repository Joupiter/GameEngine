package fr.joupi.games.engine.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum GameTeamColor {

    RED ("Rouge", ChatColor.RED, DyeColor.RED),
    BLUE ("Bleu", ChatColor.BLUE, DyeColor.BLUE),
    GREEN ("Vert", ChatColor.GREEN, DyeColor.LIME),
    YELLOW ("Jaune", ChatColor.YELLOW, DyeColor.YELLOW),
    WHITE ("Blanc", ChatColor.WHITE, DyeColor.WHITE),
    GRAY ("Gris", ChatColor.GRAY, DyeColor.GRAY),
    ORANGE ("Orange", ChatColor.GOLD, DyeColor.ORANGE),
    PURPLE ("Violet", ChatColor.DARK_PURPLE, DyeColor.PURPLE),
    PINK ("Rose", ChatColor.LIGHT_PURPLE, DyeColor.PINK),
    CYAN ("Cyan", ChatColor.AQUA, DyeColor.CYAN);

    private final String name;
    private final ChatColor chatColor;
    private final DyeColor dyeColor;

    public final static EnumSet<GameTeamColor> colors = EnumSet.allOf(GameTeamColor.class);

    public static List<GameTeamColor> getColors(int size) {
        return getColorsAsStream(size).toList();
    }

    public static Stream<GameTeamColor> getColorsAsStream() {
        return colors.stream();
    }

    public static Stream<GameTeamColor> getColorsAsStream(int size) {
        return colors.stream().limit(size);
    }

}