/*
 * Name: Nang Chen
 * Login: cs12saw
 * PID: A14205066
 * Date: 05/16/2017
 * File: Course.java
 * 
 * This is a class that gets all the course material.
 */

package hw6;

import java.util.*;

public class Course implements Course_Interface {

	private String courseName;
	private String courseCode;
	private MyPriorityQueue<Registration> waitlistQueue;
	private List<Student> roster;
	private int maxCapacity;

	public Course(String name, String code, int capacity) {
		this.courseName = name;
		this.courseCode = code;
		this.maxCapacity = capacity;
		this.waitlistQueue = new MyPriorityQueue<Registration>(capacity);
		this.roster = new LinkedList<Student>();
	}

	@Override
	public String toString() {
		return courseCode;
	}

	/**
	 * Getter for course name
	 * 
	 * @return course name
	 */
	@Override
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Getter for course code
	 * 
	 * @return course code
	 */
	@Override
	public String getCourseCode() {
		return courseCode;
	}

	/**
	 * Getter for course capacity
	 * 
	 * @return course capacity
	 */
	@Override
	public int getCourseCapacity() {
		return maxCapacity;
	}

	/**
	 * Getter for Course Roster
	 * 
	 * @return course roster
	 */
	@Override
	public List<Student> getCourseRoster() {
		return roster;
	}

	/**
	 * Checks whether the course enrollment has reached its capacity
	 * 
	 * @return Returns true if the number of enrolled students is equal to
	 *         capacity, false otherwise
	 */
	@Override
	public boolean isFull() {
		if (roster.size() == maxCapacity) {
			return true;
		}
		return false;
	}

	/**
	 * Enqueues the student to the waitlist for this course
	 * 
	 * @param Registration
	 *            r to be waitlisted
	 */
	@Override
	public void addToWaitlist(Registration r) {
		waitlistQueue.offer(r);
	}

	/**
	 * Enrolls the next student in the waitlist to the course. Does nothing if
	 * the waitlist is empty
	 * 
	 * @return the student that is ready to be enrolled
	 */
	@Override
	public Registration processWaitlist() {
		if (waitlistQueue.peek() == null) {
			return null;
		}
		// registraion object that gets the head of the waitlist queue
		Registration reg = waitlistQueue.poll();
		// add student into the course roster
		this.getCourseRoster().add(reg.getStudent());
		// enroll student into particular course
		reg.getStudent().enrollCourse(reg.getCourse());
		return reg;
	}

}
