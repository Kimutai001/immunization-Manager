package Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.immunizationmanager.R;
import com.example.immunizationmanager.ViewVaccine;

import java.util.ArrayList;

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
        holder.timeLine.setText(vaccine.getTimeLine());
        String mode = vaccine.getMode();

        Drawable drawable;
        if (mode.equals("Oral")){
            drawable = DrawableCompat.wrap(AppCompatResources.getDrawable(context, R.drawable.oral));
        }else{
            drawable = DrawableCompat.wrap(AppCompatResources.getDrawable(context, R.drawable.injection));
        }
        holder.oral.setImageDrawable(drawable);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public  static  class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView VaccineName,receivedDate,timeLine;
        ImageView oral;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

           VaccineName=itemView.findViewById(R.id.VacName);
           receivedDate=itemView.findViewById(R.id.adminDate);
           timeLine=itemView.findViewById(R.id.timeLine);
           oral=itemView.findViewById(R.id.oral);
        }
    }
}
