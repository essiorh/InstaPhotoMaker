package developer.essiorh.instaphotomaker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import developer.essiorh.instaphotomaker.data.rest.profile.GetProfileRestApi;
import developer.essiorh.instaphotomaker.data.rest.profile.IGetProfileRestApi;
import developer.essiorh.instaphotomaker.domain.profile.IProfileInteractor;
import developer.essiorh.instaphotomaker.domain.profile.ProfileInteractor;
import rx.Subscriber;

/**
 * Created by eSSiorh
 * on 28.02.17
 */

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private RecyclerView rvList;
    private EditText etNick;
    private ProgressBar pbLoading;
    private Button btnLoadImages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view =
                LayoutInflater.from(getContext()).inflate(R.layout.fragment_main, null);
        etNick = (EditText) view.findViewById(R.id.etNick);
        pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);
        rvList = (RecyclerView) view.findViewById(R.id.rvList);
        btnLoadImages = (Button) view.findViewById(R.id.btnLoadImages);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        if (!(getActivity() instanceof MainRouter)) {
            throw new IllegalStateException("Activity must be implement MainRouter interface");
        }
        ListAdapter listAdapter = new ListAdapter(new ArrayList<String>(),
                (MainRouter) getActivity());
        rvList.setAdapter(listAdapter);
        btnLoadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage(v);
            }
        });
    }

    public void loadImage(View view) {
        String nick = etNick.getText().toString();
        if (TextUtils.isEmpty(nick.trim())) {
            Toast.makeText(getContext(), R.string.load_image_message, Toast.LENGTH_SHORT).show();
            return;
        }
        hideKeyboard(view);
        pbLoading.setVisibility(View.VISIBLE);
        rvList.setVisibility(View.GONE);
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
//                    ivFresco.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,
//                            R.drawable.love));
                    Toast.makeText(getContext(), "Аккаунт не найден, попробуй еще!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                pbLoading.setVisibility(View.GONE);
                rvList.setVisibility(View.VISIBLE);
                if (rvList.getAdapter() != null) {
                    ListAdapter listAdapter = (ListAdapter) rvList.getAdapter();
                    listAdapter.updateData(strings);
                    Toast.makeText(getContext(), "Фотки загружены", Toast.LENGTH_SHORT).show();
                }
            }
        }, nick.trim());
    }

    private void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
