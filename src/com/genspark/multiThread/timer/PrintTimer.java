package com.genspark.multiThread.timer;

import java.util.TimerTask;

public class PrintTimer extends TimerTask {

	@Override
	public void run() {
		System.out.println("Time out ---");
	}
}
