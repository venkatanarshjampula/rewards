package com.rewards.rewardcalculater.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rewards.rewardcalculater.constants.Constants;
import com.rewards.rewardcalculater.model.Customer;
import com.rewards.rewardcalculater.model.Rewards;
import com.rewards.rewardcalculater.model.Transaction;
import com.rewards.rewardcalculater.util.DataUtil;

@Service
public class RewardServiceImpl {


	public 	Customer getCustomerByCustomerId(int customerId) {
		List<Customer> customers=DataUtil.getAllCustomers();
		if(customerId>customers.size())
			return null;
		return customers.get(customerId-1);
	}


	public Rewards getPointsByCustomerId(Long customerId) {

		Timestamp lastMonthTimestamp = getTimeStamp(Constants.daysInMonth);
		Timestamp lastSecondMonthTimestamp = getTimeStamp(2*Constants.daysInMonth);
		Timestamp lastThirdMonthTimestamp = getTimeStamp(3*Constants.daysInMonth);

		List<Transaction> lastMonthTransactions = getDataByCustomerIdAndTransactionDateBetween(customerId, lastMonthTimestamp, Timestamp.from(Instant.now()));
		List<Transaction> lastSecondMonthTransactions = getDataByCustomerIdAndTransactionDateBetween(customerId, lastSecondMonthTimestamp, lastMonthTimestamp);
		List<Transaction> lastThirdMonthTransactions = getDataByCustomerIdAndTransactionDateBetween(customerId, lastThirdMonthTimestamp,lastSecondMonthTimestamp);

		Long lastMonthRewardPoints = getPointsPerMonth(lastMonthTransactions);
		Long lastSecondMonthRewardPoints = getPointsPerMonth(lastSecondMonthTransactions);
		Long lastThirdMonthRewardPoints = getPointsPerMonth(lastThirdMonthTransactions);

		Rewards rewards = new Rewards();
		rewards.setCustomerId(customerId);
		rewards.setLastMonthRewards(lastMonthRewardPoints);
		rewards.setLastSecondMonthRewards(lastSecondMonthRewardPoints);
		rewards.setLastThirdMonthRewards(lastThirdMonthRewardPoints);
		rewards.setTotalRewards(lastMonthRewardPoints + lastSecondMonthRewardPoints + lastThirdMonthRewardPoints);

		return rewards;

	}

	private List<Transaction> getDataByCustomerIdAndTransactionDateBetween(Long customerId,
			Timestamp lastMonthTimestamp, Timestamp from) {

		List<Transaction> transactionsList =DataUtil.getAllTransactionData();
		List<Transaction> tList=new ArrayList<Transaction>();
		for (Transaction transaction : transactionsList) {
			if(transaction.getCustomerId()==customerId&&(transaction.getTransactionDate().getTime()>=lastMonthTimestamp.getTime()&&transaction.getTransactionDate().getTime()<=from.getTime()))

			{
				tList.add(transaction);
			}
		}

		return tList;
	}



	private Long getPointsPerMonth(List<Transaction> transactions) {

		long totalPoints=0l;
		for (Transaction transaction : transactions) {
			Long points=calculatePoints(transaction);
			totalPoints=+points;
		}
		return totalPoints;

		//return transactions.stream().map(transaction -> calculatePoints(transaction)).collect(Collectors.summingLong(r -> r.longValue()));
	}

	private Long calculatePoints(Transaction t) {
		if (t.getTransactionAmount() > Constants.firstReward && t.getTransactionAmount() <= Constants.secondReward) {
			return Math.round(t.getTransactionAmount() - Constants.firstReward);
		} else if (t.getTransactionAmount() > Constants.secondReward) {
			return Math.round(t.getTransactionAmount() - Constants.secondReward) * 2
					+ (Constants.secondReward - Constants.firstReward);
		} else
			return 0l;

	}

	public Timestamp getTimeStamp(int days) {
		return Timestamp.valueOf(LocalDateTime.now().minusDays(days));
	}



}
