package developer.essiorh.instaphotomaker.domain.profile;

import java.util.List;

import developer.essiorh.instaphotomaker.domain.common.IInteractor;
import rx.Subscriber;

/**
 * Created by eSSiorh
 * on 26.02.17
 */

public interface IProfileInteractor extends IInteractor {
    void getProfile(Subscriber<List<String>> subscriber, String profileId);
}
