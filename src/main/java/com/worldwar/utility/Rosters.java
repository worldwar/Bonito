package com.worldwar.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.worldwar.backend.Roster;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;

public class Rosters {

    private static Gson gson;

    static {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public static Roster from(String filename) {
        try {
            JsonReader jsonReader = new JsonReader(new FileReader(filename));
            return gson.fromJson(jsonReader, Roster.class);
        } catch (FileNotFoundException e) {
            return empty();
        }
    }

    public static void write(Roster roster, String filename) {
        String json = gson.toJson(roster);
        try (OutputStream os = new FileOutputStream(filename)) {
            os.write(json.getBytes());
        } catch (Exception e) {
        }
    }

    public static Roster empty() {
        return new Roster();
    }
}
