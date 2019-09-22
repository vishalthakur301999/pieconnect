package com.example.tom_m.myapplication.Control;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tom_m.myapplication.Model.Macro;
import com.example.tom_m.myapplication.R;
import java.util.ArrayList;

public class MacroAdapter extends RecyclerView.Adapter<MacroAdapter.MacroViewHolder> {

    private ArrayList<Macro> macroList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        listener = listener;
    }
    public MacroAdapter(ArrayList<Macro> macroList) {
        this.macroList = macroList;
    }

    public static class MacroViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView title, command, lastExecution, amountsOfExecution, exitCode;

        public MacroViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            icon                = itemView.findViewById(R.id.icon);
            title               = itemView.findViewById(R.id.title);
            command             = itemView.findViewById(R.id.command);
            lastExecution       = itemView.findViewById(R.id.lastExecution);
            amountsOfExecution  = itemView.findViewById(R.id.amountsOfExecution);
            exitCode            = itemView.findViewById(R.id.exitCode);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) listener.onItemClick(position);
                    }
                }
            });
        }
    }

    @Override
    public MacroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.macro_item, parent, false);
        MacroViewHolder evh = new MacroViewHolder(v, listener);
        return evh;
    }

    @Override
    public void onBindViewHolder(MacroViewHolder holder, int position) {
        Macro currentItem = macroList.get(position);

        holder.icon.setImageResource(currentItem.getImageResource());
        holder.title.setText(currentItem.getTitle());
        holder.command.setText(currentItem.getCommand());
        holder.lastExecution.setText(currentItem.getLastExecution());
        holder.amountsOfExecution.setText(Integer.toString(currentItem.getAmountsExecuted()));
        holder.exitCode.setText(Integer.toString(currentItem.getExitCode()));
    }

    @Override
    public int getItemCount() {
        return macroList.size();
    }
}