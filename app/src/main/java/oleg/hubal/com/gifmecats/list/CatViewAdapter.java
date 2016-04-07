package oleg.hubal.com.gifmecats.list;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import oleg.hubal.com.gifmecats.Model.Cat;
import oleg.hubal.com.gifmecats.R;
import oleg.hubal.com.gifmecats.interfaces.OnCatListUser;

/**
 * Created by User on 07.04.2016.
 */
public class CatViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Cat> cats;
    private View v;
    private RecyclerView.ViewHolder vh;
    private OnCatListUser listUser;

    public CatViewAdapter(ArrayList<Cat> cats, OnCatListUser listUser) {
        this.cats = cats;
        this.listUser = listUser;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cat_card, parent, false);
                vh = new CatViewHolder(v, listUser);
                break;
            case 1:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.plus_card, parent, false);
                vh = new PlusViewHolder(v, listUser);
                break;
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (this.getItemViewType(position)) {
            case 0:
                CatViewHolder catHolder = (CatViewHolder) holder;
                catHolder.setImage(cats.get(position).getImage());
                break;
            case 1:
                PlusViewHolder plusHolder = (PlusViewHolder) holder;
                plusHolder.setImage(cats.get(position).getImage());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return cats.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == cats.size() - 1)
            return 1;
        else
            return 0;
    }
}
