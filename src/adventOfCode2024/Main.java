package adventOfCode2024;

import adventOfCode2024.day1.Day1;
import adventOfCode2024.day2.Day2;
import adventOfCode2024.day3.Day3;
import adventOfCode2024.day4.Day4;
import adventOfCode2024.day5.Day5;
import adventOfCode2024.day6.Day6;

public class Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Provide the day to run as argument");
			return;
		}
		
		String day = args[0];
		switch (day) {
		case "day1":
			Day1.run();
		case "day2":
			Day2.run();
		case "day3":
			Day3.run();
		case "day4":
			Day4.run();
		case "day5":
			Day5.run();
		case "day6":
			Day6.run();
		}
	}
}
