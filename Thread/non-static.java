public class ThreadMain {
    public static void main(String[] args) {

        CookingTask task1 = new CookingTask("Cooking");
        CookingTask task2 = new CookingTask("Washing");
        CookingTask task3 = new CookingTask("Cleaning");

        task1.start();
        task2.start();
        task3.start();

        System.out.println("All tasks started...");
    }
}

class CookingTask extends Thread {

    private String taskName;

    public CookingTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {

        long startTime = System.currentTimeMillis();

        while (true) {

            System.out.println(
                    Thread.currentThread().getName()
                    + " - Running: " + taskName
            );

            // Wait 1 second before next iteration
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(taskName + " interrupted.");
                break;
            }

            // Stop after 10 seconds
            if (System.currentTimeMillis() - startTime >= 10_000) {
                break;
            }
        }

        System.out.println(taskName + " finished.");
    }
}


// output :
Thread-0 - Running: Cooking
Thread-1 - Running: Washing
Thread-2 - Running: Cleaning

Thread-0 - Running: Cooking
Thread-1 - Running: Washing
Thread-2 - Running: Cleaning

Thread-0 - Running: Cooking
Thread-1 - Running: Washing
Thread-2 - Running: Cleaning

Thread-0 - Running: Cooking
Thread-1 - Running: Washing
Thread-2 - Running: Cleaning

Thread-0 - Running: Cooking
Thread-1 - Running: Washing
Thread-2 - Running: Cleaning

Thread-0 - Running: Cooking
Thread-1 - Running: Washing
Thread-2 - Running: Cleaning

Thread-0 - Running: Cooking
Thread-1 - Running: Washing
Thread-2 - Running: Cleaning

Thread-0 - Running: Cooking
Thread-1 - Running: Washing
Thread-2 - Running: Cleaning

Thread-0 - Running: Cooking
Thread-1 - Running: Washing
Thread-2 - Running: Cleaning

Thread-0 - Running: Cooking
Thread-1 - Running: Washing
Thread-2 - Running: Cleaning

Cooking finished.
Washing finished.
Cleaning finished.
