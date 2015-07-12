package mpocket.project.com.mpocket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abhishek on 12/7/15.
 */
public class PersonalExpensesCustomListAdapter extends ArrayAdapter<PersonalExpensesData> {

    public PersonalExpensesCustomListAdapter(Context context, List<PersonalExpensesData> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PersonalExpensesData data = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.personal_expenses_list, parent, false);
        }

        TextView purpose = (TextView) convertView.findViewById(R.id.purposeTextView);
        TextView amount = (TextView) convertView.findViewById(R.id.amountTextview);
        TextView date = (TextView) convertView.findViewById(R.id.dateTextView);


        purpose.setText(data.get_purpose());
        amount.setText("Amount: "+data.get_amount());
        date.setText("Date: " + data.get_date());

        return convertView;

    }
}
