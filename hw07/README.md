# HW07 — Patterns

### Memento
- [`AtmState.java`](src/main/java/org/aburavov/otus/java/professional/hw07/atm/AtmState.java)
- [`Atm.java`](src/main/java/org/aburavov/otus/java/professional/hw07/atm/Atm.java) — stores history and can restore it.
- [`AtmHistory.java`](src/main/java/org/aburavov/otus/java/professional/hw07/atm/AtmHistory.java)

### Composite
- [`BalanceHolder.java`](src/main/java/org/aburavov/otus/java/professional/hw07/atm/BalanceHolder.java) common interface for `Atm` and `Department` with `getBalance()` method.
- [`Atm.java`](src/main/java/org/aburavov/otus/java/professional/hw07/atm/Atm.java) and [`Department.java`](src/main/java/org/aburavov/otus/java/professional/hw07/atm/Department.java) — implementations of the composite pattern.

### Command
- [`Command.java`](src/main/java/org/aburavov/otus/java/professional/hw07/atm/Command.java)
- [`RestoreAllCommand.java`](src/main/java/org/aburavov/otus/java/professional/hw07/atm/RestoreAllCommand.java)
- [`Department.java`](src/main/java/org/aburavov/otus/java/professional/hw07/atm/Department.java) — invoker of the command.

### Builder
- [`AtmBuilder.java`](src/main/java/org/aburavov/otus/java/professional/hw07/atm/AtmBuilder.java)

### Factory
- [`AtmFactory.java`](src/main/java/org/aburavov/otus/java/professional/hw07/atm/AtmFactory.java)
