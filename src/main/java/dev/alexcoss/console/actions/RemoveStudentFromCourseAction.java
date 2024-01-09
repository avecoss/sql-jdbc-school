package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.console.actions.AbstractAction;
import dev.alexcoss.dao.StudentsCoursesDao;

public class RemoveStudentFromCourseAction extends AbstractAction {
    public RemoveStudentFromCourseAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute() {
        StudentsCoursesDao studentsCoursesDao = commandInputScanner.getStudentsCoursesDao();
        processStudentInCourse(studentsCoursesDao::removeItems, "remove");
    }
}
