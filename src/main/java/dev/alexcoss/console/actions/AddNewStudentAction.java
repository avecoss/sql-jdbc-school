package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.console.actions.AbstractAction;
import dev.alexcoss.model.Student;

public class AddNewStudentAction extends AbstractAction {
    public AddNewStudentAction(CommandInputScanner commandInputScanner) {
        super(commandInputScanner);
    }

    @Override
    public void execute() {
        Student student = new Student();
        System.out.print("Enter student first name: ");
        student.setFirstName(commandInputScanner.getScanner().nextLine());

        System.out.print("Enter student last name: ");
        student.setLastName(commandInputScanner.getScanner().nextLine());

        commandInputScanner.getStudentDao().addItem(student);
        System.out.println("Executing command 3: Add a new student");
    }
}
