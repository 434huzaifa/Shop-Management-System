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

public class EmployeeAdapter extends ArrayAdapter<EmployeeHelper> {
    private  int resourceID;
    private LayoutInflater inflate;
    public EmployeeAdapter(Context context,int resourceID ,ArrayList<EmployeeHelper>empArrayList) {
        super(context,resourceID,empArrayList);
        this.resourceID=resourceID;
        inflate=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View rowView=inflate.inflate(resourceID,parent,false);
       TextView etName,etGender,etPosition,etDate;
       etName=rowView.findViewById(R.id.nameEmpRow);
       etGender=rowView.findViewById(R.id.genderEmpRow);
       etPosition=rowView.findViewById(R.id.positionEmpRow);
       etDate=rowView.findViewById(R.id.joinDateEmpRow);
       EmployeeHelper eh=this.getItem(position);
       etName.setText(eh.getName());
       etGender.setText(eh.getGender());
       etPosition.setText(eh.getPosition());
       etDate.setText(eh.getJoin_date());
        return rowView;
    }
}
