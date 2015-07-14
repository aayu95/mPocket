package mpocket.project.com.mpocket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abhishek on 14/7/15.
 */
public class DebtListCustomAdapter extends ArrayAdapter<DebtData> {

    public DebtListCustomAdapter(Context context, List<DebtData> objects) {
        super(context, 0, objects);
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            DebtData data = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.debt_and_loan_list, parent, false);
            }

            TextView personName = (TextView) convertView.findViewById(R.id.personNameTextview);
            TextView amount = (TextView) convertView.findViewById(R.id.amountTextview);
            TextView borrowDate = (TextView) convertView.findViewById(R.id.borrowDateTextview);
            TextView returnDate = (TextView) convertView.findViewById(R.id.returnDateTextView);


            personName.setText(data.get_lend_to());
            amount.setText("Amount: " + data.get_amount());
            borrowDate.setText("Borrowed On: " + data.get_lend_on());
            returnDate.setText("Return By: " + data.get_return_by());

            return convertView;

        }
}
