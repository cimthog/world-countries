package com.divineventures.countryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{


    private List<MyData> dataList;
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private static final int API_LOADER = 1;
    private final String API_URL = "https://restcountries.eu/rest/v2/all";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        dataList = new ArrayList<>();
        myAdapter = new MyAdapter(this, dataList);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"You clicked",Toast.LENGTH_SHORT).show();
                Intent detailsIntent = new Intent(getApplicationContext(),DetailActivity.class);
                detailsIntent.putExtra(Intent.EXTRA_TEXT,"test");
                startActivity(detailsIntent);

            }
        });



        Bundle queryBundle = new Bundle();
        queryBundle.putString("API_URL", API_URL);

        task myTask = new task();
        myTask.execute();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        MenuItem searchMenuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setOnQueryTextListener(MainActivity.this);

        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                myAdapter = new MyAdapter(MainActivity.this, dataList);
                recyclerView.setAdapter(myAdapter);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<MyData> newList = new ArrayList<>();
        for (MyData c : dataList) {
            String name = c.getName().toLowerCase();

            if (name.contains(newText)) {
                newList.add(c);
            }

        }
        myAdapter.setFilter(newList);
        return false;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (R.id.action_refresh):
                task myTask = new task();
                myTask.execute();
                myAdapter = new MyAdapter(this, dataList);
                recyclerView.setAdapter(myAdapter);
                break;
            case (R.id.action_settings):
                return true;
            case (R.id.action_extit) :
                closeApp();
            default:
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void closeApp() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Do you want to exit this app?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        alert.show();
    }


    public class task extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startActivity(new Intent(getApplicationContext(),SplashSCreen.class));
        }


        @Override
        protected String doInBackground(String... strings) {
            String url = "https://restcountries.eu/rest/v2/all";

             try {
                 OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Response response = null;

                try {
                    response = client.newCall(request).execute();
                    JSONArray object = new JSONArray(response.body().string());
                    for (int i = 0; i < object.length(); i++) {

                        JSONObject list = object.getJSONObject(i);

                        String name = list.getString("name");
                        String flag = list.getString("flag");
                        String region = list.getString("region");
                        String population = list.getString("population");

                        MyData data = new MyData(name, flag, region, population);
                        dataList.add(data);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of List");

            }
             }catch (NetworkOnMainThreadException e) {
                 Toast.makeText(getApplicationContext(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
             }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            myAdapter.notifyDataSetChanged();
        }
    }
}

