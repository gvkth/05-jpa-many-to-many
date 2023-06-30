package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Course;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import com.luv2code.cruddemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO{

    //define the field for entity manager
    //inject entity manager using constructor injection

    private EntityManager entityManager;

    @Autowired
    public AppDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor theInstructor) {
        entityManager.persist(theInstructor);//this will also save the detail object because of CascadeType is ALL
    }

    @Override
    public Instructor findInstructorById(int theId) {
        return entityManager.find(Instructor.class,theId);//Magic ORM Mapping: This will ALSO retrieve the instructor details object
    }

    @Override
    @Transactional
    public void deleteInstructorById(int theId) {
        //retrieve the instructor
        Instructor tempInstructor = entityManager.find(Instructor.class,theId);

        List<Course> courses = tempInstructor.getCourses();

        //break associations of all courses for instructor
        for (Course tempCourse: courses){
            tempCourse.setInstructor(null);
        }

        //delete the instructor
        entityManager.remove(tempInstructor);//MAGIC: this will ALSO delete the instructor details object because of CascadeType.ALL
    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return entityManager.find(InstructorDetail.class,theId);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int theId) {

        InstructorDetail tempInstructorDetail = entityManager.find(InstructorDetail.class,theId);

        //remove the associated object, not delete courses
        tempInstructorDetail.getInstructor().setInstructorDetail(null);

        entityManager.remove(tempInstructorDetail);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int theId) {
        //create query
        TypedQuery<Course> query = entityManager.createQuery("from Course where instructor.id=:data",Course.class);
        query.setParameter("data",theId);

        //execute query
        List<Course> courses = query.getResultList();

        return courses;
    }
    @Override
    public Instructor findCoursesByInstructorIdJoinFetch(int theId) {
        //create query
        TypedQuery<Instructor> query = entityManager.createQuery("select i from Instructor i "
                +" JOIN FETCH i.courses "
                +" JOIN FETCH i.instructorDetail "
                +" where i.id=:data",Instructor.class);
        query.setParameter("data",theId);

        //execute query
        Instructor instructor = query.getSingleResult();

        return instructor;
    }

    @Override
    @Transactional
    public void update(Instructor tempInstructor) {
        entityManager.merge(tempInstructor);
    }


    @Override
    @Transactional
    public void deleteCourseById(int theId) {
        //retrieve the course
        Course tempCourse = entityManager.find(Course.class, theId);

        //delete the course
        entityManager.remove(tempCourse);
    }

    @Override
    @Transactional
    public void save(Course theCourse) {
        entityManager.persist(theCourse);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int theId) {
        //create query
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course  c "
                +"JOIN FETCH c.reviews "
                +"where c.id= :data", Course.class
        );
        query.setParameter("data", theId);

        //execute query
        Course course = query.getSingleResult();
        return course;
    }

    @Override
    public Course findCourseAndStudentsByCourseId(int theId) {
        //create query
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course  c "
                        +"JOIN FETCH c.students "
                        +"where c.id= :data", Course.class
        );
        query.setParameter("data", theId);

        //execute query
        Course course = query.getSingleResult();
        return course;

    }

    @Override
    public Student findStudentAndCourses(int theStudentId) {
        //create query
        TypedQuery<Student> query = entityManager.createQuery(
                "select s from Student  s "
                        +"JOIN FETCH s.courses "
                        +"where s.id= :data", Student.class
        );
        query.setParameter("data", theStudentId);

        //execute query
        Student student = query.getSingleResult();
        return student;
    }

    @Override
    @Transactional
    public void update(Student tempStudent) {
        entityManager.merge(tempStudent);
    }

    @Override
    @Transactional
    public void deleteStudentById(int theId) {
        //retrieve the student
        Student tempStudent = entityManager.find(Student.class, theId);

        //delete the student
        entityManager.remove(tempStudent);
    }
}
