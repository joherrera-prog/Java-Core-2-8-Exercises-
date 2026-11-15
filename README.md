# Java Core · Section 8

Open the root `pom.xml` in IntelliJ IDEA and select JDK 21. To run all included tests from the project root:

```bash
mvn test
```

| Topic | Exercises |
| --- | --- |
| Generics | 1-3-0 Hello Generics, 1-3-1 Crazy Generics, 1-3-2 Heterogeneous Max Holder |
| Functional programming | 5-1-1 Crazy Lambdas, 5-2-1 Crazy Streams, 5-3-1 Crazy Optionals |
| Regular expressions | 3-6-3 Crazy Regex |
| Iterators | Business Days, Zipping, Buffering |

The Bobocode exercises retain their original Maven module layout, tests, and test resources. The iterator implementations and their JUnit tests are in the `iterators` module. The Maven reactor includes these exercises and their shared utilities.

Original exercises by [Bobocode](https://github.com/bobocode-projects/java-fundamentals-exercises). See `LICENSE`.
