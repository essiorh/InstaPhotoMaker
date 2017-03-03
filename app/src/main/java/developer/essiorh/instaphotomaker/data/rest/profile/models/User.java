package developer.essiorh.instaphotomaker.data.rest.profile.models;

import com.google.gson.annotations.SerializedName;

import developer.essiorh.instaphotomaker.common.RestConst;

/**
 * Created by eSSiorh
 * on 30.10.16
 */

public class User {

    @SerializedName(RestConst.ResponseFields.MEDIA)
    private Media media;

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}
