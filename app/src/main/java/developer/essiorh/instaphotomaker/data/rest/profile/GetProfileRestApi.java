package developer.essiorh.instaphotomaker.data.rest.profile;

import developer.essiorh.instaphotomaker.common.RestConst;
import developer.essiorh.instaphotomaker.data.rest.common.ServiceFactory;
import developer.essiorh.instaphotomaker.data.rest.profile.models.ProfileResponse;
import rx.Observable;

/**
 * Created by eSSiorh
 * on 26.02.17
 */

public class GetProfileRestApi implements IGetProfileRestApi {

    @Override
    public Observable<ProfileResponse> getProfile(String profile) {
        GetProfileService service = ServiceFactory.createRetrofitService(GetProfileService.class,
                RestConst.Api.BASE_URL);
        return service.getProfile(RestConst.Api.BASE_URL + profile + RestConst.Api.END_POINT);
    }
}
