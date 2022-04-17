package com.genspark.multiThread.fruitMarket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Market {

	//Members
	private int fruitsCapacity;
	private Map<Fruits, Integer> fruits = new HashMap<Fruits, Integer>();
	
	//Contructor
	public Market(int capacity) {
		if (capacity > 0) {
			this.fruitsCapacity = capacity;
		} else {
			throw new IllegalArgumentException("This fruit's capacity have to greater than zero");
		}
	}
	
	// check if their is no place or its full //
	private boolean isFull() {
		return getFruitCount() >= this.fruitsCapacity;
	}
	
	private boolean isEmpty() {
		return fruits.isEmpty();
	}
	
	private int getFruitCount() {
		int count = 0;
		for(Map.Entry<Fruits, Integer> entry : fruits.entrySet()) {
			count +=entry.getValue();
		}
		return count;
	}

	public synchronized boolean trySell(String name, List<Product> remainFruits) {
		
		Product sell = null;
		do {
			sell = remainFruits.size() > 0 ? remainFruits.get(0) : null;
			if (sell == null) {
				break;
			}
			System.out.println(String.format("Farmer(%s) is trying to sell %d of %s", name, sell.getNumber(), sell.getFruitType().toString()));
			
			if (isFull()) {
				System.out.println(" -> Market store is full, need to wait have some empty seet");
				return false;
			}
			
			if (this.getFruitCount() + sell.getNumber() < fruitsCapacity) {
				Integer numberOnStore = fruits.get(sell.getFruitType());
				if (numberOnStore == null) {
					numberOnStore = sell.getNumber();
				} else {
					numberOnStore += sell.getNumber();
				}
				fruits.put(sell.getFruitType(), numberOnStore);
				remainFruits.remove(0);
				
				System.out.println(String.format(" -> Market stored %d of %s newly from farmer", sell.getNumber(), sell.getFruitType().toString()));
				System.out.println(this);								
				
			} else {
				Integer numberCanSell = Math.max(0, fruitsCapacity - this.getFruitCount());
				
				Integer numberOnStore = fruits.get(sell.getFruitType());
				if (numberOnStore == null) {
					numberOnStore = 0;
				}
				
				fruits.put(sell.getFruitType(), numberOnStore + numberCanSell);
				sell.setNumber(sell.getNumber() - numberCanSell);
				
				System.out.println(String.format(" -> Market stored %d of %s newly from farmer", numberCanSell, sell.getFruitType().toString()));
				System.out.println(this);
				
				return false;
			}
		} while (sell != null);
		return true;
	}
	
	public synchronized boolean tryBuy(String name, List<Product> demandFruits) {
		Product buy = null;
		do {
			buy = demandFruits.size() > 0 ? demandFruits.get(0) : null;
			if (buy == null) {
				break;
			}
			System.out.println(String.format("Customer(%s) is trying to buy %d of %s", name, buy.getNumber(), buy.getFruitType().toString()));
			if (isEmpty()) {
				System.out.println(" -> Market store is empty");
				return false;
			}
			
			Integer numberOnStore = fruits.get(buy.getFruitType());
			
			if (numberOnStore != null && numberOnStore >= buy.getNumber()) {
				fruits.put(buy.getFruitType(), numberOnStore - buy.getNumber());
				System.out.println(String.format(" -> Market sells %d of %s to customer", buy.getNumber(), buy.getFruitType().toString()));
				System.out.println(this);
				
			} else {
				if (numberOnStore == null) {
					numberOnStore = 0;
				}
				System.out.println(String.format(" -> Market sells %d of %s to customer", numberOnStore, buy.getFruitType().toString()));
				System.out.println(this);
				fruits.remove(buy.getFruitType());
				return false;
			}
		} while (buy != null);
		return true;
	}	
	
	@Override
	public String toString() {
		String out = "\nMarket status is following:\n";
		
		for (Map.Entry<Fruits, Integer> fruit : fruits.entrySet()) {
			out += String.format("%d of %s.\n", fruit.getValue(), fruit.getKey().toString());
		}
		out+="\n";
		return out;
	}


}
