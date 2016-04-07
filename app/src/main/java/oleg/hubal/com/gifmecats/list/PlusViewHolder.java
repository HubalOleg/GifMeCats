package oleg.hubal.com.gifmecats.list;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import oleg.hubal.com.gifmecats.R;
import oleg.hubal.com.gifmecats.interfaces.OnCatListUser;

/**
 * Created by User on 07.04.2016.
 */
public class PlusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnCatListUser listUser;
    private ImageView btnPlus;

    PlusViewHolder(View itemView, OnCatListUser listUser) {
        super(itemView);

        this.listUser = listUser;

        btnPlus = (ImageView) itemView.findViewById(R.id.ivPlusImage_PC);
        btnPlus.setOnClickListener(this);
    }

    public void setImage(Bitmap bitmap) {
        btnPlus.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        listUser.onPlusButtonClicked();
    }
}