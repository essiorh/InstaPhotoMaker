package developer.essiorh.instaphotomaker.presentatioin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import developer.essiorh.instaphotomaker.R;

/**
 * Created by eSSiorh
 * on 28.02.17
 */

public class DetailFragment extends Fragment {

    public static final String ARG_URL = "ARG_URL";
    private SimpleDraweeView ivFresco;

    public static DetailFragment getInstance(String url) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_detail, null);
        ivFresco = (SimpleDraweeView) view.findViewById(R.id.ivFresco);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = getArguments().getString(ARG_URL);
        ivFresco.setImageURI(url);
    }
}
