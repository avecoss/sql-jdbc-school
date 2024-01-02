package dev.alexcoss.console;

import dev.alexcoss.dao.CourseDao;
import dev.alexcoss.dao.GroupDao;
import dev.alexcoss.dao.StudentDao;
import dev.alexcoss.dao.StudentsCoursesDao;
import dev.alexcoss.model.Course;
import dev.alexcoss.model.Group;
import dev.alexcoss.model.Student;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CommandInputScanner {

    protected Scanner scanner;
    private final StudentDao studentDao;
    private final GroupDao groupDao;
    private final CourseDao courseDao;
    private final StudentsCoursesDao studentsCoursesDao;

    public CommandInputScanner(StudentDao studentDao, GroupDao groupDao, CourseDao courseDao, StudentsCoursesDao studentsCoursesDao) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
        this.courseDao = courseDao;
        this.studentsCoursesDao = studentsCoursesDao;
        this.scanner = new Scanner(System.in);
    }

    public void scannerRun(List<String> commands) {
        int commandsCount = commands.size() - 1;
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Enter a command:");
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if ("exit".equals(line)) {
                    isRunning = false;
                } else {
                    processInput(commandsCount, line);
                }
            }
        }
        scanner.close();
    }

    private void processInput(int commandsCount, String input) {
        if (isInteger(input)) {
            processIntegerInput(commandsCount, input);
        } else {
            System.out.println("Invalid input. Please enter a valid command or 'exit' to close the application.");
        }
    }

    private void processIntegerInput(int commandsCount, String input) {
        int number = Integer.parseInt(input);
        if (number >= 0 && number <= commandsCount) {
            executeCommand(number);
        } else {
            System.out.println("Invalid command number. Please enter a valid command.");
        }
    }

    private void executeCommand(int commandNumber) {
        switch (commandNumber) {
            case 0:
                printStudentById();
                break;
            case 1:
                findAllGroupsWithLessOrEqualStudents();
                break;
            case 2:
                findAllStudentsRelatedToCourse();
                break;
            case 3:
                addNewStudent();
                break;
            case 4:
                deleteStudentById();
                break;
            case 5:
                addStudentToCourse();
                break;
            case 6:
                removeStudentFromCourse();
                break;
            default:
                System.out.println("Invalid command number. Please enter a valid command.");
        }
    }

    private void printStudentById() {
        processStudentById(studentId -> {
                Student student = studentDao.getStudentById(studentId);
                if (student != null) {
                    System.out.println("Student: " + student);
                } else {
                    System.out.println("Student not found");
                }
            },
            "Executing command 0: Print student by ID: "
        );
    }

    private void findAllGroupsWithLessOrEqualStudents() {
        Map<Group, Integer> allGroupsWithStudents = groupDao.getAllGroupsWithStudents();

        List<Map.Entry<Group, Integer>> minEntries = allGroupsWithStudents.entrySet().stream()
            .collect(Collectors.groupingBy(Map.Entry::getValue))
            .entrySet().stream()
            .min(Map.Entry.comparingByKey())
            .map(Map.Entry::getValue)
            .orElse(Collections.emptyList());

        System.out.println("Executing command 1: Find all groups with less or equal students' number");

        minEntries.forEach(entry -> {
            System.out.println("Group: " + entry.getKey());
            System.out.println("Number of students: " + entry.getValue());
        });

    }

    private void findAllStudentsRelatedToCourse() {
        printListOfCourses();

        System.out.print("Enter the course name: ");
        String courseName = scanner.nextLine();

        System.out.println("Executing command 2: Find all students related to the course with the given name " + courseName);

        List<Student> studentsByCourse = studentDao.getStudentsByCourse(courseName);
        if (studentsByCourse.isEmpty()) {
            System.out.println("No students found for this course title " + courseName);
        } else {
            studentsByCourse.forEach(System.out::println);
        }
    }

    private void addNewStudent() {
        Student student = new Student();
        System.out.print("Enter student first name: ");
        student.setFirstName(scanner.nextLine());

        System.out.print("Enter student last name: ");
        student.setLastName(scanner.nextLine());

        studentDao.addItem(student);
        System.out.println("Executing command 3: Add a new student");
    }

    private void deleteStudentById() {
        processStudentById(studentDao::removeStudentById, "Executing command 4: Delete a student by ID: ");
    }

    private void addStudentToCourse() {
        processStudentInCourse(studentsCoursesDao::addItem, "add");
    }

    private void removeStudentFromCourse() {
        processStudentInCourse(studentsCoursesDao::removeItems, "remove");
    }

    private void printListOfCourses() {
        System.out.println("List of courses: ");
        List<Course> courses = courseDao.getAllItems();
        courses.forEach(course -> {
            System.out.println(course.getName());
        });
    }

    private void processStudentById(Consumer<Integer> action, String actionName) {
        System.out.print("Enter the student ID: ");
        if (scanner.hasNextInt()) {
            int studentId = scanner.nextInt();
            action.accept(studentId);
            System.out.println(actionName + studentId);
        } else {
            System.out.println("Invalid input. Please enter a valid integer for the student ID.");
        }
        scanner.nextLine();
    }

    private void processStudentInCourse(Consumer<Map<Integer, Integer>> action, String actionName) {
        printListOfCourses();

        System.out.print("Enter the course name: ");
        String inputCourseName = scanner.nextLine();

        System.out.print("Enter the student ID: ");
        if (scanner.hasNextInt()) {
            int studentId = scanner.nextInt();
            Student student = studentDao.getStudentById(studentId);

            List<Course> courses = courseDao.getAllItems();
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
        scanner.nextLine();
    }

    private boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
