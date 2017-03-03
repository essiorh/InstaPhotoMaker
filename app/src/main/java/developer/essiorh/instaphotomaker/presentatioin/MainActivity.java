package developer.essiorh.instaphotomaker.presentatioin;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import developer.essiorh.instaphotomaker.R;

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
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContainer, MainFragment.getInstance(), MAIN_FRAGMENT)
                .commit();
    }

    @Override
    public void openDetailInfo(String url) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContainer, DetailFragment.getInstance(url), DETAIL_FRAGMENT)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .addToBackStack(null)
                .commit();

    }
}
