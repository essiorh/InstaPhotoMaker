package developer.essiorh.instaphotomaker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "https://www.instagram.com/";
    public static final String END_POINT = "/?__a=1";
    public static final String FAKE = "fake";
    private SimpleDraweeView ivFresco;
    private EditText etNick;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivFresco = (SimpleDraweeView) findViewById(R.id.ivFresco);
        etNick = (EditText) findViewById(R.id.etNick);
        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
    }

    public void loadImage(View view) {
        String nick = etNick.getText().toString();
        if (TextUtils.isEmpty(nick.trim())) {
            Toast.makeText(this, "Введите не пустой ник", Toast.LENGTH_SHORT).show();
            return;
        }
        hideKeyboard(view);
        new DataLoaderAsyncTask().execute(nick.trim());
        pbLoading.setVisibility(View.VISIBLE);
        ivFresco.setVisibility(View.GONE);
    }

    private void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    class DataLoaderAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String stringBuilder = BASE_URL +
                    strings[0] +
                    END_POINT;
            String json = getJSON(stringBuilder);
            Log.d(TAG, "doInBackground: json = " + json);
            User user = getUserFromJson(json);
            if (user != null && user.getMedia() != null && user.getMedia().getNodesItemList() != null &&
                    user.getMedia().getNodesItemList().size() > 0) {
                Log.d(TAG, "doInBackground: user = " + user.getMedia().getNodesItemList().get(0).getThumbnailScr());
                return user.getMedia().getNodesItemList().get(0).getThumbnailScr();
            } else {
                return FAKE;
            }
        }

        @Override
        protected void onPostExecute(String url) {
            super.onPostExecute(url);
            pbLoading.setVisibility(View.GONE);
            ivFresco.setVisibility(View.VISIBLE);
            if (url.equals(FAKE)) {
                ivFresco.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,
                        R.drawable.love));
                Toast.makeText(MainActivity.this, "Аккаунт не найден, попробуй еще!", Toast.LENGTH_SHORT).show();
                return;
            }
            ivFresco.setImageURI(url);
            Toast.makeText(MainActivity.this, "Фотка загружена", Toast.LENGTH_SHORT).show();
        }
    }

    public User getUserFromJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        User user = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (!jsonObject.has("user")) {
                return null;
            }
            JSONObject jsonUser = jsonObject.getJSONObject("user");
            if (!jsonUser.has("media")) {
                return null;
            }
            JSONObject jsonMedia = jsonUser.getJSONObject("media");
            if (!jsonMedia.has("nodes")) {
                return null;
            }
            JSONArray nodesArray = jsonMedia.getJSONArray("nodes");
            List<NodesItem> nodesItemList = new ArrayList<>();
            for (int i = 0; i < nodesArray.length(); i++) {
                JSONObject jsonNode = nodesArray.getJSONObject(i);
                if (!jsonNode.has("thumbnail_src")) {
                    break;
                }
                NodesItem nodesItem = new NodesItem();
                String thumbnailSrc = jsonNode.getString("thumbnail_src");
                nodesItem.setThumbnailScr(thumbnailSrc);
                nodesItemList.add(nodesItem);
            }
            user = new User();
            Media media = new Media();
            media.setNodesItemList(nodesItemList);
            user.setMedia(media);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public String getJSON(String url) {
        HttpURLConnection c = null;
        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    private Bitmap getBitmap(Bitmap bitmap, Bitmap label) {
        Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(label, 50, 50, null);
        canvas.save();
        return bitmap;
    }

    private void saveBitmap(Bitmap bmp) {

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    + "/Filename.png");
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public Bitmap drawTextToBitmap(Context gContext,
                                   int gResId,
                                   String gText) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

        Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(61, 61, 61));
        // text size in pixels
        paint.setTextSize((int) (14 * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2;

        canvas.drawText(gText, x, y, paint);

        return bitmap;
    }
}
