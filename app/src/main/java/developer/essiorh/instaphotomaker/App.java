package developer.essiorh.instaphotomaker;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by eSSiorh
 * on 30.10.16
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
