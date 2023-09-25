package habittracker;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static final Scanner scanner = new Scanner(System.in);

	private static final int[] DAILY = { Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY,
			Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY };

	private static List<Habit> habits = new ArrayList<Habit>();

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		// Loading the list of habits and the corresponding data through
		// deserialization.
		try {
			FileInputStream fileInputStream = new FileInputStream("habits.ser");
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			habits = (List<Habit>) objectInputStream.readObject();
			objectInputStream.close();
			fileInputStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Creating the habits and adding them to the habits list (But only if they
		// haven't already been added after loading the list).
		int[] workingOutDays = {Calendar.MONDAY, Calendar.WEDNESDAY, Calendar.FRIDAY};
		Habit workingOut = new Habit("Example 1", workingOutDays);
		addNewHabit(workingOut);
		
		Habit stretching = new Habit("Example 2", DAILY);
		addNewHabit(stretching);

		// Informing the user if no habits have been added yet.
		if (habits.isEmpty()) {
			System.out.println(Color.CYAN.colorCode() + "[Info] " + Color.reset() + "No habits have been created yet.");
			scanner.close();
			return;
		}

		// Making sure that the first thing the user does at startup is to track his
		// habits for the current day.
		trackHabits();

		while (true) {
			int choice = 0;
			boolean validInput = false;
			do {
				try {
					// Printing a menu to the console where the user can choose what to do next.
					System.out.print("\n" + Color.CYAN.colorCode() + "[Choose an action]" + Color.reset()
							+ "(1) Track habits (2) View statistics / (0) Close program ");
					choice = scanner.nextInt();
					scanner.nextLine();
					validInput = true;
				} catch (InputMismatchException e) {
					scanner.nextLine();
					System.out.println(Color.RED.colorCode() + "[Error]" + Color.reset() + "Invalid input. Try again.");
					validInput = false;
					continue;
				}

				switch (choice) {
				case 1:
					System.out.print("\n");
					trackHabits();
					break;
				case 2:
					printStatistics();
					break;
				case 0:
					scanner.close();
					// Saving the list of habits and its changes.
					try {
						FileOutputStream fileOutputStream = new FileOutputStream("habits.ser");
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
						objectOutputStream.writeObject(habits);

						objectOutputStream.close();
						fileOutputStream.close();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					return;
				default:
					System.out.println(new InvalidInputException().getMessage());
					break;
				}
			} while (!validInput);
		}
	}

	/**
	 * 
	 */
	private static void trackHabits() {
		boolean allHabitsTracked = true;

		for (Habit habit : habits) {
			// Skipping the current habit, if it was already tracked today, or if the habit
			// is not scheduled for today.
			if (datesAreOnSameDay(habit.getLastEntry(), new Date()) || !habit.isScheduledToday()) {
				continue;
			}
			allHabitsTracked = false;

			// Letting the user can either track his habit now or skip it for the time
			// being.
			int choice = 0;
			boolean validInput = false;
			do {
				try {
					System.out.print(Color.CYAN.colorCode() + "[Track habit] " + Color.reset() + habit.getName()
							+ " (1) Successfully completed / (2) Not successfully completed / (0) Skip for now ");
					choice = scanner.nextInt();
					scanner.nextLine();
					validInput = true;
				} catch (InputMismatchException e) {
					scanner.nextLine();
					System.out.println(new InvalidInputException().getMessage());
					validInput = false;
					continue;
				}

				switch (choice) {
				case 1:
					validInput = true;
					addNewEntry(habit, true);
					break;
				case 2:
					addNewEntry(habit, false);
					validInput = true;
					break;
				case 0:
					validInput = true;
					break;
				default:
					validInput = false;
					System.out.println(new InvalidInputException().getMessage());
					break;
				}
			} while (!validInput);
		}

		if (allHabitsTracked) {
			System.out.println(Color.CYAN.colorCode() + "[Info] " + Color.reset() + Color.GREEN.colorCode()
					+ "All habits have already been tracked today." + Color.reset());
		}
	}

	/**
	 * Adds a habit to the list of habits if it has not already been added after
	 * loading the habits list.
	 * 
	 * @param habit The habit to add.
	 */
	private static void addNewHabit(Habit habit) {
		if (!habits.contains(habit)) {
			habits.add(habit);
		}
	}

	/**
	 * @param habit         Habit to add.
	 * @param isSuccessfull true if the habit was successfully completed, false if
	 *                      not.
	 */
	private static void addNewEntry(Habit habit, boolean isSuccessfull) {
		Entry newEntry;
		if (isSuccessfull) {
			// If the habit was successfully completed, it is tracked as successful
			// and without a reason for failure.
			newEntry = new Entry(isSuccessfull, null);
		} else {
			// If the habit was not successfully completed, it is tracked as not successful
			// and the user is automatically prompted to choose a reason for failure.
			ReasonForFailure reasonForFailure = ReasonForFailure.chooseReasonForFailure();
			newEntry = new Entry(isSuccessfull, reasonForFailure);
		}
		// Add the new entry to the list of entries of the given habit and updating the
		// information about when the last entry took place.
		habit.getEntries().add(newEntry);
		habit.setLastEntry(new Date());
	}

	/**
	 * Checks if two date objects represent the same day.
	 * 
	 * @param date1
	 * @param date2
	 * @return true if date1 and date 2 represent the same day.
	 */
	private static boolean datesAreOnSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}

		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(date1);

		Calendar calendar2 = new GregorianCalendar();
		calendar2.setTime(date2);

		int year1 = calendar1.get(Calendar.YEAR);
		int month1 = calendar1.get(Calendar.MONTH);
		int day1 = calendar1.get(Calendar.DAY_OF_MONTH);

		int year2 = calendar2.get(Calendar.YEAR);
		int month2 = calendar2.get(Calendar.MONTH);
		int day2 = calendar2.get(Calendar.DAY_OF_MONTH);

		return year1 == year2 && month1 == month2 && day1 == day2;
	}

	/**
	 * Prints the statistics for all the habits formatted to the console.
	 */
	private static void printStatistics() {
		System.out.println("\n=======================================================================================");
		for (Habit habit : habits) {
			habit.printStatistics();
		}
		System.out.println("=======================================================================================");
	}

}