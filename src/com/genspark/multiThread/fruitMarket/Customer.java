package com.genspark.multiThread.fruitMarket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Customer implements Runnable {

	private String name;
	private int demandCycle;
	private int demandLimit;
	private Market market;
	private List<Product> demandFruits;
	
	public Customer(String name, Market market, int demandCycle, int demandLimit) {
		this.name = name;
		this.market = market;
		this.demandCycle = demandCycle;
		this.demandLimit = demandLimit;
		this.demandFruits = new ArrayList<Product>();
	}
	
	public String getCustomerName() {
		return name;
	}
	
	public List<Product> getDemandFruits() {
		return demandFruits;
	}
	
	@Override
	public void run() {
		try {
			synchronized (this.market) {
				do {
					int nextWaitTime = ThreadLocalRandom.current().nextInt(demandCycle / 2, demandCycle);
					this.market.wait(nextWaitTime);
					
					Fruits fruitsType = Fruits.values()[ThreadLocalRandom.current().nextInt(Fruits.values().length)];
					int buyCount = ThreadLocalRandom.current().nextInt(demandLimit / 2, demandLimit);
					
					System.out.println("=============================Customer Action(<)=============================");
					
					if (demandFruits.isEmpty()) {
						Product toBuy = new Product(fruitsType, buyCount);
						demandFruits.add(toBuy);
					}
					boolean result = this.market.tryBuy(this.name, demandFruits);
					if (!result) {
						System.out.println(String.format("Customer(%s) is failed to buy", this.name));
						System.out.println(this);
					}
					if (!result) {
						System.out.println(String.format("Customer(%s) is successed to buy", this.name));
						this.market.wait();
					} else {
						this.market.notify();
					}
					System.out.println("=============================Customer Action(>)=============================\n");
				} while (true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		String out = String.format("\nCustomer(%s) status is following:\n", this.name);
		
		for (Product fruit: demandFruits) {
			out += String.format("%d of %s.\n", fruit.getNumber(), fruit.getFruitType().toString());
		}
		out+="\n";
		return out;
	}
}
