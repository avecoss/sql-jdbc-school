package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.console.actions.AbstractAction;
import dev.alexcoss.model.Student;

public class PrintStudentByIdAction extends AbstractAction {

    public PrintStudentByIdAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute() {
        processStudentById(studentId -> {
                Student student = commandInputScanner.getStudentDao().getStudentById(studentId);
                if (student != null) {
                    System.out.println("Student: " + student);
                } else {
                    System.out.println("Student not found");
                }
            },
            "Executing command 0: Print student by ID: "
        );
    }
}
