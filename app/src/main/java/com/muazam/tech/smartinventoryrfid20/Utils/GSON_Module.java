package com.muazam.tech.smartinventoryrfid20.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GSON_Module {


    public String _put_values(ArrayList<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public ArrayList<String> _get_values(String value) {
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(value, type);
    }
}
