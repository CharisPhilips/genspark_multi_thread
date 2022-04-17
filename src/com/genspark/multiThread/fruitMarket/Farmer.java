package com.genspark.multiThread.fruitMarket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Farmer implements Runnable {

	private String name;
	private int productCycle;
	private int productLimit;
	private Market market;
	private List<Product> remainFruits;
	
	public Farmer(String name, Market market, int productCycle, int productLimit) {
		this.name = name;
		this.market = market;
		this.productCycle = productCycle;
		this.productLimit = productLimit;
		this.remainFruits = new ArrayList<Product>();
	}

	public String getFarmerName() {
		return name;
	}

	public List<Product> getRemainFruits() {
		return remainFruits;
	}
	
	@Override
	public void run() {
		try {
			synchronized (this.market) {
				do {
					int nextWaitTime = ThreadLocalRandom.current().nextInt(productCycle / 2, productCycle);
					this.market.wait(nextWaitTime);

					Fruits fruitsType = Fruits.values()[ThreadLocalRandom.current().nextInt(Fruits.values().length)];
					int sellCount = ThreadLocalRandom.current().nextInt(productLimit / 2, productLimit);

					System.out.println("=============================Farmer Action(<)=============================");
					
					if (remainFruits.isEmpty()) {
						Product toSell = new Product(fruitsType, sellCount);
						remainFruits.add(toSell);
					}
					boolean result = this.market.trySell(this.name, remainFruits);
					if (!result) {
						System.out.println(String.format("Farmer(%s) is failed to sell", this.name));
						System.out.println(this);
					} else {
						System.out.println(String.format("Farmer(%s) is successed to sell", this.name));
						
					}
					if (!result) {
						this.market.wait();
					} else {
						this.market.notify();
					}
					System.out.println("=============================Farmer Action(>)=============================\n");
				} while (true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		String out = String.format("\nFarmer(%s) status is following:\n", this.name);
		
		for (Product fruit: remainFruits) {
			out += String.format("%d of %s.\n", fruit.getNumber(), fruit.getFruitType().toString());
		}
		out+="\n";
		return out;
	}

}
