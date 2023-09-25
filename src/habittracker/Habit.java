package habittracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Habit implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private int[] scheduledDays;
	private List<Entry> entries;
	private Date lastEntry;

	public Habit(String name, int[] scheduledDays) {
		this.name = name;
		this.scheduledDays = scheduledDays;
		this.entries = new ArrayList<Entry>();
	}

	// Logic

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Habit other = (Habit) o;
		// Returning true if the names are the same and false if not.
		return name.equals(other.getName());
	}

	/**
	 * Outputs the statistics that have been collected about a particular habit to
	 * the console.
	 */
	public void printStatistics() {
		System.out.println("\n" + Color.CYAN.colorCode() + name + Color.reset());
		System.out.println("Overview:");
		// Outputting the success rate in red if the rate is < 0.75 and outputting it in
		// green if the rate is > 0.75.
		if (calculateSuccessRate() < 0.75) {
			System.out.println(" - Success rate: " + Color.RED.colorCode() + (int) (calculateSuccessRate() * 100) + "%"
					+ Color.reset());
		} else {
			System.out.println(" - Success rate: " + Color.GREEN.colorCode() + (int) (calculateSuccessRate() * 100)
					+ "%" + Color.reset());
		}
		System.out.println(" - Total entries: " + Color.CYAN.colorCode() + entries.size() + Color.reset());
		System.out.println(" - Total successes: " + Color.CYAN.colorCode() + countSuccessfulDays() + Color.reset());
		printReasonsForFailureBreakdown();
	}

	/**
	 * Calculates the rate at which a habit was successfully performed.
	 * 
	 * @return The rate at which a habit was successfully performed.
	 */
	public double calculateSuccessRate() {
		int totalDays = entries.size();
		if (totalDays == 0) {
			return 0.0;
		}
		int successfulDays = countSuccessfulDays();
		return (double) successfulDays / (double) totalDays;
	}

	/**
	 * Counts the number of days on which the habit was successfully performed.
	 * 
	 * @return Number of days on which the habit was successfully performed.
	 */
	public int countSuccessfulDays() {
		int successfulDays = 0;
		for (Entry entry : entries) {
			if (entry.isSuccess()) {
				successfulDays++;
			}
		}
		return successfulDays;
	}

	/**
	 * Prints a breakdown about the reasons of failure for a specific habit to the
	 * console.
	 */
	public void printReasonsForFailureBreakdown() {
		System.out.println("\nHow often the different reasons of failure occurred:");

		// Creating a list that contains all reasons for failure of a specific habit
		// that have been tracked in the passed.
		List<ReasonForFailure> trackedReasonsForFailure = new ArrayList<ReasonForFailure>();
		for (Entry entry : entries) {
			if (!entry.isSuccess()) {
				trackedReasonsForFailure.add(entry.getReasonForFailure());
			}
		}

		// For each existing reason the number of times it appears in the tracked list
		// is counted.
		// Iterating through the list of the existing reasons for failure.
		for (ReasonForFailure reasonForFailure1 : ReasonForFailure.values()) {
			int counter = 0;
			// Iterating through the list of all tracked reasons for failure.
			for (ReasonForFailure reasonForFailure2 : trackedReasonsForFailure) {
				if (reasonForFailure1.equals(reasonForFailure2)) {
					counter++;
				}
			}
			System.out.println(
					" - " + reasonForFailure1.getName() + ": " + Color.CYAN.colorCode() + counter + Color.reset());
		}
	}

	/**
	 * Determines whether or not a habit is scheduled for the current day.
	 * 
	 * @return true if the habit is scheduled for the current day, false if it is
	 *         not.
	 */
	public boolean isScheduledToday() {
		Calendar calendar = new GregorianCalendar();
		int dayOfWeekToday = calendar.get(Calendar.DAY_OF_WEEK);

		// Iterating through the list of all days of the week at which a habit is
		// scheduled and deciding whether it includes the current day of the week.
		for (int dayOfWeek : scheduledDays) {
			if (dayOfWeek == dayOfWeekToday) {
				return true;
			}
		}
		return false;
	}

	// Getters / Setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getScheduledDays() {
		return scheduledDays;
	}

	public void setScheduledDays(int[] scheduledDays) {
		this.scheduledDays = scheduledDays;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public Date getLastEntry() {
		return lastEntry;
	}

	public void setLastEntry(Date lastEntry) {
		this.lastEntry = lastEntry;
	}

}
