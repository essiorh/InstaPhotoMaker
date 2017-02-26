package developer.essiorh.instaphotomaker;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eSSiorh
 * on 30.10.16
 */

public class Media {

    @SerializedName("nodes")
    private List<NodesItem> nodesItemList;

    public List<NodesItem> getNodesItemList() {
        return nodesItemList;
    }

    public void setNodesItemList(List<NodesItem> nodesItemList) {
        this.nodesItemList = nodesItemList;
    }
}
