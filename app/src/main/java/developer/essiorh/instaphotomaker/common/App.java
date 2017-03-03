package developer.essiorh.instaphotomaker.common;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by eSSiorh
 * on 30.10.16
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Fresco.initialize(this);
    }

    public static Context getContext() {
        return context;
    }
}
