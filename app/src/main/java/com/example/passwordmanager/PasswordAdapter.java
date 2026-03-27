
package com.example.passwordmanager;

import android.text.method.PasswordTransformationMethod;
import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import java.util.*;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.ViewHolder> {

    private List<PasswordEntity> list;
    private OnItemAction listener;
    private Set<Integer> visiblePositions = new HashSet<>();

    public interface OnItemAction {
        void onDelete(PasswordEntity entity);
        void onEdit(PasswordEntity entity);
    }

    public PasswordAdapter(List<PasswordEntity> list, OnItemAction listener) {
        this.list = list;
        this.listener = listener;
    }

    public void updateList(List<PasswordEntity> newList){
        this.list = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView site, user, pass;
        Button deleteBtn, editBtn;
        ImageButton toggleVisible;

        public ViewHolder(View v) {
            super(v);
            site = v.findViewById(R.id.siteText);
            user = v.findViewById(R.id.userText);
            pass = v.findViewById(R.id.passText);
            deleteBtn = v.findViewById(R.id.deleteBtn);
            editBtn = v.findViewById(R.id.editBtn);
            toggleVisible = v.findViewById(R.id.toggleVisible);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_password, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int pos) {
        PasswordEntity p = list.get(pos);

        h.site.setText(p.siteName);
        h.user.setText(p.username);
        
        // Decrypt the text
        String plainText = EncryptionUtil.decrypt(p.password);
        h.pass.setText(plainText);

        // Manage visibility
        if (visiblePositions.contains(pos)) {
            h.pass.setTransformationMethod(null);
            h.toggleVisible.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        } else {
            h.pass.setTransformationMethod(new PasswordTransformationMethod());
            h.toggleVisible.setImageResource(android.R.drawable.ic_menu_view);
        }

        h.toggleVisible.setOnClickListener(v -> {
            if (visiblePositions.contains(pos)) {
                visiblePositions.remove(pos);
            } else {
                visiblePositions.add(pos);
            }
            notifyItemChanged(pos);
        });

        h.deleteBtn.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(p);
        });

        h.editBtn.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(p);
        });
    }

    @Override
    public int getItemCount() { 
        return list == null ? 0 : list.size();
    }
}
