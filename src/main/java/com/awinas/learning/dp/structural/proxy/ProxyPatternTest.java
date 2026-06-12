package com.awinas.learning.dp.structural.proxy;

public class ProxyPatternTest {

	public static void main(String[] args) {
		CommandExecutor admin = new CommandExecutorProxy("Awinas", "Awi2719");
		CommandExecutor nonAdmin = new CommandExecutorProxy("Jey", "Jey20");
		try {
			admin.runCommand("ls -ltr");
			admin.runCommand(" rm -rf abc.pdf");
			
			nonAdmin.runCommand("rm -rf abc.pdf");
		} catch (Exception e) {
			System.out.println("Exception Message::" + e.getMessage());
		}

	}

}