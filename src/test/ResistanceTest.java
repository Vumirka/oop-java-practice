package src.test;

import src.domain.*;
import java.util.concurrent.TimeUnit;

// ============================================================
// Тестування Worker Thread команд
// ============================================================

/**
 * <h1>ResistanceTest</h1>
 *
 * Тестує функціональність команд Worker Thread:
 * MaxResistanceCommand, MinResistanceCommand, AvgResistanceCommand,
 * CommandQueue з кожним типом команди.
 */
public class ResistanceTest {

    /** Розмір тестової колекції */
    private static final int N = 20;

    /** Тестова колекція */
    private static ViewResult view = new ViewResult();

    /**
     * Точка входу тестів.
     * @param args аргументи (не використовуються)
     */
    public static void main(String[] args) {
        // Ініціалізуємо колекцію один раз
        view.viewInit();

        System.out.println("\n  +--------------------------------+");
        System.out.println("  |       Running tests...         |");
        System.out.println("  +--------------------------------+");

        testMax();
        testMin();
        testAvg();
        testMaxQueue();
        testMinQueue();
        testAvgQueue();

        System.out.println("\n  +--------------------------------+");
        System.out.println("  |        All tests done!         |");
        System.out.println("  +--------------------------------+\n");
    }

    // -------------------------------------------------------

    /**
     * Тест 1 - MaxResistanceCommand: пошук максимуму.
     */
    private static void testMax() {
        System.out.println("\n  [ Test 1 - MaxResistanceCommand ]");
        MaxResistanceCommand cmd = new MaxResistanceCommand(view);
        cmd.execute();
        boolean passed = cmd.getResult() > -1;
        System.out.println("  Result index : " + cmd.getResult());
        System.out.println("  Test         : " + (passed ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 2 - MinResistanceCommand: пошук мінімуму.
     */
    private static void testMin() {
        System.out.println("\n  [ Test 2 - MinResistanceCommand ]");
        MinResistanceCommand cmd = new MinResistanceCommand(view);
        cmd.execute();
        boolean passed = cmd.getResult() > -1;
        System.out.println("  Result index : " + cmd.getResult());
        System.out.println("  Test         : " + (passed ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 3 - AvgResistanceCommand: середнє значення.
     */
    private static void testAvg() {
        System.out.println("\n  [ Test 3 - AvgResistanceCommand ]");
        AvgResistanceCommand cmd = new AvgResistanceCommand(view);
        cmd.execute();
        boolean passed = cmd.getResult() > 0.0;
        System.out.printf("  Avg R_total  : %.2f Ohm%n", cmd.getResult());
        System.out.println("  Test         : " + (passed ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 4 - CommandQueue + MaxResistanceCommand.
     */
    private static void testMaxQueue() {
        System.out.println("\n  [ Test 4 - CommandQueue + MaxResistanceCommand ]");
        CommandQueue queue = new CommandQueue();
        MaxResistanceCommand cmd = new MaxResistanceCommand(view);
        queue.put(cmd);
        try {
            while (cmd.running()) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            queue.shutdown();
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        boolean passed = cmd.getResult() > -1;
        System.out.println("  Result index : " + cmd.getResult());
        System.out.println("  Test         : " + (passed ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 5 - CommandQueue + MinResistanceCommand.
     */
    private static void testMinQueue() {
        System.out.println("\n  [ Test 5 - CommandQueue + MinResistanceCommand ]");
        CommandQueue queue = new CommandQueue();
        MinResistanceCommand cmd = new MinResistanceCommand(view);
        queue.put(cmd);
        try {
            while (cmd.running()) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            queue.shutdown();
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        boolean passed = cmd.getResult() > -1;
        System.out.println("  Result index : " + cmd.getResult());
        System.out.println("  Test         : " + (passed ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 6 - CommandQueue + AvgResistanceCommand.
     */
    private static void testAvgQueue() {
        System.out.println("\n  [ Test 6 - CommandQueue + AvgResistanceCommand ]");
        CommandQueue queue = new CommandQueue();
        AvgResistanceCommand cmd = new AvgResistanceCommand(view);
        queue.put(cmd);
        try {
            while (cmd.running()) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            queue.shutdown();
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        boolean passed = cmd.getResult() > 0.0;
        System.out.printf("  Avg R_total  : %.2f Ohm%n", cmd.getResult());
        System.out.println("  Test         : " + (passed ? "PASSED ✓" : "FAILED ✗"));
    }
}
