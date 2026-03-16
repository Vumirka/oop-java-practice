package src.test;

import src.domain.observer.ResistanceObservable;
import src.domain.observer.AppAnnotations;
import src.domain.thread.AvgResistanceCommand;
import src.domain.thread.MinResistanceCommand;
import src.domain.thread.MaxResistanceCommand;
import src.domain.thread.CommandQueue;
import src.domain.view.ViewResult;
import java.util.concurrent.TimeUnit;

/**
 * <h1>ResistanceTest</h1>
 *
 * Тестує функціональність Observer, Worker Thread команд
 * та анотацій через Reflection.
 */
public class ResistanceTest {

    private static final int    N     = 20;
    private static ViewResult   view  = new ViewResult();

    /**
     * Точка входу тестів.
     * @param args аргументи (не використовуються)
     */
    public static void main(String[] args) {
        view.viewInit();

        System.out.println("\n  +--------------------------------+");
        System.out.println("  |       Running tests...         |");
        System.out.println("  +--------------------------------+");

        testObserver();
        testReflection();
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
     * Тест 1 - Observer: перевірка сповіщення спостерігачів.
     */
    private static void testObserver() {
        System.out.println("\n  [ Test 1 - Observer ]");

        ResistanceObservable obs = new ResistanceObservable();
        final boolean[] notified = {false};

        // Реєструємо лямбда-спостерігача
        obs.addObserver(items -> notified[0] = true);
        obs.generate(5);

        System.out.println("  Notified     : " + notified[0]);
        System.out.println("  Items count  : " + obs.getItems().size());
        boolean passed = notified[0] && obs.getItems().size() == 5;
        System.out.println("  Test         : " + (passed ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 2 - Reflection: читання RUNTIME анотації.
     */
    private static void testReflection() {
        System.out.println("\n  [ Test 2 - Reflection + Annotations ]");

        AppAnnotations.Author a =
            ResistanceObservable.class.getAnnotation(
                AppAnnotations.Author.class);

        boolean passed = a != null && !a.name().isEmpty();
        System.out.println("  @Author found : " + (a != null));
        if (a != null) {
            System.out.println("  name          : " + a.name());
            System.out.println("  date          : " + a.date());
        }
        System.out.println("  Test          : " + (passed ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 3 - MaxResistanceCommand.
     */
    private static void testMax() {
        System.out.println("\n  [ Test 3 - MaxResistanceCommand ]");
        MaxResistanceCommand cmd = new MaxResistanceCommand(view);
        cmd.execute();
        boolean passed = cmd.getResult() > -1;
        System.out.println("  Result index : " + cmd.getResult());
        System.out.println("  Test         : " + (passed ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 4 - MinResistanceCommand.
     */
    private static void testMin() {
        System.out.println("\n  [ Test 4 - MinResistanceCommand ]");
        MinResistanceCommand cmd = new MinResistanceCommand(view);
        cmd.execute();
        boolean passed = cmd.getResult() > -1;
        System.out.println("  Result index : " + cmd.getResult());
        System.out.println("  Test         : " + (passed ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 5 - AvgResistanceCommand.
     */
    private static void testAvg() {
        System.out.println("\n  [ Test 5 - AvgResistanceCommand ]");
        AvgResistanceCommand cmd = new AvgResistanceCommand(view);
        cmd.execute();
        boolean passed = cmd.getResult() > 0.0;
        System.out.printf("  Avg R_total  : %.2f Ohm%n", cmd.getResult());
        System.out.println("  Test         : " + (passed ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 6 - CommandQueue + MaxResistanceCommand.
     */
    private static void testMaxQueue() {
        System.out.println("\n  [ Test 6 - CommandQueue + MaxResistanceCommand ]");
        CommandQueue queue = new CommandQueue();
        MaxResistanceCommand cmd = new MaxResistanceCommand(view);
        queue.put(cmd);
        try {
            while (cmd.running()) TimeUnit.MILLISECONDS.sleep(100);
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
     * Тест 7 - CommandQueue + MinResistanceCommand.
     */
    private static void testMinQueue() {
        System.out.println("\n  [ Test 7 - CommandQueue + MinResistanceCommand ]");
        CommandQueue queue = new CommandQueue();
        MinResistanceCommand cmd = new MinResistanceCommand(view);
        queue.put(cmd);
        try {
            while (cmd.running()) TimeUnit.MILLISECONDS.sleep(100);
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
     * Тест 8 - CommandQueue + AvgResistanceCommand.
     */
    private static void testAvgQueue() {
        System.out.println("\n  [ Test 8 - CommandQueue + AvgResistanceCommand ]");
        CommandQueue queue = new CommandQueue();
        AvgResistanceCommand cmd = new AvgResistanceCommand(view);
        queue.put(cmd);
        try {
            while (cmd.running()) TimeUnit.MILLISECONDS.sleep(100);
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