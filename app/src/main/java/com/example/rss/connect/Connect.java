package com.example.rss.connect;

import com.example.rss.parser.Parser;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Connect {

    public List connect(final String url){

        List newsList = null;

        try {
            URL contentUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) contentUrl.openConnection();
            connection.setReadTimeout(10*1000);
            connection.setConnectTimeout(10*1000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            InputStream is = connection.getInputStream();
            newsList = new Parser().parse(is);
        } catch (Exception e){}

        return newsList;
    }
}
