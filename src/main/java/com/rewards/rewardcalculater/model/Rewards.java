package com.rewards.rewardcalculater.model;

public class Rewards {
    private long customerId;
	private long lastMonthRewards;
    private long lastSecondMonthRewards;
    private long lastThirdMonthRewards;
    private long totalRewards;
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getLastMonthRewards() {
		return lastMonthRewards;
	}
	public void setLastMonthRewards(long lastMonthRewards) {
		this.lastMonthRewards = lastMonthRewards;
	}
	public long getLastSecondMonthRewards() {
		return lastSecondMonthRewards;
	}
	public void setLastSecondMonthRewards(long lastSecondMonthRewards) {
		this.lastSecondMonthRewards = lastSecondMonthRewards;
	}
	public long getLastThirdMonthRewards() {
		return lastThirdMonthRewards;
	}
	public void setLastThirdMonthRewards(long lastThirdMonthRewards) {
		this.lastThirdMonthRewards = lastThirdMonthRewards;
	}
	public long getTotalRewards() {
		return totalRewards;
	}
	public void setTotalRewards(long totalRewards) {
		this.totalRewards = totalRewards;
	}
	
	
   
}
