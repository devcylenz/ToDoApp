package devcylenz.todoapp;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryManager {

    public static void addToHistory (String string, Context context) {
        ArrayList<String> historyItems = HistoryFileHelper.readData(context);
        historyItems.add(string);
        HistoryFileHelper.writeData(historyItems, context);
    }

    public static void initHistory (Context context){
        HistoryFileHelper.readData(context);
    }

    public static void emptyHistory (Context context) {
        HistoryFileHelper.writeData(new ArrayList<String>(), context);
    }

}
