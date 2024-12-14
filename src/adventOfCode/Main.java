package adventOfCode;

import java.lang.reflect.Method;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the year (e.g., 2024): ");
        String year = scanner.nextLine();

        System.out.print("Enter the day (e.g., day1): ");
        String day = scanner.nextLine();

        scanner.close();
		
		try {
			String className = "adventOfCode" + year +  "." + day + "." + capitalize(day);

			Class<?> dayClass = Class.forName(className);
			
			Method runMethod = dayClass.getMethod("run");
			
			runMethod.invoke(null);
		}  catch (ClassNotFoundException e) {
            System.out.println("Class for " + year + " " + day + " not found.");
        } catch (NoSuchMethodException e) {
            System.out.println("No run() method available for " + year + " " + day);
        } catch (Exception e) {
            System.out.println("Error running " + year + " " + day + ": " + e.getMessage());
        }
		
	}
	    private static String capitalize(String input) {
	        if (input == null || input.isEmpty()) {
	            return input;
	        }
	        return input.substring(0, 1).toUpperCase() + input.substring(1);
	    }
}
