package dev.alexcoss.service;

import dev.alexcoss.dao.CourseDao;
import dev.alexcoss.dao.GroupDao;
import dev.alexcoss.dao.StudentDao;
import dev.alexcoss.model.Course;
import dev.alexcoss.model.Group;
import dev.alexcoss.model.Student;

import java.util.List;

public class GenerateStartData {

    public void generateDataForDatabase() {
        List<Group> groups = generateGroups();
        saveGroupsToDatabase(groups);

        List<Course> courses = generateCourses();
        saveCoursesToDatabase(courses);

        List<Student> students = generateStudents();
        List<Group> groupWithId = getGroupsFromDatabase();
        List<Student> studentsInGroups = assignStudentsToGroups(students, groupWithId);
        saveStudentsToDatabase(studentsInGroups);
    }

    private List<Group> generateGroups() {
        GroupsGenerator groupsGenerator = new GroupsGenerator();
        return groupsGenerator.generateGroupList();
    }

    private void saveGroupsToDatabase(List<Group> groups) {
        GroupDao groupDao = new GroupDao();
        groupDao.addGroups(groups);
    }

    private List<Student> generateStudents() {
        StudentGenerator studentGenerator = new StudentGenerator();
        return studentGenerator.generateStudents();
    }

    private List<Group> getGroupsFromDatabase() {
        GroupDao groupDao = new GroupDao();
        return groupDao.getAllGroups();
    }

    private List<Student> assignStudentsToGroups(List<Student> students, List<Group> groups) {
        GroupRandomizer randomizer = new GroupRandomizer();
        return randomizer.assignStudentsToGroups(students, groups);
    }

    private void saveStudentsToDatabase(List<Student> students) {
        StudentDao studentDao = new StudentDao();
        studentDao.addStudents(students);
    }

    private List<Course> generateCourses() {
        CoursesGenerator coursesGenerator = new CoursesGenerator();
        return coursesGenerator.getCoursesList();
    }

    private void saveCoursesToDatabase(List<Course> courses) {
        CourseDao courseDao = new CourseDao();
        courseDao.addGroups(courses);
    }
}
