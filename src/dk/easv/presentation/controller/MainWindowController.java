package dk.easv.presentation.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private IntegerProperty currentPage = new SimpleIntegerProperty(0);
    private IntegerProperty totalPages = new SimpleIntegerProperty(10);

    @FXML
    public Label pageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageLabel.textProperty().bind(Bindings.concat("page ", currentPage, " / ", totalPages));

    }

    public void handlePreviousPage() {
        if(currentPage.get() == 0) return;
        currentPage.set(currentPage.get() - 1);
    }

    public void handlePreviousPageMax() {
        if(currentPage.get() == 0) return;
        currentPage.set(0);
    }

    public void handleNextPage() {
        if(currentPage.get() == totalPages.get()) return;
        currentPage.set(currentPage.get() + 1);
    }

    public void handleNextPageMax() {
        if(currentPage.get() == totalPages.get()) return;
        currentPage.set(totalPages.get());
    }

    public void handleShowTopMovies() {
    }

    public void handleShowMostSimilar() {
    }

    public void handleShowTopFromSimilar() {
    }
}
