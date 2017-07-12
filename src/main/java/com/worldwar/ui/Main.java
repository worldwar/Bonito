package com.worldwar.ui;

import com.worldwar.backend.*;
import com.worldwar.backend.task.TaskScheduler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private Stage primaryStage;

    private BorderPane rootLayout;

    private ObservableList<Torrenta> torrentData = FXCollections.observableArrayList();

    private Agent agent;

    private Stage addDialog = new Stage();

    private Stage createDialog = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        agent = new Agent("bonito.json");
        agent.start();
        initRootLayout();
        initData();
        show();
    }

    private void initData() {
        for (RosterItem item : agent.roster().getTorrents()) {
            addTorrenta(item);
        }
    }

    public void addTorrenta(RosterItem item) {
        Torrenta torrenta = rosterToTorrenta(item);
        TorrentContext context = TorrentContexts.from(item);
        DoneTask doneTask = new DoneTask(1000, 1000, TorrentRegister.get(context.hashinfo()),
                torrenta);
        TaskScheduler.getInstance().emit(doneTask);
        torrentData.add(torrenta);
    }

    private Torrenta rosterToTorrenta(RosterItem item) {
        Torrenta torrenta = new Torrenta();
        torrenta.setName(item.getFilename());
        torrenta.setSize(item.getSize());
        torrenta.setDone(item.getDownloaded() * 1.0d / item.getSize());
        return torrenta;
    }

    private void show() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/torrent.fxml"));
        Parent root = fxmlLoader.load();
        rootLayout.setCenter(root);
        Controller controller = fxmlLoader.getController();
        controller.setMain(this);
    }

    private void initRootLayout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL resource = getClass().getResource("/RootLayout.fxml");
        fxmlLoader.setLocation(resource);
        rootLayout = fxmlLoader.load();
        primaryStage.setScene(new Scene(rootLayout, 831, 475));
        primaryStage.show();

        addDialog.initModality(Modality.APPLICATION_MODAL);
        createDialog.initModality(Modality.APPLICATION_MODAL);
        addDialog.initOwner(primaryStage.getScene().getWindow());
        createDialog.initOwner(primaryStage.getScene().getWindow());
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ObservableList<Torrenta> getTorrentData() {
        return torrentData;
    }

    public void handleAddButton() throws IOException {
        popupDialog(addDialog, "/addDialog.fxml", "Add Torrent");
    }

    public void handleCreateButton() throws IOException {
        popupDialog(createDialog, "/createDialog.fxml", "create Torrent");
    }

    private void popupDialog(Stage dialog, String dialogResource, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource(dialogResource));
        Parent root = fxmlLoader.load();
        dialog.setScene(new Scene(root));

        BaseController controller = fxmlLoader.getController();
        controller.setMain(this);
        dialog.setTitle(title);
        dialog.showAndWait();
    }

    public void closeAddDialog() {
        addDialog.close();
    }

    public void closeCreateDialog() {
        createDialog.close();
    }

    public void addTorrent(RosterItem rosterItem) {
        TorrentContext context;
        try {
            context = agent.add(rosterItem);
            context.start();
        } catch (IOException e) {
        }
        addTorrenta(rosterItem);
    }
}
