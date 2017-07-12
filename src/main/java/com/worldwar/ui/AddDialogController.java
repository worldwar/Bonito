package com.worldwar.ui;

import com.worldwar.backend.RosterItem;
import com.worldwar.backend.TorrentContexts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;

public class AddDialogController extends BaseController {

    @FXML
    private TextField torrentFileText;

    @FXML
    private TextField targetFileText;

    @FXML
    private TextField nameText;



    @FXML
    private void popupFileDialog(ActionEvent event) throws IOException {
        Dialogs.popupFileDialog(event, "Open Torrent File", torrentFileText);
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
