package dk.easv.movieviewstrategy;

import dk.easv.entities.Movie;
import dk.easv.utils.Helpers;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;

import static dk.easv.presentation.controller.MainWindowController.CELLS_IN_GRID;

public class TopMovieSeenStrategy implements  MovieViewStrategy {

    private final GridPane pane;

    private final ObservableList<Movie> movies;

    public TopMovieSeenStrategy(GridPane pane, ObservableList<Movie> movies) {
        this.pane = pane;
        this.movies = movies;
    }


    @Override
    public void drawPage(int page) {
        pane.getChildren().clear();
        final int startIndex = (page) * CELLS_IN_GRID;
        for(int i = startIndex; (i < movies.size() && i < startIndex + CELLS_IN_GRID); i++) {
            var colRow = Helpers.convertIndexToColRow(i, startIndex);

            var movie = movies.get(i);

            BorderPane bp = new BorderPane();
            bp.getStyleClass().add("moviePane");

            // Add image
            Image img = new Image("file:data/ComingToAmerica.jpg");
            ImageView imgView = new ImageView(img);

            imgView.setFitWidth(img.getWidth() / 3);
            imgView.setFitHeight(img.getHeight() / 3);
            bp.setCenter(imgView);

            // Add text
            DecimalFormat df = new DecimalFormat("#.#");

            VBox vbox = new VBox();

            var title = new Label(movie.getTitle());
            title.setWrapText(true);

            vbox.getChildren().add(title);
            vbox.getChildren().add(new Label(movie.getYear() + ""));
            vbox.getChildren().add(new Label(df.format(movie.getAverageRating())));
            bp.setRight(vbox);

            pane.add(bp, colRow.getKey(), colRow.getValue());
        }
    }
}
