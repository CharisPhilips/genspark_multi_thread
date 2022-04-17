package com.genspark.multiThread.fruitMarket;

public class Product {
	
	//Members
	private Fruits fruitType;
	private int number;

	//Constructors
	public Product(Fruits fruitType, int number) {
		super();
		this.fruitType = fruitType;
		this.number = number;
	}
	
	//Getter/Setter
	public Fruits getFruitType() {
		return fruitType;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
}
