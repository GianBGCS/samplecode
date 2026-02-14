import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Repository {
    private static final String DB_URL = "jdbc:sqlite:students.db";
    private Connection connection;

    public Repository() {
        try {
            this.connection = DriverManager.getConnection(DB_URL);
            createTable();
        } catch (SQLException e) {
            System.err.println("FATAL: Cannot connect to database: " + e.getMessage());
            throw new RuntimeException(e);   // crash with full stack trace
        }
    }

    private void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS students (
                id INTEGER PRIMARY KEY,        -- student ID, user-provided, NOT auto-increment
                first_name TEXT NOT NULL,
                initial TEXT NOT NULL,
                last_name TEXT NOT NULL,
                age INTEGER NOT NULL,
                email TEXT NOT NULL,
                gender TEXT NOT NULL,
                course TEXT NOT NULL,
                year INTEGER NOT NULL,
                section INTEGER NOT NULL
            )
        """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
        }
    }

    // addStudent – inserts the student with the given ID (no generated keys)
    public Student addStudent(Student student) {
        String sql = """
            INSERT INTO students(
                id, first_name, initial, last_name, age, email,
                gender, course, year, section
            ) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, student.getId());
            pstmt.setString(2, student.getFirst_Name());
            pstmt.setString(3, student.getInitial());
            pstmt.setString(4, student.getLast_Name());
            pstmt.setInt(5, student.getAge());
            pstmt.setString(6, student.getEmail());
            pstmt.setString(7, student.getGender());
            pstmt.setString(8, student.getCourse());
            pstmt.setInt(9, student.getYear());
            pstmt.setInt(10, student.getSection());

            pstmt.executeUpdate();
            return student;   // student is already immutable with its ID
        } catch (SQLException e) {
            // Handle duplicate ID or other errors
            System.out.println("Insert failed: " + e.getMessage());
            return null;
        }
    }

    // getAllStudents – returns list of all students
    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student s = new Student.Builder()
                        .id(rs.getInt("id"))
                        .first_name(rs.getString("first_name"))
                        .initial(rs.getString("initial"))
                        .last_name(rs.getString("last_name"))
                        .age(rs.getInt("age"))
                        .email(rs.getString("email"))
                        .gender(rs.getString("gender"))
                        .course(rs.getString("course"))
                        .year(rs.getInt("year"))
                        .section(rs.getInt("section"))
                        .build();
                students.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Read failed: " + e.getMessage());
        }
        return students;
    }

    // getStudentById – now looks up by the student ID (the primary key)
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Student.Builder()
                            .id(rs.getInt("id"))
                            .first_name(rs.getString("first_name"))
                            .initial(rs.getString("initial"))
                            .last_name(rs.getString("last_name"))
                            .age(rs.getInt("age"))
                            .email(rs.getString("email"))
                            .gender(rs.getString("gender"))
                            .course(rs.getString("course"))
                            .year(rs.getInt("year"))
                            .section(rs.getInt("section"))
                            .build();
                }
            }
        } catch (SQLException e) {
            System.out.println("Read by ID failed: " + e.getMessage());
        }
        return null;
    }

    // clearAllStudents – deletes every row
    public void clearAllStudents() {
        String sql = "DELETE FROM students";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("All students deleted.");
        } catch (SQLException e) {
            System.out.println("Clear failed: " + e.getMessage());
        }
    }

    // close – closes the persistent connection
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Close failed: " + e.getMessage());
        }
    }
    }
}

