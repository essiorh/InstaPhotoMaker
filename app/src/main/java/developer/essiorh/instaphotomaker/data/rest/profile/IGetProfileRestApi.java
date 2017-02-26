package developer.essiorh.instaphotomaker.data.rest.profile;

import rx.Observable;

/**
 * Created by eSSiorh
 * on 26.02.17
 */

public interface IGetProfileRestApi {
    Observable<ProfileResponse> getProfile(String profile);
}
