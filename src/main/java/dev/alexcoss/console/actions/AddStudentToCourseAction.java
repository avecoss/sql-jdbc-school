package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.console.actions.AbstractAction;
import dev.alexcoss.dao.StudentsCoursesDao;

import java.util.Scanner;

public class AddStudentToCourseAction extends AbstractAction {
    public AddStudentToCourseAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute(Scanner scanner) {
        StudentsCoursesDao studentsCoursesDao = commandInputScanner.getStudentsCoursesDao();
        processStudentInCourse(scanner, studentsCoursesDao::addItem, "add");
    }
}
