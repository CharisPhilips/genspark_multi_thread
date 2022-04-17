package com.genspark.multiThread.fruitMarket;

import java.util.List;

public class Simulation {
	
	private List<Farmer> farmers;
	private List<Customer> customers;
	
	public Simulation(List<Farmer> farmers, List<Customer> customers) {
		this.farmers = farmers;
		this.customers = customers;
	}

	public void startSimulate() {
		for (Farmer farmer : this.farmers) {
			new Thread(farmer).start();
		}
		for (Customer customer : this.customers) {
			new Thread(customer).start();
		}
	}
}
