package developer.essiorh.instaphotomaker;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eSSiorh
 * on 30.10.16
 */

public class User {

    @SerializedName("media")
    private Media media;

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}
