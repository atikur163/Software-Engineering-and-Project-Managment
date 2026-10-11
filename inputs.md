# Test Inputs

| Test | Threads | Increments per thread | Expected count |
|------|--------:|----------------------:|---------------:|
| TC1 | 1   | 1,000  | 1,000     |
| TC2 | 2   | 10,000 | 20,000    |
| TC3 | 5   | 10,000 | 50,000    |
| TC4 | 10  | 50,000 | 500,000   |
| TC5 | 20  | 50,000 | 1,000,000 |
| TC6 | 50  | 50,000 | 2,500,000 |
| TC7 | 100 | 50,000 | 5,000,000 |

Each test is run once with `true` (thread-safe) and at least 5 times with `false` (unsynchronized).
