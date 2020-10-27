package devcylenz.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText itemET;
    private Button addButton;
    private ListView itemsList;
    private TextView emptyList;

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HistoryManager.initHistory(this);

        itemET = findViewById(R.id.item_edit_text);
        addButton = findViewById(R.id.add_btn);
        itemsList = findViewById(R.id.items_list);

        items = FileHelper.readData(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, items);
        itemsList.setAdapter(adapter);

        emptyList = findViewById(R.id.empty_list);
        itemsList.setEmptyView(emptyList);

        addButton.setOnClickListener(this);
        itemsList.setOnItemClickListener(this);
        itemET.setOnEditorActionListener(
                (arg0, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        addItem();
                        return true;
                    }
                    return false;
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        //return true;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_history){
            Intent historyActivity = new Intent(this, HistoryActivity.class);
            startActivity(historyActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_btn) {
            addItem();
        }
    }

    private void addItem() {
        String itemEntered = itemET.getText().toString();
        if (itemEntered.equals("")) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.add_btn) , R.string.snackbar_empty, Snackbar.LENGTH_SHORT);
            snackbar.setAction("X", v -> snackbar.dismiss());
            snackbar.show();
            return;
        }
        adapter.add(itemEntered);
        itemET.setText("");

        FileHelper.writeData(items, this);

        Snackbar snackbar = Snackbar.make(findViewById(R.id.add_btn) , R.string.item_added, Snackbar.LENGTH_SHORT);
        snackbar.setAction("X", v -> snackbar.dismiss());
        snackbar.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HistoryManager.addToHistory(items.get(position), this);
        items.remove(position);
        adapter.notifyDataSetChanged();
        FileHelper.writeData(items, this);

        Snackbar snackbar = Snackbar.make(findViewById(R.id.add_btn) , R.string.snackbar_done, Snackbar.LENGTH_SHORT);
        snackbar.setAction("X", v -> snackbar.dismiss());
        snackbar.show();
    }
}