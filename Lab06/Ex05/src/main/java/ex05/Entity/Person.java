package ex05.Entity;

public class Person {
    private String name;
    private String gender;
    private int age;

    public Person(String name, String gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("Person[name=%s, gender=%s, age=%d]", name, gender, age);
    }
}