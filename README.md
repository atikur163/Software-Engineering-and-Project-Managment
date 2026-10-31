
# Lab_Thread_Counter
**Problem SEPM-001:** Comparing Static and Non-Static Variables in Multithreading

> **Name:** _<your name>_  **ID:** _<your id>_  **Course/Section:** _<course>_

## Directory contents

| File | Description |
|------|-------------|
| `handwritten_code.pdf` / `.jpg` | Scan/photo of the handwritten Java source (`Firstname_Thread.java`) |
| `inputs.md` | Inputs used for each test case (below) |
| `outputs/` | Screenshots of actual program runs (one per test case and mode) |
| `README.md` | This file: expected results, result tables, analysis, conclusion |

## 1. Inputs and expected results

Formulas (N = threads, M = increments per thread):

- Expected count = N × M
- Absolute difference = |S − I|, where S = static counter, I = sum of non-static counters
- Percentage difference = |S − I| / I × 100 (relative to the non-static count)

| Test | Threads (N) | Increments per thread (M) | Expected count | Command (safe) | Command (unsafe) |
|------|------------:|--------------------------:|---------------:|----------------|------------------|
| TC1 | 1   | 1,000  | 1,000     | `java Firstname_Thread 1 1000 true`    | `java Firstname_Thread 1 1000 false` |
| TC2 | 2   | 10,000 | 20,000    | `java Firstname_Thread 2 10000 true`   | `java Firstname_Thread 2 10000 false` |
| TC3 | 5   | 10,000 | 50,000    | `java Firstname_Thread 5 10000 true`   | `java Firstname_Thread 5 10000 false` |
| TC4 | 10  | 50,000 | 500,000   | `java Firstname_Thread 10 50000 true`  | `java Firstname_Thread 10 50000 false` |
| TC5 | 20  | 50,000 | 1,000,000 | `java Firstname_Thread 20 50000 true`  | `java Firstname_Thread 20 50000 false` |
| TC6 | 50  | 50,000 | 2,500,000 | `java Firstname_Thread 50 50000 true`  | `java Firstname_Thread 50 50000 false` |
| TC7 | 100 | 50,000 | 5,000,000 | `java Firstname_Thread 100 50000 true` | `java Firstname_Thread 100 50000 false` |

### Expected behavior: Experiment A (thread-safe, `AtomicLong`)

| Test | Static count (safe) | Non-static total | Absolute difference | Expected difference (%) |
|------|--------------------:|-----------------:|--------------------:|------------------------:|
| TC1 | 1,000     | 1,000     | 0 | 0% |
| TC2 | 20,000    | 20,000    | 0 | 0% |
| TC3 | 50,000    | 50,000    | 0 | 0% |
| TC4 | 500,000   | 500,000   | 0 | 0% |
| TC5 | 1,000,000 | 1,000,000 | 0 | 0% |
| TC6 | 2,500,000 | 2,500,000 | 0 | 0% |
| TC7 | 5,000,000 | 5,000,000 | 0 | 0% |

### Expected behavior: Experiment B (unsynchronized static `long`)

- **Non-static total:** always equals the expected count (each thread has its own counter, so no sharing).
- **Static count:** less than or equal to the expected count. Lost updates make it lower; the exact value cannot be predicted and varies from run to run.
- **TC1 (1 thread):** no concurrency, so the difference should be 0%.
- **Trend:** the difference is not guaranteed to grow steadily with thread count. It depends on scheduling, CPU cores, and JVM conditions.

## 2. Result recording sheet (replace with YOUR actual outputs)

Run Experiment B at least 5 times per thread count. Paste real values only; do not edit program output.

| Threads | Expected count | Run | Static count (unsafe) | Non-static total | Abs. difference | Difference (%) |
|--------:|---------------:|----:|----------------------:|-----------------:|----------------:|---------------:|
| 1   | 1,000     | 1 |  |  |  |  |
| 1   | 1,000     | 2 |  |  |  |  |
| 1   | 1,000     | 3 |  |  |  |  |
| 1   | 1,000     | 4 |  |  |  |  |
| 1   | 1,000     | 5 |  |  |  |  |
| 2   | 20,000    | 1–5 |  |  |  |  |
| 5   | 50,000    | 1–5 |  |  |  |  |
| 10  | 500,000   | 1–5 |  |  |  |  |
| 20  | 1,000,000 | 1–5 |  |  |  |  |
| 50  | 2,500,000 | 1–5 |  |  |  |  |
| 100 | 5,000,000 | 1–5 |  |  |  |  |

Average summary:

| Threads | Expected count | Avg static (unsafe) | Non-static total | Avg abs. difference | Avg difference (%) |
|--------:|---------------:|--------------------:|-----------------:|--------------------:|-------------------:|
| 1   | 1,000     |  | 1,000     |  |  |
| 2   | 20,000    |  | 20,000    |  |  |
| 5   | 50,000    |  | 50,000    |  |  |
| 10  | 500,000   |  | 500,000   |  |  |
| 20  | 1,000,000 |  | 1,000,000 |  |  |
| 50  | 2,500,000 |  | 2,500,000 |  |  |
| 100 | 5,000,000 |  | 5,000,000 |  |  |

## 3. Analysis questions

1. **Static vs non-static:** A static variable belongs to the class, with one copy shared by all instances. A non-static variable belongs to each object, so every object has its own copy.
2. **Why threads share the static counter:** There is only one copy in the class, so every `CounterTask` updates the same memory location.
3. **Why each thread has its own non-static counter:** Each thread gets its own `CounterTask` object, and each object holds its own instance field.
4. **Why `join()`:** Without it, the main thread may read the counters while threads are still running, giving incomplete totals. `join()` waits for every thread to finish first.
5. **Why unsafe static count can be lower:** `count++` is read, add, write (not atomic). Two threads can read the same value and both write back the same result, so one increment is lost.
6. **Does more threads always mean a larger percentage difference?** No. It depends on scheduling, core count, and timing. _Support this with your own observations._
7. **Why runs differ:** Thread interleaving is decided by the OS scheduler and varies each run, so the number of lost updates changes.
8. **What `AtomicLong` changes:** `incrementAndGet()` performs the read-modify-write as one atomic operation, so no updates are lost.
9. **Sharing one instance counter too:** Make all threads use the same single `CounterTask` object (one shared object, multiple `Thread`s), and protect the field with `synchronized` or `AtomicLong`.

## 4. Observation and conclusion (complete using your real data)

_Observation:_ Describe how the unsafe difference changed from 1 to 100 threads in your runs, and mention the variation between the 5 repeated runs.

_Conclusion:_ Static versus non-static describes **ownership**, not thread-safety. A static variable is shared across instances, but concurrent updates still need synchronization or atomic operations. The thread-safe experiment is the control case (0% difference). The unsynchronized experiment shows lost updates, but the percentage difference does not necessarily rise steadily with thread count.
