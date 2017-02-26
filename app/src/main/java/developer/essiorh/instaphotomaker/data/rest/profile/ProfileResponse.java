package developer.essiorh.instaphotomaker.data.rest.profile;

import com.google.gson.annotations.SerializedName;

import developer.essiorh.instaphotomaker.User;

/**
 * Created by eSSiorh
 * on 26.02.17
 */

public class ProfileResponse {

    @SerializedName("user")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
