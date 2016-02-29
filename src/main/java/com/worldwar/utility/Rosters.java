package com.worldwar.utility;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.worldwar.backend.Roster;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Rosters {
    public static Roster from(String filename) {
        try {
            JsonReader jsonReader = new JsonReader(new FileReader(filename));
            return new Gson().fromJson(jsonReader, Roster.class);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
