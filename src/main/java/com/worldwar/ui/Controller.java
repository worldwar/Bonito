package com.worldwar.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;


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
    private TableColumn<Torrenta, Double> doneColumn;

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
        doneColumn.setCellFactory(ProgressBarTableCell.<Torrenta>forTableColumn());
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
}
