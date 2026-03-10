package src.test;

import src.domain.ResistanceData;
import src.domain.ResistanceCalculator;
import src.domain.ViewTable;
import src.domain.ViewableTable;
import src.domain.View;
import java.io.File;
import java.util.ArrayList;

// ============================================================
// Тестування ViewTable: обчислення, серіалізація, поліморфізм
// ============================================================

/**
 * <h1>ResistanceTest</h1>
 *
 * Тестує функціональність {@link ViewTable}:
 * ширину таблиці, обчислення, серіалізацію та поліморфізм.
 */
public class ResistanceTest {

    /** Допустима похибка */
    private static final double DELTA = 1e-9;

    /**
     * Точка входу.
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        System.out.println("\n  +--------------------------------+");
        System.out.println("  |       Running tests...         |");
        System.out.println("  +--------------------------------+");

        testWidth();
        testComputation();
        testSerialization();
        testPolymorphism();

        System.out.println("  +--------------------------------+");
        System.out.println("  |        All tests done!         |");
        System.out.println("  +--------------------------------+\n");
    }

    /**
     * Тест 1 - перевірка встановлення ширини таблиці.
     * Демонстрація overloading: setWidth(int) та setWidth(int, String).
     */
    private static void testWidth() {
        System.out.println("\n  [ Test 1 - Table width (overloading) ]");

        ViewTable table = new ViewTable(30);

        // overloading метод 1
        table.setWidth(40);
        boolean t1 = table.getWidth() == 40;

        // overloading метод 2
        table.setWidth(60, "Width updated by user");
        boolean t2 = table.getWidth() == 60;

        System.out.println("  setWidth(40)          : " + table.getWidth()
            + " " + (t1 ? "PASSED ✓" : "FAILED ✗"));
        System.out.println("  setWidth(60, message) : " + table.getWidth()
            + " " + (t2 ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 2 - перевірка правильності обчислення.
     * U1=10V, U2=20V, U3=30V, I=2A → R=30 Ohm → Oct:36, Hex:1E
     */
    private static void testComputation() {
        System.out.println("\n  [ Test 2 - Computation ]");

        ResistanceData data = new ResistanceData(10, 20, 30, 2);
        ResistanceCalculator calc = new ResistanceCalculator(data);
        double result = calc.calculate();

        boolean passed = Math.abs(result - 30.0) < DELTA;
        System.out.println("  Expected : 30.0 Ohm");
        System.out.println("  Got      : " + result + " Ohm");
        System.out.println("  Octal    : " + calc.toOctal() + " (expected: 36)");
        System.out.println("  Hex      : " + calc.toHex()   + " (expected: 1E)");
        System.out.println("  Result   : " + (passed ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 3 - серіалізація та десеріалізація колекції ViewTable.
     */
    private static void testSerialization() {
        System.out.println("\n  [ Test 3 - Serialization ]");

        ViewTable t1 = new ViewTable(50);
        ViewTable t2 = new ViewTable(50);
        t1.viewInit();

        try {
            t1.viewSave();
            t2.viewRestore();
        } catch (Exception e) {
            System.out.println("  ERROR: " + e.getMessage());
            return;
        }

        boolean passed = t1.getItems().size() == t2.getItems().size()
                      && t1.getItems().containsAll(t2.getItems());

        System.out.println("  Size match : " + (t1.getItems().size() == t2.getItems().size()));
        System.out.println("  Data match : " + t1.getItems().containsAll(t2.getItems()));
        System.out.println("  Result     : " + (passed ? "PASSED ✓" : "FAILED ✗"));

        new File("results.ser").delete();
    }

    /**
     * Тест 4 - демонстрація поліморфізму (dynamic method dispatch).
     * Змінна типу View фактично містить ViewTable.
     * При виклику viewInit() виконується ViewTable.viewInit(), а не ViewResult.viewInit().
     */
    private static void testPolymorphism() {
        System.out.println("\n  [ Test 4 - Polymorphism ]");

        // Поліморфізм: тип View → фактичний об'єкт ViewTable
        View view = new ViewableTable().getView();

        // dynamic method dispatch: який viewInit() виконається?
        // → ViewTable.viewInit() (бо overriding)
        view.viewInit();

        boolean passed = view instanceof ViewTable;
        System.out.println("  view type  : " + view.getClass().getSimpleName());
        System.out.println("  is ViewTable : " + passed);
        System.out.println("  Result       : " + (passed ? "PASSED ✓" : "FAILED ✗"));
    }
}