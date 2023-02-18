package dk.easv.presentation.controller;

import dk.easv.entities.Movie;
import dk.easv.entities.TopMovie;
import dk.easv.presentation.model.AppModel;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private ObservableList<Movie> currentView = null;

    private static final int ROWS_IN_GRID = 4;
    private static final int COLUMNS_IN_GRID = 4;
    private static final int CELLS_IN_GRID = ROWS_IN_GRID * COLUMNS_IN_GRID;

    private AppModel model;

    @FXML
    public Label pageLabel;

    @FXML
    public GridPane movieGridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setModel(AppModel model) {
        this.model = model;

        pageLabel.textProperty().bind(Bindings.concat("page ", model.currentPageProperty(), " / ", model.totalPagesProperty()));

        model.loadUsers();
        model.loadData(model.getObsLoggedInUser());

        handleShowTopMovieSeen();
    }

    public void handlePreviousPage() {
        if(model.getCurrentPage() == 0) return;
        model.currentPageProperty().set(model.getCurrentPage() - 1);
        refreshView();
    }

    public void handlePreviousPageMax() {
        if(model.getCurrentPage() == 0) return;
        model.currentPageProperty().set(0);
        refreshView();
    }

    public void handleNextPage() {
        if(model.getCurrentPage() == model.getTotalPages()) return;
        model.currentPageProperty().set(model.getCurrentPage() + 1);
        refreshView();
    }

    public void handleNextPageMax() {
        if(model.getCurrentPage() == model.getTotalPages()) return;
        model.currentPageProperty().set(model.getTotalPages());
        refreshView();
    }


    public void handleShowTopMovieSeen() {
        currentView = model.getObsTopMovieSeen();
        model.calculateAndSetTotalPagesProperty(currentView.size(), CELLS_IN_GRID);

        refreshView();
    }

    public void handleShowTopMovieNotSeen() {
        currentView = model.getObsTopMovieNotSeen();
        model.calculateAndSetTotalPagesProperty(currentView.size(),  CELLS_IN_GRID);

        refreshView();
    }

    public void handleShowTopMoviesSimilarUsers() { // TODO: This should probably be changed
        ObservableList<Movie> movies = FXCollections.observableArrayList();

        for(TopMovie tm : model.getObsTopMoviesSimilarUsers()) {
            movies.add(tm.getMovie());
        }

        currentView = movies;
        model.calculateAndSetTotalPagesProperty(currentView.size(),  CELLS_IN_GRID);

        refreshView();
    }

    private void refreshView() {
        movieGridPane.getChildren().clear();
        int startIndex = (model.getCurrentPage()) * CELLS_IN_GRID;
        for(int i = startIndex; (i < currentView.size() && i < startIndex + CELLS_IN_GRID); i++) {
            int col = ((i - startIndex) % ROWS_IN_GRID);
            int row = ((i - startIndex) / ROWS_IN_GRID);

            var movie = currentView.get(i);

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

            movieGridPane.add(bp, col, row);
        }
    }
}
