## Problem 1: Demonstrate Stack and Heap Memory

### Task
Create a `Student` class with an instance variable `age` and a method `display()`. Inside `display()`, declare a local variable `marks`. In `main()`, create a `Student` object and display the instance variable, the local variable and the object identity using `System.identityHashCode()`.

### Sample Input
```
Age: 20
Marks: 85
```

### Sample Output
```
Student Object Created
Object Identity Hash Code: 12345678
Instance Variable (age): 20
Local Variable (marks): 85
```

### Mark Distribution
- Define the `Student` class and instance variable
- Declare and display the local variable inside `display()`
- Create the object and display its identity hash code

### Solution: `Student.java`
```java
public class Student {
    int age;                       // instance variable -> stored in the heap, inside the object

    Student(int age) {
        this.age = age;
    }

    void display() {
        int marks = 85;            // local variable -> stored in the stack frame of display()
        System.out.println("Instance Variable (age): " + age);
        System.out.println("Local Variable (marks): " + marks);
    }

    public static void main(String[] args) {
        Student s = new Student(20);   // 's' (reference) -> stack, object -> heap

        System.out.println("Student Object Created");
        System.out.println("Object Identity Hash Code: " + System.identityHashCode(s));
        s.display();
    }
}
```

---

## Problem 2: Stack Frames, Heap Objects and Inner Classes

### Task
Create an `Employee` class with an instance variable `salary` and an instance method `display()`. Inside `display()`, declare a local variable `bonus` and a **local inner class** named `Report` with a method `show()`.

The program should:
1. Create an `Employee` object and display its identity hash code.
2. Display the instance variable `salary` and the local variable `bonus`.
3. Create a `Report` object and display its identity hash code and a message from `show()`.

### Sample Input
```
Enter salary: 30000
Enter bonus: 5000
```

### Sample Output
```
Employee Object Created
Employee Identity Hash Code: 12345678

Inside display() Method:
Salary (Instance Variable): 30000
Bonus (Local Variable): 5000

Report Object Created
Report Identity Hash Code: 87654321
Report: Employee salary is 30000

Memory Concept:
Employee object -> Heap
Report object -> Heap
salary -> Instance field
bonus -> Local variable in display()
show() -> Executes in its own method frame
```

### Solution: `Employee.java`
```java
import java.util.Scanner;

public class Employee {
    int salary;                    // instance variable -> heap, inside the Employee object

    Employee(int salary) {
        this.salary = salary;
    }

    void display(int bonusInput) {
        int bonus = bonusInput;    // local variable -> stack frame of display()

        System.out.println("\nInside display() Method:");
        System.out.println("Salary (Instance Variable): " + salary);
        System.out.println("Bonus (Local Variable): " + bonus);

        // Local inner class declared inside a method
        class Report {
            void show() {
                // show() runs in its own stack frame
                System.out.println("Report: Employee salary is " + salary);
            }
        }

        Report r = new Report();   // Report object -> heap
        System.out.println("\nReport Object Created");
        System.out.println("Report Identity Hash Code: " + System.identityHashCode(r));
        r.show();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter salary: ");
        int salary = sc.nextInt();
        System.out.print("Enter bonus: ");
        int bonus = sc.nextInt();

        Employee emp = new Employee(salary);   // 'emp' reference -> stack, object -> heap
        System.out.println("\nEmployee Object Created");
        System.out.println("Employee Identity Hash Code: " + System.identityHashCode(emp));

        emp.display(bonus);

        System.out.println("\nMemory Concept:");
        System.out.println("Employee object -> Heap");
        System.out.println("Report object -> Heap");
        System.out.println("salary -> Instance field");
        System.out.println("bonus -> Local variable in display()");
        System.out.println("show() -> Executes in its own method frame");

        sc.close();
    }
}
```

---

## Key Concepts

| Item | Where it lives |
|------|----------------|
| Objects (`Student`, `Employee`, `Report`) | Heap |
| Instance variables (`age`, `salary`) | Heap, inside their object |
| Local variables (`marks`, `bonus`) | Stack, in the method's frame |
| Object references (`s`, `emp`, `r`) | Stack (as local variables) |
| Method calls (`display()`, `show()`) | Each call gets its own stack frame |
