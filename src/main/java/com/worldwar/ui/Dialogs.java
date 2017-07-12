package com.worldwar.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class Dialogs {

    public static void popupFileDialog(ActionEvent event, String title, TextField textField) {
        String path = popupFileDialogAndGetFilename(event, title);
        if (path != null) {
            textField.setText(path);
        }
    }

    public static String popupFileDialogAndGetFilename(ActionEvent event, String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("torrent file", ".torrent"));
        File file = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());
        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return null;
        }
    }

    public static void popupSaveFileDialog(ActionEvent event, String title, TextField textField) {
        String path = popupSaveFileDialogAndGetFilename(event, title);
        if (path != null) {
            textField.setText(path);
        }
    }

    public static String popupSaveFileDialogAndGetFilename(ActionEvent event, String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("torrent file", ".torrent"));
        File file = fileChooser.showSaveDialog(((Button) event.getSource()).getScene().getWindow());
        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return null;
        }
    }
}
