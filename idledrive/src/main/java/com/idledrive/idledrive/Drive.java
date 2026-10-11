package com.idledrive.idledrive;

public class Drive {
    private String name;
    private long totalSpace;
    private long freeSpace;
    private double usagePercent;
    private String status;

    public Drive(String name, long totalSpace, long freeSpace, double usagePercent, String status) {
        this.name = name;
        this.totalSpace = totalSpace;
        this.freeSpace = freeSpace;
        this.usagePercent = usagePercent;
        this.status = status;
    }

    // Getters
    public String getName() { return name; }
    public long getTotalSpace() { return totalSpace; }
    public long getFreeSpace() { return freeSpace; }
    public double getUsagePercent() { return usagePercent; }
    public String getStatus() { return status; }

    // Setters (optional)
    public void setName(String name) { this.name = name; }
    public void setTotalSpace(long totalSpace) { this.totalSpace = totalSpace; }
    public void setFreeSpace(long freeSpace) { this.freeSpace = freeSpace; }
    public void setUsagePercent(double usagePercent) { this.usagePercent = usagePercent; }
    public void setStatus(String status) { this.status = status; }
}