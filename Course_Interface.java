package hw6;

import java.util.List;

public interface Course_Interface {
	
	/**
	 * Getter for course name
	 * @return course name
	 */
	public String getCourseName();
	
	/**
	 * Getter for course code
	 * @return course code
	 */
	public String getCourseCode();
	
	/**
	 * Getter for course capacity
	 * @return course capacity
	 */
	public int getCourseCapacity();
	
	/**
	 * Getter for Course Roster
	 * @return course roster
	 */
	public List<Student> getCourseRoster();
	
	/**
	 * Checks whether the course enrollment has reached its capacity
	 * @return Returns true if the number of enrolled students is equal to 
	 * capacity, false otherwise
	 */
	public boolean isFull();

	/**
	 * Enqueues the student to the waitlist for this course
	 * @param Registration r to be waitlisted
	 */
	public void addToWaitlist(Registration r);
	
	/**
	 * Enrolls the next student in the waitlist to the course.
	 * Does nothing if the waitlist is empty
	 */
	public Registration processWaitlist();

}
