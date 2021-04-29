# Implementation of the Pohlig–Hellman algorithm for computing discrete logarithms with a simple example.

## Run executable (requires Java 8)

```
java -jar pohlig-hellman.jar
```

## Build & run

Install SDKMAN, following any instructions:

```
curl -s https://get.sdkman.io | bash
```

Open a new terminal and install Kotlin:

```
sdk install kotlin
```

Open a new terminal, compile and run:

```
kotlinc util.kt algorithm.kt main.kt -include-runtime -d out.jar
java -jar out.jar
```
