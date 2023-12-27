package dev.alexcoss.service;

import dev.alexcoss.dao.*;

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
        GroupManager groupManager = new GroupManager(groupDao, groupsGenerator);
        groupManager.generateAndSaveGroupsToDatabase();

        CourseManager courseManager = new CourseManager(courseDao, coursesGenerator);
        courseManager.generateAndSaveCoursesToDatabase();

        StudentManager studentManager = new StudentManager(studentDao, groupRandomizer, studentGenerator, groupDao);
        studentManager.generateAndSaveStudentsToDatabase();

        StudentsCoursesManager studentsCoursesManager = new StudentsCoursesManager(courseRandomizer, studentsCoursesDao, studentDao, courseDao);
        studentsCoursesManager.assignStudentsToCoursesAndSave();
    }
}
