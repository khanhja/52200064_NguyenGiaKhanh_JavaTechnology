import java.util.List;

public class Program {
    public static void main(String[] args) {
        // Display list of student
        List<Student> list = StudentUtils.generate();
        StudentUtils.print(list);

        // Sorted list by student's name
        StudentUtils.sortByName(list);
        StudentUtils.print(list);

        // Sorted list by student's average
        StudentUtils.sortByAvg(list);
        StudentUtils.print(list);

        // Sorted list by descending student's age
        StudentUtils.sortByAgeDescending(list);
        StudentUtils.print(list);
       
        // Compute and display the average of all students
        System.out.print("The average of all students: " + StudentUtils.avg(list));
       
        // Filter good students
        System.out.println("\nThe list of good student's name: ");
        List<String> goodStudentList = StudentUtils.goodStudentName(list);
        goodStudentList.forEach(System.out::println);
    }
}
