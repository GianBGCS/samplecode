public final class Student {
    // ---------- All fields are final – exactly as in your diagram ----------
    private final int id;               // student ID, provided by user (e.g. 3111)
    private final String first_name;
    private final String initial;       // middle initial
    private final String last_name;
    private final int age;
    private final String email;
    private final String gender;
    private final String course;
    private final int year;
    private final int section;

    // ---------- Private constructor (takes Builder) ----------
    private Student(Builder builder) {
        this.id = builder.id;
        this.first_name = builder.first_name;
        this.initial = builder.initial;
        this.last_name = builder.last_name;
        this.age = builder.age;
        this.email = builder.email;
        this.gender = builder.gender;
        this.course = builder.course;
        this.year = builder.year;
        this.section = builder.section;
    }

    // ---------- Getters – exact names from diagram ----------
    public int getId() {
        return id;
    }

    public String getFirst_Name() {
        return first_name;
    }

    public String getInitial() {
        return initial;
    }

    public String getLast_Name() {
        return last_name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getCourse() {
        return course;
    }

    public int getYear() {
        return year;
    }

    public int getSection() {
        return section;
    }

    // ---------- toString() – as required ----------
    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s %s %s', age=%d, email='%s', gender='%s', course='%s', year=%d, section=%d}",
                id, first_name, initial, last_name, age, email, gender, course, year, section);
    }

    // ---------- Static nested Builder ----------
    public static class Builder {
        private int id;              // now REQUIRED, no default
        private String first_name;
        private String initial;
        private String last_name;
        private int age;
        private String email;
        private String gender;
        private String course;
        private int year;
        private int section;

        // Builder methods – each returns this for chaining
        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder first_name(String first_name) {
            this.first_name = first_name;
            return this;
        }

        public Builder initial(String initial) {
            this.initial = initial;
            return this;
        }

        public Builder last_name(String last_name) {
            this.last_name = last_name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder course(String course) {
            this.course = course;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder section(int section) {
            this.section = section;
            return this;
        }

        public Student build() {
            // You can add validation here (e.g., id > 0, age > 0, etc.)
            return new Student(this);
        }
    }
}
