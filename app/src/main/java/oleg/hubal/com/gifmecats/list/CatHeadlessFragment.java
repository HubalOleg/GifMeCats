package oleg.hubal.com.gifmecats.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import oleg.hubal.com.gifmecats.Model.Cat;

/**
 * Created by User on 07.04.2016.
 */
public class CatHeadlessFragment extends Fragment {

    private ArrayList<Cat> cats = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);

        return null;
    }

    public ArrayList<Cat> getCats() {
        return cats;
    }

    public int catsSize() {
        return cats.size();
    }

}
