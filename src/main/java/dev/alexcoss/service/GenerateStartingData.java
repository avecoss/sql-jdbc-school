package dev.alexcoss.service;

import dev.alexcoss.dao.*;
import dev.alexcoss.model.Course;
import dev.alexcoss.model.Group;
import dev.alexcoss.model.Student;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenerateStartingData {

    private final GroupDao groupDao;
    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final GroupsGenerator groupsGenerator;
    private final StudentGenerator studentGenerator;
    private final CoursesGenerator coursesGenerator;
    private final GroupRandomizer groupRandomizer;
    private final CourseRandomizer courseRandomizer;
    private final StudentsCoursesDao studentsCoursesDao;

    public GenerateStartingData() {
        this.groupDao = new GroupDao();
        this.studentDao = new StudentDao();
        this.courseDao = new CourseDao();
        this.groupsGenerator = new GroupsGenerator();
        this.studentGenerator = new StudentGenerator();
        this.coursesGenerator = new CoursesGenerator();
        this.groupRandomizer = new GroupRandomizer();
        this.courseRandomizer = new CourseRandomizer();
        this.studentsCoursesDao = new StudentsCoursesDao();
    }

    public void generateDataForDatabase() {
        List<Group> groups = generateGroups();
        saveGroupsToDatabase(groups);

        List<Course> courses = generateCourses();
        saveCoursesToDatabase(courses);

        List<Student> students = generateStudents();
        List<Group> groupsFromDatabase = getGroupsFromDatabase();
        List<Student> studentsInGroups = assignStudentsToGroups(students, groupsFromDatabase);
        saveStudentsToDatabase(studentsInGroups);

        List<Student> studentsFromDatabase = getStudentsFromDatabase();
        List<Course> coursesFromDatabase = getCoursesFromDatabase();
        Map<Integer, Set<Integer>> mapStudentCourses = assignStudentsToCourse(studentsFromDatabase, coursesFromDatabase);
        saveCoursesToDatabase(mapStudentCourses);
    }

    private List<Group> generateGroups() {
        return groupsGenerator.generateGroupList();
    }

    private void saveGroupsToDatabase(List<Group> groups) {
        groupDao.addAllItems(groups);
    }

    private List<Student> generateStudents() {
        return studentGenerator.generateStudents();
    }

    private List<Group> getGroupsFromDatabase() {
        return groupDao.getAllItems();
    }

    private List<Student> assignStudentsToGroups(List<Student> students, List<Group> groups) {
        return groupRandomizer.assignStudentsToGroups(students, groups);
    }

    private void saveStudentsToDatabase(List<Student> students) {
        studentDao.addAllItems(students);
    }

    private List<Course> generateCourses() {
        return coursesGenerator.getCoursesList();
    }

    private void saveCoursesToDatabase(List<Course> courses) {
        courseDao.addAllItems(courses);
    }

    private List<Student> getStudentsFromDatabase() {
        return studentDao.getAllItems();
    }

    private List<Course> getCoursesFromDatabase() {
        return courseDao.getAllItems();
    }

    private Map<Integer, Set<Integer>> assignStudentsToCourse(List<Student> students, List<Course> courses) {
        return courseRandomizer.assignStudentsToCourse(students, courses);
    }

    private void saveCoursesToDatabase(Map<Integer, Set<Integer>> mapStudentCourses) {
        studentsCoursesDao.addAllItems(mapStudentCourses);
    }
}
