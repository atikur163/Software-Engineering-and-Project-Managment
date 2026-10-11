import java.util.concurrent.atomic.AtomicLong;

// Rename the class AND this file to your own first name, e.g. Rahim_Thread.java
public class Firstname_Thread {

    // Experiment A: thread-safe static counter
    static final AtomicLong safeStatic = new AtomicLong(0);
    // Experiment B: unsynchronized static counter (race condition)
    static long unsafeStatic = 0;

    static class CounterTask implements Runnable {
        private final long increments;
        private final boolean threadSafe;
        long instanceCounter = 0; // non-static: one per object/thread

        CounterTask(long increments, boolean threadSafe) {
            this.increments = increments;
            this.threadSafe = threadSafe;
        }

        @Override
        public void run() {
            for (long i = 0; i < increments; i++) {
                if (threadSafe) {
                    safeStatic.incrementAndGet();
                } else {
                    unsafeStatic++; // not atomic: lost updates possible
                }
                instanceCounter++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            System.out.println("Usage: java Firstname_Thread <threads> <increments> <true|false>");
            return;
        }
        int n = Integer.parseInt(args[0]);
        long m = Long.parseLong(args[1]);
        boolean threadSafe = Boolean.parseBoolean(args[2]);

        CounterTask[] tasks = new CounterTask[n];
        Thread[] threads = new Thread[n];
        for (int i = 0; i < n; i++) {
            tasks[i] = new CounterTask(m, threadSafe);
            threads[i] = new Thread(tasks[i]);
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join(); // wait before reading the counters

        long staticCount = threadSafe ? safeStatic.get() : unsafeStatic;
        long nonStaticTotal = 0;
        for (CounterTask t : tasks) nonStaticTotal += t.instanceCounter;

        long expected = n * m;
        long diff = Math.abs(staticCount - nonStaticTotal);
        double pct;
        if (nonStaticTotal == 0) pct = (staticCount == 0) ? 0.0 : Double.NaN;
        else pct = (double) diff / nonStaticTotal * 100.0;

        System.out.println("Mode: " + (threadSafe ? "thread-safe (AtomicLong)" : "unsynchronized"));
        System.out.println("Threads: " + n + ", Increments per thread: " + m);
        System.out.println("Expected count: " + expected);
        System.out.println("Static count: " + staticCount);
        System.out.println("Non-static total: " + nonStaticTotal);
        System.out.println("Absolute difference: " + diff);
        System.out.println(Double.isNaN(pct) ? "Percentage difference: undefined"
                : String.format("Percentage difference: %.6f%%", pct));
    }
}
