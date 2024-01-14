package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.dao.StudentsCoursesDao;

import java.util.Scanner;

public class RemoveStudentFromCourseAction extends AbstractAction {
    public RemoveStudentFromCourseAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute(Scanner scanner) {
        StudentsCoursesDao studentsCoursesDao = commandInputScanner.getStudentsCoursesDao();
        processStudentInCourse(scanner, studentsCoursesDao::removeItems, "remove");
    }
}
