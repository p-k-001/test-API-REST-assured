package api.model;

public class User {
    private String name;
    private String email;
    private int age;
    private String role;

    public User(String name, String email, int age, String role){
        this.name = name;
        this.email = email;
        this.age = age;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
