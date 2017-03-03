package developer.essiorh.instaphotomaker.data.rest.profile.models;

import com.google.gson.annotations.SerializedName;

import developer.essiorh.instaphotomaker.common.RestConst;

/**
 * Created by eSSiorh
 * on 30.10.16
 */

public class NodesItem {

    @SerializedName(RestConst.ResponseFields.THUMBNAIL_SRC)
    private String thumbnailScr;

    public String getThumbnailScr() {
        return thumbnailScr;
    }

    public void setThumbnailScr(String thumbnailScr) {
        this.thumbnailScr = thumbnailScr;
    }
}
