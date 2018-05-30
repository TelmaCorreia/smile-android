package com.thesis.smile.iota.responses;

public class NodeInfoResponse extends ApiResponse {

    private final String appName;
    private final String appVersion;
    private final String jreVersion;
    private final int jreAvailableProcessors;
    private final long jreFreeMemory;
    private final long jreMaxMemory;
    private final long jreTotalMemory;
    private final String latestMilestone;
    private final int latestMilestoneIndex;
    private final String latestSolidSubtangleMilestone;
    private final int latestSolidSubtangleMilestoneIndex;
    private final int neighbors;
    private final int packetsQueueSize;
    private final long time;
    private final int tips;
    private final int transactionsToRequest;


    public NodeInfoResponse(jota.dto.response.GetNodeInfoResponse apiResponse) {
        appName = apiResponse.getAppName();
        appVersion = apiResponse.getAppVersion();
        jreVersion = apiResponse.getJreVersion();
        jreAvailableProcessors = apiResponse.getJreAvailableProcessors();
        jreFreeMemory = apiResponse.getJreFreeMemory();
        jreMaxMemory = apiResponse.getJreMaxMemory();
        jreTotalMemory = apiResponse.getJreTotalMemory();
        latestMilestone = apiResponse.getLatestMilestone();
        latestMilestoneIndex = apiResponse.getLatestMilestoneIndex();
        latestSolidSubtangleMilestone = apiResponse.getLatestSolidSubtangleMilestone();
        latestSolidSubtangleMilestoneIndex = apiResponse.getLatestSolidSubtangleMilestoneIndex();
        neighbors = apiResponse.getNeighbors();
        packetsQueueSize = apiResponse.getPacketsQueueSize();
        time = apiResponse.getTime();
        tips = apiResponse.getTips();
        transactionsToRequest = apiResponse.getTransactionsToRequest();
        setDuration(apiResponse.getDuration());
    }

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getJreVersion() {
        return jreVersion;
    }

    public Integer getJreAvailableProcessors() {
        return jreAvailableProcessors;
    }

    public long getJreFreeMemory() {
        return jreFreeMemory;
    }

    public long getJreMaxMemory() {
        return jreMaxMemory;
    }

    public long getJreTotalMemory() {
        return jreTotalMemory;
    }

    public String getLatestMilestone() {
        return latestMilestone;
    }

    public int getLatestMilestoneIndex() {
        return latestMilestoneIndex;
    }

    public String getLatestSolidSubtangleMilestone() {
        return latestSolidSubtangleMilestone;
    }

    public int getLatestSolidSubtangleMilestoneIndex() {
        return latestSolidSubtangleMilestoneIndex;
    }

    public int getNeighbors() {
        return neighbors;
    }

    public int getPacketsQueueSize() {
        return packetsQueueSize;
    }

    public Long getTime() {
        return time;
    }

    public int getTips() {
        return tips;
    }

    public int getTransactionsToRequest() {
        return transactionsToRequest;
    }
}