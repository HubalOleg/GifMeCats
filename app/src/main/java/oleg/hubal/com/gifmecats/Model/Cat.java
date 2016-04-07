package oleg.hubal.com.gifmecats.Model;

import android.graphics.Bitmap;

import oleg.hubal.com.gifmecats.Constants;

/**
 * Created by User on 07.04.2016.
 */
public class Cat {

    private String url;
    private String id;
    private Bitmap image;

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
