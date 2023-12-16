package com.awinas.learning;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.IntStream;

public class Test {

	public static void main(String[] args) {

//		// L1
//
//		// List l2 = l1.stream().filter((e) -> e%2!=0 ).forEach((e) - > sysout(e))
//
//		// l1 l2 int array list
//		// fuc to print odd num from list
//
//		Runnable r1= new Runnable() {
//
//			@Override
//			public void run() {
//				
//					//l1.stream().filter((e) -> e%2!=0 ).forEach((e) -> sysout(e))	
//				
//				
//			}
//			
//		}
//		
//		Runnable r2= new Runnable() {
//			@Override
//			public void run() {
//				//l2.stream().filter((e) -> e%2!=0 ).forEach((e) -> sysout(e))	
//			}
//		}
//		
////		Thread t1 = new Thread(r1);
////		Thread t2 = new Thread(r2);
////		
////		t1.start();
////		t2.start();
//
//		// List<Runnable> task = new
//		ExecutorService ec = Executors.newFixedThreadPool(2);
//		ArrayList<Runnable> task = new ArrayList<>();
//		task.add(r1);
//		task.add(r2);
//	//	ec.invokeAll(task );

//		String a = "123";
//		Integer newInt = Integer.valueOf(a);
//		System.out.println(newInt);
//		Integer out = 0;
//		for (char c : a.toCharArray()) {
//			int val = (int) c - 48;
//			out = (out * 10) + val;
//		}
//		System.out.println(out);

		IntStream.range(1, 10).forEach(System.out::println);

		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println(PalletType.USER.name().equals("USER"));
		System.out.println(PalletType.USER.name().equals("USR"));

		System.out.println("************* EXP Date **************");
		final String LOCAL_DATE_TIME_PATTERN_EXP = "MM/dd/yyyy";
		String date = "08/23/2020";
		try {
			DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN_EXP);
			System.out.println(LocalDate.parse(date, formatterDateTime).atStartOfDay());
			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN_EXP);
			System.out.println(LocalDate.parse(date, formatterDate));
		} catch (Exception e) {
			System.out.println("Exception while converting date  " + date);
		}

//		Map<String, String> map = new HashMap<>();
//		for (int i = 1; i < 5000000; i++) {
//			map.put("aw" + i, "aw" + i);
//		}
//		System.out.println(map.size());

		final String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
		date = "2013-10-09 23:59:10";
		try {
			DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN);
			System.out.println(LocalDateTime.parse(date, formatterDateTime));
			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN);
			System.out.println(LocalDate.parse(date, formatterDate));
		} catch (Exception e) {
			System.out.println("Exception while converting date  " + date);
		}

		System.out.println("************* GTIN CONVERSION **************");

		try {

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}

		System.out.println("*******************************************");
		// DC Pallet Ids
		// OYI
		System.out.println("OYI*******************************************");
		System.out.println("47431616684991".length());
		System.out.println("4743161668513200".length());
		System.out.println("Receivings*******************************************");
		System.out.println("4743161667004800".length());
		System.out.println("47431616670184".length());

		System.out.println("*******************************************");

		System.out.println("01102010605700182483".length());
		String smartPalletId = "01102010605700182483";

		System.out.println("******************addLeadingZeroes*************************");
		System.out.println(addLeadingZeroes("123", 12));
		System.out.println(addLeadingZeroes("123", 5));
		System.out.println(addLeadingZeroes("123", 8));
		System.out.println(addLeadingZeroes("12345678", 8));
		System.out.println("******************getPalletIdFromSmartPalletid*************************");
		System.out.println(getPalletIdFromSmartPalletid("12345", "4743"));

		System.out.println("******************getPrintPalletIdFromPalletid*************************");
		System.out.println(getPrintPalletIdFromPalletid(getPalletIdFromSmartPalletid("12345", "4743")));

		System.out.println("******************OPTIONAL*************************");

		String errorMessage = "message";

		if (Optional.ofNullable(errorMessage).isPresent() && errorMessage.contains("errorEnum")) {
			System.out.println("Print");
		}
		errorMessage = "";
		if (Optional.ofNullable(errorMessage).isPresent() && errorMessage.contains("errorEnum")) {
			System.out.println("Print");
		}
		errorMessage = " ";
		if (Optional.ofNullable(errorMessage).isPresent() && errorMessage.contains("errorEnum")) {
			System.out.println("Print");
		}
		errorMessage = "errorEnum";
		if (Optional.ofNullable(errorMessage).isPresent() && errorMessage.contains("errorEnum")) {
			System.out.println("Print");
		}
		errorMessage = nullVal();
		if (Optional.ofNullable(errorMessage).isPresent() && errorMessage.contains("errorEnum")) {
			System.out.println("Print");
		}else {
			System.out.println("null");
		}

		errorMessage ="";
		System.out.println(errorMessage.contains(" "));
	}
	
	private static String nullVal() {
		return null;
	}

	public static String getPrintPalletIdFromPalletid(String smartPalletId) {

		return smartPalletId.substring(smartPalletId.length() - 8, smartPalletId.length());
		// if(palletId.length() >= 8)

	}

	public static String getPalletIdFromSmartPalletid(String smartPalletId, String clubNbr) {
		StringBuilder palletId = new StringBuilder();
		return palletId.append("0").append(clubNbr).append("010").append(addLeadingZeroes(smartPalletId, 12))
				.toString();
		// if(palletId.length() >= 8)

	}

	public static String addLeadingZeroes(String smartPalletId, int totalLength) {
		System.out.println("smartPalletId " + smartPalletId);
		System.out.println("totalLength " + totalLength);
		StringBuilder palletId = new StringBuilder();
		IntStream.range(0, totalLength - smartPalletId.length()).forEach((i) -> {
			palletId.append(0);
		});
		return palletId.append(smartPalletId).toString();
	}

}

enum PalletType {
	USER, DC
}
