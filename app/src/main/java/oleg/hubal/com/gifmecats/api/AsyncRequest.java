package oleg.hubal.com.gifmecats.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import oleg.hubal.com.gifmecats.Constants;
import oleg.hubal.com.gifmecats.Model.Cat;

/**
 * Created by User on 06.04.2016.
 */
public final class AsyncRequest<T> extends AsyncTask<String, Void, T> {

    private Class<T> classType;
    private TypeRequest typeRequest;
    private IResponseListener responseListener;
    private HashMap<String, Object> paramsRequest = new HashMap<>();
    private boolean isSuccess;

    public AsyncRequest<T> classType(final Class<T> classType) {
        this.classType = classType;
        return this;
    }

    public AsyncRequest<T> typeRequest(final TypeRequest typeRequest) {
        this.typeRequest = typeRequest;
        return this;
    }

    public AsyncRequest<T> addParam(final String key, final Object value) {
        this.paramsRequest.put(key, value);
        return this;
    }

    public AsyncRequest<T> responseListener(final IResponseListener<T> responseListener) {
        this.responseListener = responseListener;
        return this;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (responseListener != null)
            responseListener.onStart();
    }

    @Override
    protected T doInBackground(String... params) {
        final String requestUrl = params[0];
        String urlParams = "";

        boolean isFirstParameter = true;
        for (String key : paramsRequest.keySet()) {
            urlParams += (isFirstParameter ? "?" : "&") + key + "=" + paramsRequest.get(key);
            isFirstParameter = false;
        }

        try {
            return executeRequest(requestUrl, urlParams);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        if (responseListener != null)
            responseListener.onFinish(isSuccess, t);
    }

    private T executeRequest(final String requestUrl, final String requestParamsUrl) throws IOException {
        URL url = new URL(requestUrl + requestParamsUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setRequestMethod(typeRequest.name());
        connection.connect();

        int code = connection.getResponseCode();

        if (code == 200) {
            isSuccess = true;
            return parseResponse(connection.getInputStream());
        } else {
            isSuccess = false;
        }

        return null;
    }

    private T parseResponse(final InputStream inputStream) throws IOException {

        if (classType.getName().equals(Cat.class.getName())) {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder total = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                total.append(line);
            }

            return (T) getResponseCatFromString(total.toString());
        } else if (classType.getName().equals(Set.class.getName())) {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder total = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                total.append(line);
            }

            return (T) getResponseCategoriesFromString(total.toString());
        } else if (classType.getName().equals(Bitmap.class.getName())) {
            return (T) BitmapFactory.decodeStream(inputStream);
        }
        return null;
    }

    private Cat getResponseCatFromString(final String response) throws IOException {
        Cat cat = null;

        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlPullParserFactory.newPullParser();
            parser.setInput(new StringReader(response));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        cat = new Cat();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (cat != null) {
                            if (name.equals(Constants.KEY_URL))
                                cat.setUrl(parser.nextText());

                            if (name.equals(Constants.KEY_ID))
                                cat.setId(parser.nextText());
                        }
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return cat;
    }

    private Set<String> getResponseCategoriesFromString(final String response) throws IOException {
        Set<String> categories = null;

        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlPullParserFactory.newPullParser();
            parser.setInput(new StringReader(response));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        categories = new HashSet<>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equals(Constants.KEY_NAME))
                            categories.add(parser.nextText());
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return categories;
    }
}
