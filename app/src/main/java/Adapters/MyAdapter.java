package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.immunizationmanager.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Classes.Vaccine;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Vaccine> mList;
    Context context;

    public MyAdapter(Context context, ArrayList<Vaccine> mList){
        this.mList=mList;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.vaccine_view,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vaccine vaccine=mList.get(position);
        holder.VaccineName.setText(vaccine.getVaccineName());
        holder.receivedDate.setText(vaccine.getDateVaccinated());

        String mode = vaccine.getMode();
        Drawable drawable;
        if (mode.equals("Oral")){
            drawable = DrawableCompat.wrap(AppCompatResources.getDrawable(context, R.drawable.oral));
        }else{
            drawable = DrawableCompat.wrap(AppCompatResources.getDrawable(context, R.drawable.injection));
        }
        holder.oral.setImageDrawable(drawable);

        String date=vaccine.getDateVaccinated();
        int pos=0;
        if(pos == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE,0);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }

        int pos1=1;
        if(pos1 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 0);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }

        int pos2=2;
        if(pos2 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 0);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }

        int pos3=3;
        if(pos3 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 42);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }

        int pos4=4;
        if(pos4 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE,70);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos5=5;
        if(pos5== position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE,98);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos6=6;
        if(pos6 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 42);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos7=7;
        if(pos7 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 70);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos8=8;
        if(pos8 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 98);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }

        int pos9=9;
        if(pos9 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 42);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos10=10;
        if(pos10 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 70);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos11=11;
        if(pos11 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 98);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos12=12;
        if(pos12 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 42);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos13=13;
        if(pos13 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 98);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos14=14;
        if(pos14 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 252);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos15=15;
        if(pos15 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 168);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos16=16;
        if(pos16 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 504);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos17=17;
        if(pos17 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 365);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos18=18;
        if(pos18 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 547);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos19=19;
        if(pos19 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 730);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }

        int pos20=20;
        if(pos20 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 912);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos21=21;
        if(pos21 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 1094);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos22=22;
        if(pos22 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 1276);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos23=23;
        if(pos23 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 1458);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        int pos24=24;
        if(pos24 == position) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 1640);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyy");
            String output = sdf1.format(c.getTime());
            holder.receivedDate.setText("\t"+output);
        }
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,holder.menu);
                popupMenu.inflate(R.menu.options_menu);

                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()){

                        case  R.id.menuEdit:
                            Toast.makeText(context, "Clicked Edit Option", Toast.LENGTH_SHORT).show();
                            break;

                            case  R.id.menuDelete:
                            Toast.makeText(context, "Clicked Delete Option", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.menuReminder:

                            Toast.makeText(context, "Clicked Reminder Option", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return false;
                });
                popupMenu.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public  static  class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView VaccineName,receivedDate;
        ImageView oral,menu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

           VaccineName=itemView.findViewById(R.id.VacName);
           receivedDate=itemView.findViewById(R.id.adminDate);
           oral=itemView.findViewById(R.id.oral);
           menu=itemView.findViewById(R.id.textOption);
        }
    }
}
