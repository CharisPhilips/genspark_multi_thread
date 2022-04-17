package com.genspark.multiThread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.genspark.multiThread.fruitMarket.Customer;
import com.genspark.multiThread.fruitMarket.Farmer;
import com.genspark.multiThread.fruitMarket.Market;
import com.genspark.multiThread.fruitMarket.Simulation;
import com.genspark.multiThread.primeChecker.CheckerRunnable;
import com.genspark.multiThread.primeChecker.CheckerThread;
import com.genspark.multiThread.timer.PrintTimer;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		main.testPrime();
		main.testTimer();
		main.testFruitMarket();
	}

	private void testPrime() {
		Thread t1 = new Thread(new CheckerRunnable(100000));
		t1.start();

		Thread t2 = new CheckerThread(100000);
		t2.start();
	}

	private void testTimer() {
		Timer timer = new Timer();
		TimerTask task = new PrintTimer();
		timer.scheduleAtFixedRate(task, 0, 1000);
	}
	
	private void testFruitMarket() {
		Market market = new Market(100); //capacity
		List<Farmer> farmers = new ArrayList<Farmer>(
			Arrays.asList(
				new Farmer("farmer", market, 1000, 10)
			)
		);
		List<Customer> customers = new ArrayList<Customer>(
			Arrays.asList(
				new Customer("customer1", market, 1000, 10),
				new Customer("customer2", market, 1000, 10)
			)
		);
		Simulation simulation = new Simulation(farmers, customers);
		simulation.startSimulate();
	}
}
