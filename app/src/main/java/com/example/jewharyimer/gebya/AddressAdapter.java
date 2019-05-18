package com.example.jewharyimer.gebya;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static com.example.jewharyimer.gebya.DeliveryActivity.SELECTED_ADDRESS;
import static com.example.jewharyimer.gebya.MyAccountFragment.MANAGE_ADDRESS;
import static com.example.jewharyimer.gebya.MyAddressActivity.RefreshItem;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private List<AddressModel> addressModelList;
    private int MODE;
    private int PreSelectedPosition;

    public AddressAdapter(List<AddressModel> addressModelList,int mode) {
        this.addressModelList = addressModelList;
        this.MODE=mode;
        PreSelectedPosition=DBqueries.selectedAddress;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_item_layout,viewGroup,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder viewHolder, int position) {
        String name=addressModelList.get(position).getFullname();
        String address=addressModelList.get(position).getAddress();
        String pin=addressModelList.get(position).getPincode();
        boolean selected=addressModelList.get(position).isSelected();
        viewHolder.setData(name,address,pin,selected,position);

    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{

        private TextView fullname;
        private TextView address;
        private TextView pincode;
        private ImageView icon;
        private LinearLayout optioncontainer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname=itemView.findViewById(R.id.full_name);
            address=itemView.findViewById(R.id.adderess);
            pincode=itemView.findViewById(R.id.pincode);
            icon=itemView.findViewById(R.id.icon_view);
            optioncontainer=itemView.findViewById(R.id.option_container);
        }

        public void setData(String username, String userAddress, String userpincode, boolean selected, final int position){
            fullname.setText(username);
            address.setText(userAddress);
            pincode.setText(userpincode);
            if(MODE==SELECTED_ADDRESS){
                icon.setImageResource(R.drawable.check);
                if(selected){
                    icon.setVisibility(View.VISIBLE);
                    PreSelectedPosition=position;
                }else {
                    icon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(PreSelectedPosition!=position) {
                            addressModelList.get(position).setSelected(true);
                            addressModelList.get(PreSelectedPosition).setSelected(false);
                            RefreshItem(PreSelectedPosition, position);
                            PreSelectedPosition = position;
                        }
                    }
                });

            }else if(MODE==MANAGE_ADDRESS){
                optioncontainer.setVisibility(View.GONE);
                icon.setImageResource(R.drawable.vertical_art);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optioncontainer.setVisibility(View.VISIBLE);
                        RefreshItem(PreSelectedPosition,PreSelectedPosition);
                        PreSelectedPosition=position;
                        DBqueries.selectedAddress=position;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RefreshItem(PreSelectedPosition,PreSelectedPosition);
                        PreSelectedPosition=-1;
                    }
                });

            }
        }
    }
}
