package developer.essiorh.instaphotomaker.data.rest.profile;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by eSSiorh
 * on 26.02.17
 */

public interface GetProfileService {
    @GET
    Observable<ProfileResponse> getProfile(@Url String url);
}
