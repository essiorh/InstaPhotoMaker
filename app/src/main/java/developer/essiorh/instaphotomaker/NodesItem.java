package developer.essiorh.instaphotomaker;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eSSiorh
 * on 30.10.16
 */

public class NodesItem {

    @SerializedName("thumbnail_src")
    private String thumbnailScr;

    public String getThumbnailScr() {
        return thumbnailScr;
    }

    public void setThumbnailScr(String thumbnailScr) {
        this.thumbnailScr = thumbnailScr;
    }
}
