package com.luv2code.cruddemo;

import com.luv2code.cruddemo.dao.AppDAO;
import com.luv2code.cruddemo.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO){
		return runner->{
			//System.out.println("Hello World");
//			createCourseAndReviews(appDAO);
//			retrieveCourseAndReviews(appDAO);
//			deleteCourseAndReviews(appDAO);
//			createCourseAndStudents(appDAO);
//			retrieveCourseAndStudents(appDAO);
//			retrieveStudentAndCourses(appDAO);
//			updateStudent(appDAO);
//			deleteCourse(appDAO);
			deleteStudent(appDAO);
		};
	}

	private void deleteStudent(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Deleting student id: "+theId);
		appDAO.deleteStudentById(theId);
		System.out.println("Done!");
	}

	private void deleteCourse(AppDAO appDAO) {
		int theId = 10;
		System.out.println("Deleting course id: "+theId);
		appDAO.deleteCourseById(theId);
		System.out.println("Done!");
	}

	private void updateStudent(AppDAO appDAO) {
		int theId = 2;
		Student tempStudent = appDAO.findStudentAndCourses(theId);
		//create more courses
		Course tempCourse1 = new Course("Rubik's Cube - How to Speed Cube");
		Course tempCourse2 = new Course("Atari 2600 - Game Development");

		//add courses and
		tempStudent.addCourse(tempCourse1);
		tempStudent.addCourse(tempCourse2);
		System.out.println("Saving students"+tempStudent);
		System.out.println("Associated courses: "+tempStudent.getCourses());

		appDAO.update(tempStudent);
	}

	private void retrieveStudentAndCourses(AppDAO appDAO) {
		int theId = 1;
		Student tempStudent = appDAO.findStudentAndCourses(theId);

		//print the course
		System.out.println(tempStudent);

		//print the reviews
		System.out.println(tempStudent.getCourses());

		System.out.println("Done!");
	}

	private void retrieveCourseAndStudents(AppDAO appDAO) {
		//get the course and reviews
		int theId = 10;
		Course tempCourse = appDAO.findCourseAndStudentsByCourseId(theId);

		//print the course
		System.out.println(tempCourse);

		//print the reviews
		System.out.println(tempCourse.getStudents());

		System.out.println("Done!");
	}

	private void createCourseAndStudents(AppDAO appDAO) {
		//create a course
		Course tempCourse = new Course("Pacman-abc");
		//create the students
		Student tempStudent1 = new Student("John","Doe","mail1@y.m");
		Student tempStudent2 = new Student("Mary", "Public","mail2@y.m");
		//add students to the course
		tempCourse.addStudent(tempStudent1);
		tempCourse.addStudent(tempStudent2);
		//save the course and associated students
		System.out.println("Saving the course: "+tempCourse);
		System.out.println("associated students: "+tempCourse.getStudents());

		appDAO.save(tempCourse);
		System.out.println("Done!");

	}

	private void deleteCourseAndReviews(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Deleting course: ");
		System.out.println(appDAO.findCourseAndReviewsByCourseId(theId));
		appDAO.deleteCourseById(theId);
		System.out.println("Done");
	}

	private void retrieveCourseAndReviews(AppDAO appDAO) {
		//get the course and reviews
		int theId = 1;
		Course tempCourse = appDAO.findCourseAndReviewsByCourseId(theId);

		//print the course
		System.out.println(tempCourse);

		//print the reviews
		System.out.println(tempCourse.getReviews());

		System.out.println("Done!");
	}

	private void createCourseAndReviews(AppDAO appDAO) {
		// create a course
		Course tempCourse = new Course("Pacman - How to Score One Million Points");

		// add some reviews
		tempCourse.addReview(new Review("Great course ... loved it!"));
		tempCourse.addReview(new Review("Cool course, job well done."));
		tempCourse.addReview(new Review("What a dumb course, you are an idiot!"));

		// save the courses
		System.out.println("Saving the course");
		System.out.println(tempCourse);
		System.out.println(tempCourse.getReviews());
		appDAO.save(tempCourse);
		System.out.println("Done!");
	}

	private void deleteCourseById(AppDAO appDAO) {
		int theId = 10;
		System.out.println("Deleteing course id: "+theId);

		appDAO.deleteCourseById(theId);

		System.out.println("Done!");
	}

	private void updateInstructor(AppDAO appDAO) {
		int theId = 1;

		System.out.println("Finding instructor id: " + theId);
		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("Updating instructor id: "+theId);
		tempInstructor.setLastName("TESTER");

		appDAO.update(tempInstructor);

		System.out.println("Done");
	}

	private void findCoursesForInstructorJoinFetch(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Finding instructor id: "+theId);

		Instructor tempInstructor = appDAO.findCoursesByInstructorIdJoinFetch(theId);
		System.out.println("tempInstructor: "+tempInstructor);

		//find courses for instructor


		System.out.println("the associated courses: "+tempInstructor.getCourses());
		System.out.println("Done!");
	}

	private void findInstructorWithCourses(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Finding instructor id: "+theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);
		System.out.println("tempInstructor: "+tempInstructor);

		//find courses for instructor
		List<Course> courseList=appDAO.findCoursesByInstructorId(theId);
		tempInstructor.setCourses(courseList);
		//System.out.println("the associated courses: "+tempInstructor.getCourses());

		//associate the objects
		tempInstructor.setCourses(courseList);

		System.out.println("the associated courses: "+tempInstructor.getCourses());
	}



	private void createInstructorWithCourses(AppDAO appDAO) {
		//create the instructor
		Instructor tempInstructor = new Instructor("Susan","Public","susan.public@luv2code.com");

		InstructorDetail tempInstructorDetail = new InstructorDetail(
				"http://www.youtube.com",
				"Video games"
		);
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		//create some courses
		Course tempCourse1 = new Course("Air Guitar - The Ultimate Guide");
		Course tempCourse2 = new Course("The Pinball Masterclass");

		//add courses to instructor
		tempInstructor.add(tempCourse1);
		tempInstructor.add(tempCourse2);

		//save the instructor
		System.out.println("Saving instructor: "+tempInstructor);
		System.out.println("The courses: "+tempInstructor.getCourses());
		appDAO.save(tempInstructor);

		System.out.println("Done!");
	}


	private void findInstructorDetail(AppDAO appDAO) {
		//get the instructor detail object
		int theId = 3;
		InstructorDetail tempInstructorDetail  =appDAO.findInstructorDetailById(theId);
		//print the instructor detail
		System.out.println("tempInstructorDetail: "+tempInstructorDetail);

		//print the associated instructor
		System.out.println("the associated instructor: "+tempInstructorDetail.getInstructor());

		System.out.println("Done!");

	}

	private void removeInstructor(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Deleting instructor id: "+theId);
		appDAO.deleteInstructorById(theId);

	}

	private void removeInstructorDetail(AppDAO appDAO) {
		int theId = 4;
		System.out.println("Deleting instructor detail id: "+theId);
		appDAO.deleteInstructorDetailById(theId);
	}

	private void findInstructor(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Finding instructor id: "+theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("the associated instructorDetail only: "+tempInstructor.getInstructorDetail());
	}

	private void createInstructor(AppDAO appDAO) {
		//create the instructor
		Instructor tempInstructor = new Instructor("Chad","Darby","darby@luv2code.com");

		//create the instructor detail
		InstructorDetail tempInstructorDetail = new InstructorDetail(
				"http://www.luv2code.com/youtube",
				"Luv 2 code");


		/*//create the instructor
		Instructor tempInstructor = new Instructor("Madhu","Patel","madhu@luv2code.com");

		//create the instructor detail
		InstructorDetail tempInstructorDetail = new InstructorDetail(
				"http://www.luv2code.com/youtube",
				"Guitar");*/

		//associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		//save the instructor
		//NOTE: this will also save the details object
		// because of CascadeType.ALL
		System.out.println("Saving instructor: "+tempInstructor);

		appDAO.save(tempInstructor);

	}

}
