package developer.essiorh.instaphotomaker.data.rest.profile.models;

import com.google.gson.annotations.SerializedName;

import developer.essiorh.instaphotomaker.common.RestConst;

/**
 * Created by eSSiorh
 * on 26.02.17
 */

public class ProfileResponse {

    @SerializedName(RestConst.ResponseFields.USER)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
