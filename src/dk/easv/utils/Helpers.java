package dk.easv.utils;

import javafx.util.Pair;

import static dk.easv.presentation.controller.MainWindowController.ROWS_IN_GRID;

public class Helpers {

    public static Pair<Integer, Integer> convertIndexToColRow(int i, int startIndex) {
        int col = ((i - startIndex) % ROWS_IN_GRID);
        int row = ((i - startIndex) / ROWS_IN_GRID);

        return new Pair<>(col, row);
    }

}
