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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by eSSiorh
 * on 30.10.16
 */

public class MainActivity extends AppCompatActivity implements MainRouter {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String MAIN_FRAGMENT = "MAIN_FRAGMENT";
    public static final String DETAIL_FRAGMENT = "DETAIL_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flContainer, new MainFragment(), MAIN_FRAGMENT)
                .commit();
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

    @Override
    public void openDetailInfo(String url) {
        Fragment mainFragment = getSupportFragmentManager().findFragmentByTag(MAIN_FRAGMENT);
        Fragment detailFragment = getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT);
        if (detailFragment == null) {
            detailFragment = DetailFragment.getInstance(url);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flContainer, detailFragment, DETAIL_FRAGMENT)
                .hide(mainFragment)
                .show(detailFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .addToBackStack(null)
                .commit();
    }
}
