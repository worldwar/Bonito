package com.worldwar.ui;

import com.worldwar.backend.RosterItem;
import com.worldwar.backend.TorrentContexts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class AddDialogController {

    private Main main;

    @FXML
    private TextField torrentFileText;

    @FXML
    private TextField targetFileText;

    @FXML
    private TextField nameText;

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void popupFileDialog(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Torrent File");
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("torrent file", ".torrent"));
        File file = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());
        if (file != null) {
            String path = file.getAbsolutePath();
            torrentFileText.setText(path);
        }
    }

    @FXML
    private void popupDirectoryDialog(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Open Torrent File");
        File file = chooser.showDialog(((Button) event.getSource()).getScene().getWindow());
        if (file != null) {
            String path = file.getAbsolutePath();
            targetFileText.setText(path);
        }
    }

    @FXML
    private void addTorrent(ActionEvent event) {
        String torrent = torrentFileText.getText();
        String target = targetFileText.getText();
        String name = nameText.getText();
        RosterItem rosterItem = TorrentContexts.rosterItem(torrent, target, name);
        main.addTorrent(rosterItem);
        main.closeAddDialog();
    }
}
