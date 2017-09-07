package com.example.prathamesh1.lavanya_makeup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HI on 5/10/2017.
 */

public class CustomerAdapter extends ArrayAdapter {

    ArrayList<Customer> customerList = new ArrayList<>();
    Context ctx;

    public CustomerAdapter(Context context,int resource) {
        super(context, resource);
        ctx = context;
    }

    public void add(Customer object) {
        customerList.add(object);
        super.add(object);
    }

    public void removeAll(){
        customerList.removeAll(customerList);
    }

    @Override
    public int getCount() {
        return customerList.size();
    }

    @Override
    public Customer getItem(int position) {
        return customerList.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CustomerHolder customerHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.item_listview, parent, false);
            customerHolder = new CustomerHolder();
            customerHolder.tx_mobileNo = (TextView) row.findViewById(R.id.TV_MobileNo);
            customerHolder.tx_name = (TextView) row.findViewById(R.id.TV_Name);
            customerHolder.tx_emailId = (TextView) row.findViewById(R.id.TV_EmailId);
            customerHolder.tx_dob = (TextView) row.findViewById(R.id.TV_DOB);
            customerHolder.tx_doa = (TextView) row.findViewById(R.id.TV_DOA);
            customerHolder.tx_lln = (TextView) row.findViewById(R.id.TV_Lln);
            row.setTag(customerHolder);

        }
        else{
            customerHolder = (CustomerHolder) row.getTag();
        }
        Customer customer = getItem(position);
        customerHolder.tx_name.setText(customer.getName());
        customerHolder.tx_mobileNo.setText(customer.getMobileNo());
        customerHolder.tx_emailId.setText(customer.getEmailId());
        customerHolder.tx_dob.setText(customer.getDob());
        customerHolder.tx_doa.setText(customer.getDoa());
        customerHolder.tx_lln.setText(customer.getLln());
        return row;
    }


    static class CustomerHolder{
        TextView tx_mobileNo, tx_name, tx_emailId, tx_dob, tx_doa, tx_lln;
    }
}
