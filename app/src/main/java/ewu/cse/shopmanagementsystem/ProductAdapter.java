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

public class ProductAdapter extends ArrayAdapter<ProductHelper> {
    private  int resourceID;
    private LayoutInflater inflate;
    TextView tvProductName, tvQuantity, tvPrice, tvExpDate, tvCategory;
    public ProductAdapter(@NonNull Context context, int resourceID, ArrayList<ProductHelper>pha) {
        super(context, resourceID,pha);
        this.resourceID=resourceID;
        inflate=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView=inflate.inflate(resourceID,parent,false);
        tvProductName = rowView.findViewById(R.id.tvProductNameSellRow);
        tvExpDate = rowView.findViewById(R.id.tvExpDateSellRow);
        tvQuantity = rowView.findViewById(R.id.tvQuantitySellRow);
        tvPrice = rowView.findViewById(R.id.tvPriceSellRow);
        tvCategory = rowView.findViewById(R.id.tvCategorySellRow);
        ProductHelper ph = getItem(position);
        tvProductName.setText(ph.getProductName());
        tvExpDate.setText(ph.getExpDate());
        tvQuantity.setText(ph.getQuantity());
        tvPrice.setText(ph.getPrice());
        tvCategory.setText(ph.getCategory());
        return rowView;
    }
}
