package developer.essiorh.instaphotomaker.domain.profile;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import developer.essiorh.instaphotomaker.data.rest.profile.models.NodesItem;
import developer.essiorh.instaphotomaker.data.rest.profile.models.User;
import developer.essiorh.instaphotomaker.data.rest.profile.IGetProfileRestApi;
import developer.essiorh.instaphotomaker.data.rest.profile.models.ProfileResponse;
import developer.essiorh.instaphotomaker.domain.common.Interactor;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by eSSiorh
 * on 26.02.17
 */

public class ProfileInteractor extends Interactor<ProfileResponse> implements IProfileInteractor {

    IGetProfileRestApi restApi;

    public ProfileInteractor(IGetProfileRestApi restApi) {
        super();
        this.restApi = restApi;
    }

    @Override
    public void getProfile(final Subscriber<List<String>> parentSubscriber, String profileId) {
        addSubscription(parentSubscriber);
        restApi.getProfile(profileId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getComputationSubscriber(parentSubscriber));
    }

    @NonNull
    private Subscriber<ProfileResponse> getComputationSubscriber(
            final Subscriber<List<String>> subscriber) {
        return new Subscriber<ProfileResponse>() {
            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }

            @Override
            public void onNext(ProfileResponse profileResponse) {
                User user = profileResponse.getUser();
                if (user == null) {
                    return;
                }
                List<NodesItem> nodesItemList = user.getMedia().getNodesItemList();
                if (user.getMedia() != null &&
                        nodesItemList != null &&
                        nodesItemList.size() > 0) {
                    List<String> resultList = new ArrayList<>();
                    for (NodesItem item : nodesItemList) {
                        resultList.add(item.getThumbnailScr());
                    }
                    subscriber.onNext(resultList);
                }
            }
        };
    }
}
