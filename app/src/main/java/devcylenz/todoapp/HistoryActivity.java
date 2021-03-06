package devcylenz.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    private ListView itemsList;
    private TextView emptyList;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        clearButton = findViewById(R.id.clear_button);

        items = HistoryFileHelper.readData(this);

        itemsList = findViewById(R.id.history_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        itemsList.setAdapter(adapter);

        emptyList = findViewById(R.id.empty_history);
        itemsList.setEmptyView(emptyList);

        clearButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.clear_button) {

            HistoryManager.emptyHistory(this);
            items.clear();
            adapter.notifyDataSetChanged();
        }
    }
}