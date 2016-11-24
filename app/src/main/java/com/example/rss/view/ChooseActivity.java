package com.example.rss.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.rss.R;
import com.example.rss.url.UrlBase;

public class ChooseActivity extends AppCompatActivity implements UrlBase{

    private final String[] mChooseArray = new String[]{"Деньги и власть", "Общество", "В мире", "Кругозор", "Проишествия",
            "Финансы", "Недвижимость", "Авто", "Спорт", "Леди", "42", "Афиша", "Новости компаний"};
    private ListView mChooseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChooseList = (ListView) findViewById(R.id.choose_activity_listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mChooseArray);
        mChooseList.setAdapter(adapter);

        mChooseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = ((TextView) view).getText().toString();
                String url = getTitleOfItem(title);
                Intent intent = EventsActivity.newInstance(getApplicationContext(), title, url);
                startActivity(intent);
            }
        });
    }

    private String getTitleOfItem(String title){
        String url = null;
        switch (title){
            case "Деньги и власть":
                url = ECONOMICS;
                break;
            case "Общество":
                url = SOCIETY;
                break;
            case "В мире":
                url = WORLD;
                break;
            case "Кругозор":
                url = CULTURE;
                break;
            case "Проишествия":
                url = ACCIDENTS;
                break;
            case "Финансы":
                url = FINANCE;
                break;
            case "Недвижимость":
                url = REALTY;
                break;
            case "Авто":
                url = AUTO;
                break;
            case "Спорт":
                url = SPORT;
                break;
            case "Леди":
                url = LADY;
                break;
            case "42":
                url = IT;
                break;
            case "Афиша":
                url = AFISHA;
                break;
            case "Новости компаний":
                url = PRESS;
                break;
        }
        return url;
    }
}
