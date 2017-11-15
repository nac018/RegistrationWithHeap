/*
 * Name: Nang Chen
 * Login: cs12saw
 * PID: A14205066
 * Date: 05/16/2017
 * File: Student.java
 * 
 * This is a class that gets all the course scheduling 
 * methods and conditions.
 */

package hw6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class CourseScheduling {

	public static List<Course> courseList = new LinkedList<>();
	public static List<Student> studentList = new LinkedList<>();

	public static void populateCourseandStudents(String fname) {
		File file = new File(fname);
		try {
			Scanner scanner = new Scanner(file);
			scanner.nextLine();
			boolean loadCourse = true;
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				if (nextLine.isEmpty()) {
					loadCourse = false;
					scanner.nextLine();
					continue;
				}
				Scanner wordscan = new Scanner(nextLine);
				if (loadCourse) {
					String id = wordscan.next();
					String cname = wordscan.next();
					int capacity = wordscan.nextInt();
					Course course = new Course(cname, id, capacity);
					courseList.add(course);
				} else {
					String sname = wordscan.next();
					String pid = wordscan.next();
					int coins = wordscan.nextInt();
					Student student = new Student(pid, sname, coins);
					studentList.add(student);
				}
				wordscan.close();
			}
			scanner.close();
		} catch (FileNotFoundException e) {

		}

	}

	public static void print(Student s, Course c, int coins, boolean enroll) {
		if (enroll)
			System.out.println("Enrolling " + s.toString() + " to Course " + c.toString() + " with coins " + coins);
		else
			System.out.println("Waitlisting " + s.toString() + " to Course " + c.toString() + " with coins " + coins);
	}

	public static void printFail(Student s, Course c, boolean enroll) {
		if (enroll)
			System.out.println(s.toString() + " is already enrolled in Course " + c.toString());
		else
			System.out.println(s.toString() + " is already waitlisted in Course " + "	" + c.toString());
	}

	public static void printCapacity(Course c) {
		System.out.println(c.toString() + "has already reached maximum capacity. There are no more seats left");
	}

	public static void printNoCoins(Student s, Course c) {
		System.out.println("Insufficient course coins. Cannot waitlist " + s.toString() + " to " + c.toString());
	}

	public static void generateOutput() {

		System.out.println("\n\n########COURSE INFORMATION######");
		ListIterator<Course> iter = courseList.listIterator();
		while (iter.hasNext()) {
			Course temp = iter.next();
			System.out.println(temp.getCourseCode() + "  " + temp.getCourseName());
			System.out.println("\tRoster: " + temp.getCourseRoster());
		}

		System.out.println("\n\n########STUDENT INFORMATION######");
		ListIterator<Student> iterS = studentList.listIterator();
		while (iterS.hasNext()) {
			Student temp = iterS.next();
			System.out.println(temp.toString());
			System.out.println("\t Enrolled courses: " + temp.getmyEnrolledCourses());
			System.out.println("\t Waitlisted courses: " + temp.getmyWaitlist());
		}

	}

	/**
	 * Returns a reference to the Course object whose courseCode is code
	 * 
	 * @param code
	 *            Course code of the Course to be returned
	 * @return Course Course with the course code passed as a parameter
	 */
	public static Course getCourse(String code) {
		ListIterator<Course> iter = courseList.listIterator();
		while (iter.hasNext()) {
			if (iter.next().getCourseCode().equals(code)) {
				return iter.previous();
			}
		}
		return null;
	}

	/**
	 * Returns a reference to the Student object whose StudentID is pid
	 * 
	 * @param pid
	 *            StudentID of the Student to be returned
	 * @return Student student with the id passed as a parameter
	 */
	public static Student getStudent(String pid) {
		ListIterator<Student> iter = studentList.listIterator();
		while (iter.hasNext()) {
			if (iter.next().getStudentID().equals(pid)) {
				return iter.previous();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		populateCourseandStudents("Info.txt");
		File file = new File("test3.txt");

		try {
			Scanner scLine = new Scanner(file); // scan lines in file
			System.out.println("####START COURSE SCHEDULING####\n");
			while (scLine.hasNextLine()) {// if the file has next line
				Scanner scWord = new Scanner(scLine.nextLine());
				String property;
				if (scWord.hasNext()) {// if the file has next word
					property = scWord.next();
				} else
					break;
				if (property.equals("register")) {
					//scans the next string which would be student and course
					String stu = scWord.next();
					String cour = scWord.next();
					Student student = getStudent(stu);
					Course course = getCourse(cour);
					int coins = scWord.nextInt();
					//make a new registration object with scanned
					//student,course and coins
					Registration reg = new Registration(student,course,coins);
					//if student is already enrolled
					if (course.getCourseRoster().contains(student)) {
						printFail(student, course, true);
						//if student is already waitlisted
					} else if (student.getmyWaitlist().contains(course)) {
						printFail(student, course, false);
						//if student has no sufficient coins
					} else if (student.getCoins() < coins) {
						printNoCoins(student, course);
					} else {
						//add the course to the student's waitlist
						student.getmyWaitlist().add(course);
						//enqueue the student to the waitlist
						course.addToWaitlist(reg);
						//deduct student's coins
						student.deductCoins(coins);
						print(student, course, coins, false);
					}
				} else if (property.equals("enroll")) {
					// process registrations in the waitlist
					System.out.println("\n####STARTING BATCH ENROLLMENT####");
					//scan the next integer which is the size of enrollment
					int enrollSize = scWord.nextInt();
					for (int k = 0; k < enrollSize; k++) {
						for (int i = 0; i < courseList.size(); i++) {
							//if the course is full already
							if (courseList.get(i).isFull()) {
								printCapacity(courseList.get(i));
							} else {
								//enroll the student
								Registration reg = 
										courseList.get(i).processWaitlist();
								if (reg != null) {
									print(reg.getStudent(), courseList.get(i),
											reg.getCoins(), true);
								}
							}
						}
					}
					System.out.println("####ENDING BATCH ENROLLMENT####\n");
				} else
					break;
				scWord.close();
			}
			scLine.close();
			System.out.println("\n####END COURSE SCHEDULING####");
		} catch (FileNotFoundException e) {
			System.err.println("Failed to open " + file);
			System.exit(1);
		}
		generateOutput();
	}
}
