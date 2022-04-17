package com.genspark.multiThread.primeChecker;

public class MathUtils {
	
	public static boolean isPrime_1(int number) {
		boolean isPrime = true;
		double sqrt = Math.sqrt(number);
		if (number == 0 || number == 1) {
		} else if (sqrt - ((int)sqrt) == 0) {
			isPrime = false;
		} else {
			int nSqrt = (int) sqrt;
			for (int i = 2; i <= nSqrt; ++i) {
				if (number % i == 0) {
					isPrime = false;
					break;
				}
			}
		}
		return isPrime;
	}
	
	public static boolean isPrime_2(int number) {
		boolean isPrime = true;
		for (int i = 2; i <= number / 2; ++i) {
			if (number % i == 0) {
				isPrime = false;
				break;
			}
		}
		return isPrime;
	}
	
	
//	@Override
//	public void run() {
//		boolean flag = false;
//		long prev = System.currentTimeMillis();
//		
//		double sqrt = Math.sqrt(number);
//		if (sqrt - ((int)sqrt) == 0) {
//			flag = false;
//		} else {
//			int nSqrt = (int)sqrt;
//			for (int i = 2; i <= nSqrt; ++i) {
//				if (number % i == 0) {
//					flag = true;
//					break;
//				}
//			}
//		}
//		long after = System.currentTimeMillis();
//		String message = String.format("%s: %d is %s (%d ms consumed)", 
//				this.getClass().getName(),
//				this.number,
//				flag ? "Not Prime" : "Prime",
//						(after - prev)	
//				);
//		System.out.println(message);
//	}

}
