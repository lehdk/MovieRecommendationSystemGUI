package dk.easv.utils;

import javafx.util.Pair;

import static dk.easv.presentation.controller.MainWindowController.ROWS_IN_GRID;

public class Helpers {

    /**
     * This calculates a row and column for a given index.
     * @param index The index
     * @param startIndex The start index of the draw loop (page offset).
     * @return A Pair with Column as key and Row as value.
     */
    public static Pair<Integer, Integer> convertIndexToColRow(int index, int startIndex) {
        int col = ((index - startIndex) % ROWS_IN_GRID);
        int row = ((index - startIndex) / ROWS_IN_GRID);

        return new Pair<>(col, row);
    }

}
