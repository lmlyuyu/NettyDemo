package com.maolin.filereader;

public class QuotaRecord {

    public QuotaRecord(String id, String groupId, Double quota) {
        this.id = id;
        this.groupId = groupId;
        this.quota = quota;
    }

    private String id ;

    private String groupId;

    private Double quota;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Double getQuota() {
        return quota;
    }

    public void setQuota(Double quota) {
        this.quota = quota;
    }

    @Override
    public String toString() {
        return groupId + ", " + id + ", " + quota;
    }
}
