
import java.util.Scanner;

class Student {
    int age; // Instance variable

    void display(int m) {
        int marks = m; // Local variable

        System.out.println("Instance Variable (age): " + age);
        System.out.println("Local Variable (marks): " + marks);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Age: ");
        int age = sc.nextInt();

        System.out.print("Marks: ");
        int marks = sc.nextInt();

        Student s = new Student();
        s.age = age;

        System.out.println("Student Object Created");
        System.out.println("Object Identity Hash Code: "
                + System.identityHashCode(s));

        s.display(marks);

        sc.close();
    }
}
