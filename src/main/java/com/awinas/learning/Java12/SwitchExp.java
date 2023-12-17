package com.awinas.learning.Java12;

public class SwitchExp {

	public static void main(String[] args) {
		/*
		 * This change extends the switch statement so that it can be used as either a
		 * statement or an expression.
		 * 
		 */

		/*
		 * Classic switch statement:
		 */

		String result = "";
		var day = "F";
		
		switch (day) {
			case "M":
			case "W":
			case "F": {
				result = "MWF";
				break;
			}
			case "T":
			case "TH":
			case "S": {
				result = "TTS";
				break;
			}
		}

		System.out.println("Old Switch Result: " + result);

		/*
		 * With the new Switch expression, we don’t need to set break everywhere thus
		 * prevent logic errors!
		 */

		day = "SUN";
		result = switch (day) {
		case "M", "W", "F" -> "MWF";
		case "T", "TH", "S" -> "TTS";
		default -> {
			if (day.isEmpty())
				yield "Please insert a valid day.";
			else
				yield "Looks like a Sunday.";
		}
		};

		System.out.println(result);

	}

}
