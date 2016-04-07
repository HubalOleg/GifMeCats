package oleg.hubal.com.gifmecats;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

import oleg.hubal.com.gifmecats.Model.Cat;
import oleg.hubal.com.gifmecats.api.AsyncRequest;
import oleg.hubal.com.gifmecats.api.IResponseListener;
import oleg.hubal.com.gifmecats.api.TypeRequest;
import oleg.hubal.com.gifmecats.fragments.AddCatFragment;
import oleg.hubal.com.gifmecats.interfaces.OnCatListUser;
import oleg.hubal.com.gifmecats.list.CatHeadlessFragment;
import oleg.hubal.com.gifmecats.list.CatViewAdapter;

public class MainActivity extends AppCompatActivity implements OnCatListUser {

    private ArrayList<Cat> cats;
    private RecyclerView rvCatList;
    private CatHeadlessFragment catHeadlessFragment;
    private Set categories;
    private SharedPreferences sPref;
    private AsyncRequest<?> asyncRequest;
    private int catCounter;
    private boolean isDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            commitCatHeadlessFragment();
        }

        loadCategories();
        catsManipulations();
        createList();
    }

    @Override
    public void onPlusButtonClicked() {
        DialogFragment dialog = new AddCatFragment();
        Bundle bundle = new Bundle();
        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.addAll(categories);
        bundle.putStringArrayList(Constants.KEY_CATEGORY, categoryList);
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), Constants.TAG_DIALOG);
    }

    @Override
    public void onCatImageClicked() {

    }

    public void onDialogAddClicked(int catCount, String category) {

    }

    private void loadCategories() {
        sPref = getPreferences(MODE_PRIVATE);
        categories = sPref.getStringSet(Constants.KEY_CATEGORY, null);
        if (categories == null) {
            asyncRequest = new AsyncRequest<Set>()
                    .classType(Set.class)
                    .typeRequest(TypeRequest.GET)
                    .responseListener(categoryListener);
            asyncRequest.execute(Constants.BASE_URL + Constants.GET_CATEGORIES);
        }
    }

    private final IResponseListener<Set> categoryListener = new IResponseListener<Set>() {
        @Override
        public void onStart() {

        }

        @Override
        public void onFinish(boolean isSuccess, Set response) {
            categories = response;

            sPref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putStringSet(Constants.KEY_CATEGORY, categories);
            ed.apply();
        }
    };

    private void catsManipulations() {
        cats = catHeadlessFragment.getCats();

        Bitmap plusIcon = BitmapFactory.decodeResource(getResources(), R.drawable.web_hi_res_5122);
        Cat plusButton = new Cat();
        plusButton.setImage(Bitmap.createBitmap(plusIcon));
        cats.add(plusButton);
    }

    private void createList() {
        rvCatList = (RecyclerView) findViewById(R.id.rvCatList_AM);
        rvCatList.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvCatList.setLayoutManager(layoutManager);

        CatViewAdapter catAdapter = new CatViewAdapter(cats, this);
        rvCatList.setAdapter(catAdapter);
    }

    private void commitCatHeadlessFragment() {
        catHeadlessFragment = new CatHeadlessFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(catHeadlessFragment, Constants.KEY_FRAGMENT_CAT)
                .commit();
    }
}
