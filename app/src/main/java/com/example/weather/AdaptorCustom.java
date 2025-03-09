package com.example.weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptorCustom extends RecyclerView.Adapter<AdaptorCustom.ViewHolder> {
    String country;
    List<City_ListCL> list;
    Context context;
    LayoutInflater layoutInflater;
    String status1;
    private Database v1;
    SQLiteDatabase db;


    public AdaptorCustom(List<City_ListCL> list, Context context, String country) {
        this.list = list;
        this.context = context;
        this.country = country;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.row_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final City_ListCL mylist = list.get(position);

        v1=new Database(this.context);
        String ctr = mylist.getCountry();

        MainActivity activity = new MainActivity();

        if (ctr.equalsIgnoreCase(country)) {

            holder.txt_city_rw.setText(mylist.getCity());
            holder.txt_region_rw.setText(mylist.getRegion());
            int pop = mylist.getPopulation();
            holder.txt_popul_rw.setText(Integer.toString(pop));
        }

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.card_view.setCardBackgroundColor(context.getResources().getColor(R.color.teal_200));
                String city = mylist.getCity();

                if(context instanceof MainActivity){
                    ((MainActivity)context).Show_Weather(city);

                }

            }
        });

        // TODO
//        if(mylist.getStatus().equalsIgnoreCase("x")){
//            holder.linearlay1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.red_back));
//            holder.waitimage.setVisibility(View.VISIBLE);
//            holder.okimage.setVisibility(View.VISIBLE);
//            holder.cancelimage1.setVisibility(View.GONE);
//            holder.cancelimage2.setVisibility(View.GONE);
//
//        }
//
//        if (mylist.getStatus().equalsIgnoreCase("y")){
//            holder.linearlay1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.orange_back));
//            // holder.cancelimage2.setVisibility(View.GONE);
//            holder.waitimage.setVisibility(View.GONE);
//            holder.cancelimage1.setVisibility(View.VISIBLE);
//            holder.okimage.setVisibility(View.VISIBLE);
//        }
//        if (mylist.getStatus().equalsIgnoreCase("z")){
//            holder.linearlay1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green_back));
//            //holder.cancelimage1.setVisibility(View.GONE);
//            holder.okimage.setVisibility(View.GONE);
//            holder.cancelimage2.setVisibility(View.VISIBLE);
//            holder.waitimage.setVisibility(View.VISIBLE);
//        }
//
//        holder.okimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                holder.okimage.setVisibility(View.GONE);
//                holder.cancelimage1.setVisibility(View.GONE);
//                holder.waitimage.setVisibility(View.VISIBLE);
//                holder.cancelimage2.setVisibility(View.VISIBLE);
//                holder.linearlay1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green_back));
//
//                status1="z";
//                db=v1.getWritableDatabase();
//                ContentValues cvGuncelle = new ContentValues();
//                cvGuncelle.put("keytarih",mylist.getKeytarih());
//                cvGuncelle.put("task",mylist.getTask());
//                cvGuncelle.put("status",status1);
//
//                db.update("tasklist",cvGuncelle,"keytarih = '"
//                        +mylist.getKeytarih()+"'" +" and task = '"+mylist.getTask()+"'",null);
//            }
//
//        });
//
//        holder.waitimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                status1="y";
//
//                holder.okimage.setVisibility(View.VISIBLE);
//                holder.cancelimage1.setVisibility(View.VISIBLE);
//                holder.waitimage.setVisibility(View.GONE);
//                holder.cancelimage2.setVisibility(View.GONE);
//                holder.linearlay1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.orange_back));
//
//
//                db=v1.getWritableDatabase();
//                ContentValues cvGuncelle = new ContentValues();
//                cvGuncelle.put("keytarih",mylist.getKeytarih());
//                cvGuncelle.put("task",mylist.getTask());
//                cvGuncelle.put("status",status1);
//
//                db.update("tasklist",cvGuncelle,"keytarih = '"
//                        +mylist.getKeytarih()+"'"+" and task = '"+mylist.getTask()+"'",null);
//
//            }
//        });
//
//        holder.cancelimage1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                status1="x";
//                holder.okimage.setVisibility(View.VISIBLE);
//                holder.cancelimage1.setVisibility(View.GONE);
//                holder.waitimage.setVisibility(View.VISIBLE);
//                holder.cancelimage2.setVisibility(View.GONE);
//                holder.linearlay1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.red_back));
//
//
//                db=v1.getWritableDatabase();
//                ContentValues cvGuncelle = new ContentValues();
//                cvGuncelle.put("keytarih",mylist.getKeytarih());
//                cvGuncelle.put("task",mylist.getTask());
//                cvGuncelle.put("status",status1);
//
//                db.update("tasklist",cvGuncelle,"keytarih = '"
//                        +mylist.getKeytarih()+"'"+" and task = '"+mylist.getTask()+"'",null);
//            }
//        });
//
//        holder.cancelimage2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                status1="x";
//                holder.okimage.setVisibility(View.VISIBLE);
//                holder.cancelimage1.setVisibility(View.GONE);
//                holder.waitimage.setVisibility(View.VISIBLE);
//                holder.cancelimage2.setVisibility(View.GONE);
//                holder.linearlay1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.red_back));
//
//                db=v1.getWritableDatabase();
//                ContentValues cvGuncelle = new ContentValues();
//                cvGuncelle.put("keytarih",mylist.getKeytarih());
//                cvGuncelle.put("task",mylist.getTask());
//                cvGuncelle.put("status",status1);
//
//                db.update("tasklist",cvGuncelle,"keytarih = '"
//                        +mylist.getKeytarih()+"'"+" and task = '"+mylist.getTask()+"'",null);
//            }
//
//        });
//
//
//
//        holder.cardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                showAlertDialog1(v.getContext());
//
//            }
//
//            private void showAlertDialog1(Context context) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context); // use the context here
//
//                builder
//                        .setIcon(R.drawable.ic_baseline_cancel_24)
//                        .setTitle("Silmek istediğinize emin misiniz ?")
//                        //.setMessage("Çıkmak istediğinize emin misiniz ?")
//                        .setCancelable(false)     //true dersek butoana bir kere daha basarsa mesaj iptal olur
//                        .setPositiveButton("    "+"Evet", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                RemoveDB(); // bu activity'e dön
//                            }
//                        })
//                        .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//            }
//
//            private void showAlertDialog() {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context); // use the context here
//
//                builder
//                        .setIcon(R.drawable.ic_baseline_cancel_24)
//                        .setTitle("Silmek istediğinize emin misiniz ?")
//                        //.setMessage("Çıkmak istediğinize emin misiniz ?")
//                        .setCancelable(false)     //true dersek butoana bir kere daha basarsa mesaj iptal olur
//                        .setPositiveButton("    "+"Evet", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                RemoveDB(); // bu activity'e dön
//                            }
//                        })
//                        .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//
//            }
//
//            private void RemoveDB() {
//
//                db=v1.getReadableDatabase();
//                db.delete("tasklist","keytarih = '"
//                        +mylist.getKeytarih()+"'"+" and task = '"+mylist.getTask()+"'",null);
//
//                list.remove(position);
//                notifyItemRemoved(position);
//                notifyDataSetChanged();
//            }
//
//
//
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_city_rw,txt_region_rw, txt_popul_rw;

        CardView card_view;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_city_rw = itemView.findViewById(R.id.txt_city_rw);
            txt_region_rw=itemView.findViewById(R.id.txt_region_rw);
            txt_popul_rw=itemView.findViewById(R.id.txt_popul_rw);
            card_view=itemView.findViewById(R.id.card_view);


        }
    }
}


