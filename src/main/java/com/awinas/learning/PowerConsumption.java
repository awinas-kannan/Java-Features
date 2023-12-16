package com.awinas.learning;

import java.util.LinkedHashMap;
import java.util.Map;

public class PowerConsumption {
	String date;
	int machineNo;
	int consumptionInHp;

	public PowerConsumption(String date, int machineNo, int consumptionInHp) {
		this.date = date;
		this.machineNo = machineNo;
		this.consumptionInHp = consumptionInHp;
	}

	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append(date);
		str.append("\t\t");
		str.append(String.valueOf(machineNo));
		str.append("\t\t");
		str.append(String.valueOf(consumptionInHp));
		return str.toString();
	}

	public static void main(String ar[]) {
		PowerConsumption p[] = new PowerConsumption[7];
		p[0] = new PowerConsumption("10.05.08", 10, 100);
		p[1] = new PowerConsumption("10.05.08", 11, 120);
		p[2] = new PowerConsumption("10.05.08", 12, 125);
		p[3] = new PowerConsumption("11.05.08", 10, 145);
		p[4] = new PowerConsumption("11.05.08", 11, 155);
		p[5] = new PowerConsumption("12.05.08", 12, 165);
		p[6] = new PowerConsumption("12.05.08", 10, 165);
		System.out.println("Date\t\tMachine\t\tConsumption");
		for (int i = 0; i < p.length; i++) {
			System.out.println(p[i]);
		}

		Map<String, Integer> dateAgg = new LinkedHashMap<String, Integer>();

		for (int i = 0; i < p.length; i++) {

			if (dateAgg.get(p[i].date) != null) {
				dateAgg.put(p[i].date, dateAgg.get(p[i].date) + p[i].consumptionInHp);
			} else {
				dateAgg.put(p[i].date, p[i].consumptionInHp);
			}
		}

		System.out.println(dateAgg);
		int totalHPBasedonDate = 0;
		for (String key : dateAgg.keySet()) {
			totalHPBasedonDate = totalHPBasedonDate + dateAgg.get(key);
		}
		System.out.println("totalHPBasedonDate " + totalHPBasedonDate);

		Map<Integer, Integer> hpAgg = new LinkedHashMap<Integer, Integer>();

		for (int i = 0; i < p.length; i++) {

			if (hpAgg.get(p[i].machineNo) != null) {
				hpAgg.put(p[i].machineNo, hpAgg.get(p[i].machineNo) + p[i].consumptionInHp);
			} else {
				hpAgg.put(p[i].machineNo, p[i].consumptionInHp);
			}
		}

		System.out.println(hpAgg);
		int totalHPBasedonMac = 0;
		for (Integer key : hpAgg.keySet()) {
			totalHPBasedonMac = totalHPBasedonMac + hpAgg.get(key);
		}
		System.out.println("totalHPBasedonMac " + totalHPBasedonMac);

		System.out.println("Aggregation :::");

		System.out.print("Machine/Date");
		
		for (String key : dateAgg.keySet()) {
			System.out.print("\t\t");
			System.out.print(key);
		}
		System.out.print("\t\t");
		System.out.println("Total");
		for (Integer key : hpAgg.keySet()) {
			System.out.print(key);
			for (String dates : dateAgg.keySet()) {
				boolean isPrinted = false;
				for (int i = 0; i < p.length; i++) {
					if (p[i].date == dates && p[i].machineNo == key) {
						isPrinted= true;
						System.out.print("\t\t");
						System.out.print(p[i].consumptionInHp);
					}
				}
				if(isPrinted == false) {
					System.out.print("\t\t");
					System.out.print("0");
				}
			}
			System.out.print("\t\t");
			System.out.println(hpAgg.get(key));
		}
		System.out.print("Total");
		for (String key : dateAgg.keySet()) {
			System.out.print("\t\t");
			System.out.print(dateAgg.get(key));
		}
		System.out.print("\t\t");
		System.out.println(totalHPBasedonMac);

	}
}
