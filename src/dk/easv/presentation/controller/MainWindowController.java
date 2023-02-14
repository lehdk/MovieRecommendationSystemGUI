package dk.easv.presentation.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    IntegerProperty currentpage = new SimpleIntegerProperty(0);
    IntegerProperty totalPages = new SimpleIntegerProperty(10);

    @FXML
    public Label pageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageLabel.textProperty().bind(Bindings.concat("page ", currentpage, " / ", totalPages));

    }

    public void handlePreviousPage() {
        if(currentpage.get() == 0) return;
        currentpage.set(currentpage.get() - 1);
    }

    public void handlePreviousPageMax() {
        if(currentpage.get() == 0) return;
        currentpage.set(0);
    }

    public void handleNextPage() {
        if(currentpage.get() == totalPages.get()) return;
        currentpage.set(currentpage.get() + 1);
    }

    public void handleNextPageMax() {
        if(currentpage.get() == totalPages.get()) return;
        currentpage.set(totalPages.get());
    }

}
