package dk.easv.presentation.controller;

import dk.easv.presentation.model.AppModel;
import dk.easv.movieviewstrategy.MovieViewStrategy;
import dk.easv.movieviewstrategy.TopMovieNotSeenStrategy;
import dk.easv.movieviewstrategy.TopMovieSeenStrategy;
import dk.easv.movieviewstrategy.TopMoviesSimilarUsersStrategy;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private MovieViewStrategy viewStrategy;

    public static final int ROWS_IN_GRID = 4;
    public static final int COLUMNS_IN_GRID = 4;
    public static final int CELLS_IN_GRID = ROWS_IN_GRID * COLUMNS_IN_GRID;

    private AppModel model;

    @FXML
    public Label pageLabel;

    @FXML
    public GridPane movieGridPane;

    @FXML
    public TextField searchTxtField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PauseTransition searchDelay = new PauseTransition(Duration.millis(250));

        searchTxtField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            searchDelay.setOnFinished(event -> System.out.println("Searching: " + newValue));

            searchDelay.playFromStart();
        });
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
        model.calculateAndSetTotalPagesProperty(model.getObsTopMovieSeen().size(), CELLS_IN_GRID);
        viewStrategy = new TopMovieSeenStrategy(movieGridPane, model.getObsTopMovieSeen());

        refreshView();

    }

    public void handleShowTopMovieNotSeen() {
        model.calculateAndSetTotalPagesProperty(model.getObsTopMovieNotSeen().size(), CELLS_IN_GRID);
        viewStrategy = new TopMovieNotSeenStrategy(movieGridPane, model.getObsTopMovieNotSeen());

        refreshView();
    }

    public void handleShowTopMoviesSimilarUsers() {
        model.calculateAndSetTotalPagesProperty(model.getObsTopMoviesSimilarUsers().size(), CELLS_IN_GRID);
        viewStrategy = new TopMoviesSimilarUsersStrategy(movieGridPane, model.getObsTopMoviesSimilarUsers());

        refreshView();
    }

    private void refreshView() {
        viewStrategy.drawPage(model.getCurrentPage());
    }
}
