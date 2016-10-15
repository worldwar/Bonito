package com.worldwar.ui;

import com.worldwar.backend.*;
import com.worldwar.backend.task.TaskScheduler;
import com.worldwar.utility.Rosters;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Torrenta> torrentData = FXCollections.observableArrayList();
    private Roster roster;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        new Listener().listen(8888);
        initRootLayout();
        initData();
        show();
    }

    private void initData() {
        roster = Rosters.from("bonito.json");
        roster.register();
        for (RosterItem item : roster.getTorrents()) {
            Torrenta torrenta = rosterToTorrenta(item);
            TorrentContext context = TorrentContexts.from(item);
            DoneTask doneTask = new DoneTask(1000, 1000, TorrentRegister.get(context.hashinfo()), torrenta);
            TaskScheduler.getInstance().emit(doneTask);
            torrentData.add(torrenta);
        }
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
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ObservableList<Torrenta> getTorrentData() {
        return torrentData;
    }
}
