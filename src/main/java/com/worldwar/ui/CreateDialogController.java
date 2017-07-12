package com.worldwar.ui;

import com.google.common.base.Strings;
import com.worldwar.Metainfo;
import com.worldwar.Metainfos;
import com.worldwar.bencoding.BEncoding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Paths;

public class CreateDialogController extends BaseController {

    @FXML
    private TextField seedFileText;

    @FXML
    private TextField titleText;

    @FXML
    private void selectSeedFileDialog(ActionEvent event) throws IOException {
        Dialogs.popupFileDialog(event, "select seed file", seedFileText);
    }

    @FXML
    private void createTorrent(ActionEvent event) throws IOException {
        String seedFilename = seedFileText.getText();
        String title = titleText.getText();
        if (Strings.isNullOrEmpty(title)) {
            title = Paths.get(seedFilename).getFileName().toString();
        }
        Metainfo metainfo = Metainfos.generateMetainfo(seedFilename);
        metainfo.getInfo().setName(title);

        String path = Dialogs.popupSaveFileDialogAndGetFilename(event, "save to");
        if (path != null) {
            if (!path.endsWith(".torrent")) {
                path += ".torrent";
            }
            BEncoding.write(path, Metainfos.bObject(metainfo));
            main.closeCreateDialog();
        }
    }
}
