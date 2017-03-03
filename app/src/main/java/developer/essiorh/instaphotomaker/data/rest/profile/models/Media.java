package developer.essiorh.instaphotomaker.data.rest.profile.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import developer.essiorh.instaphotomaker.common.RestConst;

/**
 * Created by eSSiorh
 * on 30.10.16
 */

public class Media {

    @SerializedName(RestConst.ResponseFields.NODES)
    private List<NodesItem> nodesItemList;

    public List<NodesItem> getNodesItemList() {
        return nodesItemList;
    }

    public void setNodesItemList(List<NodesItem> nodesItemList) {
        this.nodesItemList = nodesItemList;
    }
}
