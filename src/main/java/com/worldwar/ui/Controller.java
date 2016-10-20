package com.worldwar.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class Controller {

    @FXML
    private ListView<Torrenta> torrentList;

    @FXML
    private TableView<Torrenta> torrentTable;

    @FXML
    private TableColumn<Torrenta, String> nameColumn;

    @FXML
    private TableColumn<Torrenta, String> orderColumn;

    @FXML
    private TableColumn<Torrenta, Number> sizeColumn;

    @FXML
    private TableColumn<DoneTask, Double> doneColumn;

    @FXML
    private Button addTorrentButon;

    @FXML
    private Button addURLButton;

    private Main main;

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        sizeColumn.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        doneColumn.setCellValueFactory(new PropertyValueFactory<>("done"));
        doneColumn.setCellFactory(ProgressBarTableCell.<DoneTask>forTableColumn());
        torrentList.setCellFactory(cellData -> new ListCell<Torrenta>() {
            @Override
            public void updateItem(Torrenta item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                }
            }
        });
    }

    public void setMain(Main main) {
        this.main = main;
        torrentList.setItems(main.getTorrentData());
        torrentTable.setItems(main.getTorrentData());
    }

    @FXML
    private void handleAddButton(ActionEvent event) throws IOException {
        main.handleAddButton();
    }
}
