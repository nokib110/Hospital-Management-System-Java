// Abstract class cannot be instantiated directly
public abstract class Person {
    private int id;
    private String name;
    private int age;

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }

    // Abstract method to force subclasses to implement their own display
    public abstract String getDetails();
}