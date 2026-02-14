import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Repository repo = new Repository();

        boolean menu = true;


        while (menu) {

            try {
                System.out.println("\nMenu \n[1] See saved students \n[2] Get Student by ID \n[3] Add students \n[4] Clear all students saved \n[5] Quit"
                );
                System.out.print("Choose an option: ");
                int option = scanner.nextInt();
                scanner.nextLine();


                switch (option) {
                    case 1:
                        List<Student> allStudents = repo.getAllStudents();
                        System.out.println("\n========== ALL STUDENTS IN DATABASE ==========");
                        if (allStudents.isEmpty()) {
                            System.out.println("No students saved.");
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
                        break;

                    case 2:
                        System.out.print("\nEnter a student ID to view details (or 0 to skip): ");
                        int idToView = scanner.nextInt();
                        scanner.nextLine();
                        if (idToView != 0) {
                            Student s = repo.getStudentById(idToView);
                            if (s != null) {
                                System.out.printf("%-6s %-12s %-8s %-12s %-5s %-25s %-8s %-10s %-6s %-7s%n",
                                        "ID", "First", "Init", "Last", "Age", "Email",
                                        "Gender", "Course", "Year", "Section");
                                System.out.println("------------------------------------------------------------------------------------------------------------------");

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
                            } else {
                                System.out.println("No student found.");
                            }
                        }
                        break;

                    case 3:
                        System.out.println("\n=== Student Database Entry ===");
                        System.out.println("Each student has 10 details, including a unique student ID (e.g., 3111).");
                        System.out.println("You can add multiple students.\n");

                        while (true) {
                            try {
                                System.out.println("--- New Student ---");

                                System.out.print("Student ID (unique number): ");
                                int id = scanner.nextInt();
                                scanner.nextLine();

                                System.out.print("First name: ");
                                String first_name = scanner.nextLine();

                                System.out.print("Middle initial: ");
                                String initial = scanner.nextLine();

                                System.out.print("Last name: ");
                                String last_name = scanner.nextLine();

                                System.out.print("Age: ");
                                int age = scanner.nextInt();
                                scanner.nextLine();

                                System.out.print("Email: ");
                                String email = scanner.nextLine();

                                System.out.print("Gender: ");
                                String gender = scanner.nextLine();

                                System.out.print("Course: ");
                                String course = scanner.nextLine();

                                System.out.print("Year (1-4): ");
                                int year = scanner.nextInt();
                                scanner.nextLine();

                                System.out.print("Section: ");
                                int section = scanner.nextInt();
                                scanner.nextLine();


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


                            Student saved = repo.addStudent(student);
                            if (saved != null) {
                                System.out.println("✓ Student saved with ID: " + saved.getId());
                            } else {
                                System.out.println("✗ Failed to save student (ID might already exist).");
                            }
                            } catch (InputMismatchException e) {
                                System.out.println("Enter correct value. ");
                            }

                            System.out.print("\nAdd another student? (y/n): ");
                            String choice = scanner.nextLine();
                            if (!choice.equalsIgnoreCase("y")) {
                                break;
                            }

                        }
                        break;

                    case 4:
                        System.out.print("Clear all existing students? (y/n): ");
                        String clearChoice = scanner.nextLine();
                        if (clearChoice.equalsIgnoreCase("y")) {
                            repo.clearAllStudents();
                        }
                        break;

                    case 5:
                        menu = false;
                        repo.close();
                        break;

                    default:
                        System.out.println("Input the correct number");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter the correct value.");
                scanner.nextLine();
            }
        }
        repo.close();
        scanner.close();
    }
}
