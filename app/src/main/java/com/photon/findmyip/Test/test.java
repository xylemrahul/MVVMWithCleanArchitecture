package com.photon.findmyip.Test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class test {

    private static final String URL = "https://jsonmock.hackerrank.com/api/articles?";
    private static List<String> getArticlesTitles(String author) throws IOException {

        List<String> titles = new ArrayList<>();

        int page  = 1;
        int totalPages = 1;
        String response;

        while(page <= totalPages){
            URL obj = new URL(URL + "&author=" + author + "&page=" +page);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while((response = in.readLine())!=null){
                System.out.print(response);
                JsonObject jsonResponse = new Gson().fromJson(response, JsonObject.class);

                totalPages = jsonResponse.get("total_pages").getAsInt();
                JsonArray data = jsonResponse.getAsJsonArray("data");

                for(JsonElement e : data){
                    String title = e.getAsJsonObject().get("title").getAsString();
                    String story_title = e.getAsJsonObject().get("story_title").getAsString();
                    if(!title.isEmpty()){
                        titles.add(title);
                    }else if(!story_title.isEmpty()){
                        titles.add(story_title);
                    }
                }
            }
            page++;
        }
        return titles;
    }
}
