package developer.essiorh.instaphotomaker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import developer.essiorh.instaphotomaker.data.rest.profile.GetProfileRestApi;
import developer.essiorh.instaphotomaker.data.rest.profile.IGetProfileRestApi;
import developer.essiorh.instaphotomaker.domain.profile.IProfileInteractor;
import developer.essiorh.instaphotomaker.domain.profile.ProfileInteractor;
import rx.Subscriber;

/**
 * Created by eSSiorh
 * on 30.10.16
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
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
        pbLoading.setVisibility(View.VISIBLE);
        ivFresco.setVisibility(View.GONE);
        IGetProfileRestApi restApi = new GetProfileRestApi();
        IProfileInteractor interactor = new ProfileInteractor(restApi);
        interactor.getProfile(new Subscriber<List<String>>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted() called");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onNext(List<String> strings) {
                Log.d(TAG, "onNext() called with: strings = [" + strings + "]");
                if (strings == null || strings.size() == 0) {
                    ivFresco.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,
                            R.drawable.love));
                    Toast.makeText(MainActivity.this, "Аккаунт не найден, попробуй еще!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                pbLoading.setVisibility(View.GONE);
                ivFresco.setVisibility(View.VISIBLE);
                ivFresco.setImageURI(strings.get(0));
                Toast.makeText(MainActivity.this, "Фотка загружена", Toast.LENGTH_SHORT).show();
            }
        }, "ESSIORH999");
    }

    private void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
