package oleg.hubal.com.gifmecats.list;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import oleg.hubal.com.gifmecats.R;
import oleg.hubal.com.gifmecats.interfaces.OnCatListUser;

/**
 * Created by User on 07.04.2016.
 */
public class CatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView ivCatImage;
    private OnCatListUser listUser;

    CatViewHolder(View itemView, OnCatListUser listUser) {
        super(itemView);

        this.listUser = listUser;

        ivCatImage = (ImageView) itemView.findViewById(R.id.ivCatImage_CC);

        ivCatImage.setOnClickListener(this);
    }

    public void setImage(Bitmap bitmap) {
        ivCatImage.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        listUser.onCatImageClicked(getAdapterPosition());
    }
}
