package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.console.actions.AbstractAction;
import dev.alexcoss.dao.StudentDao;

public class DeleteStudentByIdAction extends AbstractAction {
    public DeleteStudentByIdAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute() {
        StudentDao studentDao = commandInputScanner.getStudentDao();
        processStudentById(studentDao::removeStudentById, "Executing command 4: Delete a student by ID: ");
    }
}
