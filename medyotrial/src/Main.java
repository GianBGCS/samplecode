import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Repository repo = new Repository();
        repo.printDatabasePath();

        System.out.print("Clear all existing students? (y/n): ");
        String clearChoice = scanner.nextLine();
        if (clearChoice.equalsIgnoreCase("y")) {
            repo.clearAllStudents();
        }
        System.out.println("\n=== Student Database Entry ===");
        System.out.println("Each student has 10 details, including a unique student ID (e.g., 3111).");
        System.out.println("You can add multiple students.\n");

        while (true) {
            System.out.println("--- New Student ---");

            System.out.print("Student ID (unique number): ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("First name: ");
            String first_name = scanner.nextLine();

            System.out.print("Middle initial: ");
            String initial = scanner.nextLine();

            System.out.print("Last name: ");
            String last_name = scanner.nextLine();

            System.out.print("Age: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Gender: ");
            String gender = scanner.nextLine();

            System.out.print("Course: ");
            String course = scanner.nextLine();

            System.out.print("Year (1-4): ");
            int year = Integer.parseInt(scanner.nextLine());

            System.out.print("Section: ");
            int section = Integer.parseInt(scanner.nextLine());

            // Build immutable Student with user-provided ID
            Student student = new Student.Builder()
                    .id(id)
                    .first_name(first_name)
                    .initial(initial)
                    .last_name(last_name)
                    .age(age)
                    .email(email)
                    .gender(gender)
                    .course(course)
                    .year(year)
                    .section(section)
                    .build();

            // Save to database
            Student saved = repo.addStudent(student);
            if (saved != null) {
                System.out.println("✓ Student saved with ID: " + saved.getId());
            } else {
                System.out.println("✗ Failed to save student (ID might already exist).");
            }

            System.out.print("\nAdd another student? (y/n): ");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("y")) {
                break;
            }
        }

        // --- Print all students after input loop ---
        System.out.println("\n========== ALL STUDENTS IN DATABASE ==========");
        List<Student> allStudents = repo.getAllStudents();
        if (allStudents.isEmpty()) {
            System.out.println("No students found.");
        } else {
            // Print formatted table
            System.out.printf("%-6s %-12s %-8s %-12s %-5s %-25s %-8s %-10s %-6s %-7s%n",
                    "ID", "First", "Init", "Last", "Age", "Email",
                    "Gender", "Course", "Year", "Section");
            System.out.println("------------------------------------------------------------------------------------------------------------------");
            for (Student s : allStudents) {
                System.out.printf("%-6d %-12s %-8s %-12s %-5d %-25s %-8s %-10s %-6d %-7d%n",
                        s.getId(),
                        s.getFirst_Name(),
                        s.getInitial(),
                        s.getLast_Name(),
                        s.getAge(),
                        s.getEmail(),
                        s.getGender(),
                        s.getCourse(),
                        s.getYear(),
                        s.getSection());
            }
        }

        repo.close();
        scanner.close();
    }
}