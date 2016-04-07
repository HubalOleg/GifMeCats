package oleg.hubal.com.gifmecats.fragments;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import oleg.hubal.com.gifmecats.Constants;
import oleg.hubal.com.gifmecats.MainActivity;
import oleg.hubal.com.gifmecats.R;
import oleg.hubal.com.gifmecats.api.AsyncRequest;
import oleg.hubal.com.gifmecats.api.IResponseListener;
import oleg.hubal.com.gifmecats.api.TypeRequest;

/**
 * Created by User on 08.04.2016.
 */
public class ShowFullFragment extends DialogFragment implements View.OnClickListener {

    private Button btnDelete, btnBack;
    private ImageView catImage;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_full, container, false);

        initViews();
        loadImage();

        return view;
    }

    private void loadImage() {
        Bundle bundle = getArguments();
        String id = bundle.getString(Constants.KEY_CAT_ID);
        AsyncRequest<?> fullImageRequest = new AsyncRequest<Bitmap>()
                .typeRequest(TypeRequest.GET)
                .addParam(Constants.KEY_ID, id)
                .addParam(Constants.KEY_SIZE, "full")
                .classType(Bitmap.class)
                .responseListener(fullImageListener);
        fullImageRequest.execute(Constants.BASE_URL + Constants.GET_IMAGES);
    }

    private final IResponseListener<Bitmap> fullImageListener = new IResponseListener<Bitmap>() {
        @Override
        public void onStart() {

        }

        @Override
        public void onFinish(boolean isSuccess, Bitmap response) {
            catImage.setImageBitmap(response);
        }
    };

    private void initViews() {
        btnDelete = (Button) view.findViewById(R.id.btnDelete_FSF);
        btnBack = (Button) view.findViewById(R.id.btnBack_FSF);

        catImage = (ImageView) view.findViewById(R.id.ivCatImage_FSF);

        btnDelete.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDelete_FSF:
                ((MainActivity) getActivity()).onDeleteCatClicked();
                dismiss();
                break;
            case R.id.btnBack_FSF:
                dismiss();
                break;
        }
    }
}
