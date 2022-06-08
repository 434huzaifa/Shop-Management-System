package ewu.cse.shopmanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomerAdapter extends ArrayAdapter<Customer> {
    private  int resourceID;
    private LayoutInflater inflate;
    public CustomerAdapter(@NonNull Context context, int resourceID, ArrayList<Customer> ca) {
        super(context, resourceID,ca);
        this.resourceID=resourceID;
        inflate=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView=inflate.inflate(resourceID,parent,false);
        TextView tvName=rowView.findViewById(R.id.tvNameCartRow);
        TextView tvQuantity=rowView.findViewById(R.id.tvQuantityCartRow);
        TextView tvPrice=rowView.findViewById(R.id.tvPriceCartRow);
        Customer c=getItem(position);
        tvName.setText(c.getProductName());
        tvQuantity.setText(c.getProductQuantity());
        tvPrice.setText(c.getTotalProductPrice());
        return rowView;
    }
}
