package com.worldwar.ui;

import com.worldwar.backend.Roster;
import com.worldwar.backend.RosterItem;
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

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        initRootLayout();
        initData();
        show();
    }

    private void initData() {
        Roster from = Rosters.from("bonito.json");
        for (RosterItem item : from.getTorrents()) {
            Torrenta torrenta = rosterToTorrenta(item);
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
