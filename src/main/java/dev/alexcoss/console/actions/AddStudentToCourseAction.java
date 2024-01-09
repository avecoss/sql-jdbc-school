package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.console.actions.AbstractAction;
import dev.alexcoss.dao.StudentsCoursesDao;

public class AddStudentToCourseAction extends AbstractAction {
    public AddStudentToCourseAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute() {
        StudentsCoursesDao studentsCoursesDao = commandInputScanner.getStudentsCoursesDao();
        processStudentInCourse(studentsCoursesDao::addItem, "add");
    }
}