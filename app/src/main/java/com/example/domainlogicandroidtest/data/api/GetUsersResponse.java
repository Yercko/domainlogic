package com.example.domainlogicandroidtest.data.api;



import com.example.domainlogicandroidtest.data.api.model.UserApiEntry;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GetUsersResponse {
    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<UserApiEntry> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<UserApiEntry> getItems() {
        return items;
    }

    public void setItems(List<UserApiEntry> items) {
        this.items = items;
    }
}
