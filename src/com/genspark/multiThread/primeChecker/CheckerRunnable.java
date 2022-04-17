package com.genspark.multiThread.primeChecker;

import java.util.ArrayList;
import java.util.List;

public class CheckerRunnable implements Runnable {

	private int number;

	public CheckerRunnable(int number) {
		super();
		this.number = number;
	}

	@Override
	public void run() {
		long prev = System.currentTimeMillis();
		List<Integer> listPrime = new ArrayList<Integer>();
		for (int num = 1; num <= number; num++) {
			boolean isPrime = MathUtils.isPrime_2(num);
			if (isPrime) {
				listPrime.add(num);
			}
		}
		long after = System.currentTimeMillis();
		String message = String.format("%s: %d ms consumed, Primes is %s", 
			this.getClass().getSimpleName(),
			(after - prev),
			listPrime
		);
		System.out.println(message);
	}
}
