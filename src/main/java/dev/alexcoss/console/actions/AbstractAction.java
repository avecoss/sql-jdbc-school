package dev.alexcoss.console.actions;

import dev.alexcoss.console.CommandInputScanner;
import dev.alexcoss.model.Course;
import dev.alexcoss.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractAction implements Action {

    protected final CommandInputScanner commandInputScanner;

    protected AbstractAction(CommandInputScanner commandInputScanner) {
        this.commandInputScanner = commandInputScanner;
    }

    protected void printListOfCourses() {
        System.out.println("List of courses: ");
        List<Course> courses = commandInputScanner.getCourseDao().getAllItems();
        courses.forEach(course -> {
            System.out.println(course.getName());
        });
    }

    protected void processStudentById(Consumer<Integer> action, String actionName) {
        System.out.print("Enter the student ID: ");
        if (commandInputScanner.getScanner().hasNextInt()) {
            int studentId = commandInputScanner.getScanner().nextInt();
            action.accept(studentId);
            System.out.println(actionName + studentId);
        } else {
            System.out.println("Invalid input. Please enter a valid integer for the student ID.");
        }
        commandInputScanner.getScanner().nextLine();
    }

    protected void processStudentInCourse(Consumer<Map<Integer, Integer>> action, String actionName) {
        printListOfCourses();

        System.out.print("Enter the course name: ");
        String inputCourseName = commandInputScanner.getScanner().nextLine();

        System.out.print("Enter the student ID: ");
        if (commandInputScanner.getScanner().hasNextInt()) {
            int studentId = commandInputScanner.getScanner().nextInt();
            Student student = commandInputScanner.getStudentDao().getStudentById(studentId);

            List<Course> courses = commandInputScanner.getCourseDao().getAllItems();
            Course course = courses.stream()
                .filter(c -> c.getName().equalsIgnoreCase(inputCourseName))
                .findFirst()
                .orElse(new Course());

            Map<Integer, Integer> studentIdCourseId = new HashMap<>();
            studentIdCourseId.put(student.getId(), course.getId());
            action.accept(studentIdCourseId);

            boolean isAddAction = "add".equals(actionName);
            System.out.printf("Executing command %s: %s the student %s %s %s the course %s\n",
                (isAddAction) ? "5" : "6",
                (isAddAction) ? "Add" : "Remove",
                student.getFirstName(),
                student.getLastName(),
                (isAddAction) ? "to" : "from",
                inputCourseName);
        } else {
            System.out.println("Invalid input. Please enter a valid integer for the student ID.");
        }
        commandInputScanner.getScanner().nextLine();
    }
}
