package src.test;

import src.domain.ResistanceData;
import src.domain.ViewResult;
import java.io.File;
import java.util.ArrayList;

// ============================================================
// Тестування колекції, обчислень та серіалізації
// ============================================================

/**
 * <h1>ResistanceTest</h1>
 *
 * Тестує функціональність класу {@link ViewResult}:
 * коректність обчислень, збереження та відновлення колекції.
 */
public class ResistanceTest {

    /** Допустима похибка при порівнянні дробових чисел */
    private static final double DELTA = 1e-9;

    /**
     * Точка входу - запускає всі тести.
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        System.out.println("\n  +-----------------------------+");
        System.out.println("  |      Running tests...       |");
        System.out.println("  +-----------------------------+");

        testCollection();
        testSerialization();
        testComputation();

        System.out.println("  +-----------------------------+");
        System.out.println("  |       All tests done!       |");
        System.out.println("  +-----------------------------+\n");
    }

    /**
     * Тест 1 - перевірка що колекція ініціалізується правильно.
     * Після viewInit() колекція має містити 5 елементів з порахованим опором.
     */
    private static void testCollection() {
        System.out.println("\n  [ Test 1 - Collection init ]");

        ViewResult view = new ViewResult();
        view.viewInit();

        ArrayList<ResistanceData> items = view.getItems();
        boolean passed = items.size() == 5
                && items.stream().allMatch(d -> d.getTotalResistance() > 0);

        System.out.println("  Collection size : " + items.size() + " (expected: 5)");
        System.out.println("  All R > 0       : "
                + items.stream().allMatch(d -> d.getTotalResistance() > 0));
        System.out.println("  Result          : " + (passed ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 2 — перевірка серіалізації та десеріалізації колекції.
     * Кількість і значення елементів мають збігатися після відновлення.
     */
    private static void testSerialization() {
        System.out.println("\n  [ Test 2 - Serialization ]");

        ViewResult view1 = new ViewResult();
        ViewResult view2 = new ViewResult();
        view1.viewInit();

        try {
            view1.viewSave();
            view2.viewRestore();
        } catch (Exception e) {
            System.out.println("  ERROR: " + e.getMessage());
            return;
        }

        boolean sizeOk = view1.getItems().size() == view2.getItems().size();
        boolean dataOk = view1.getItems().containsAll(view2.getItems());
        boolean passed = sizeOk && dataOk;

        System.out.println("  Size match  : " + sizeOk);
        System.out.println("  Data match  : " + dataOk);
        System.out.println("  Result      : " + (passed ? "PASSED ✓" : "FAILED ✗"));

        new File("results.ser").delete();
    }

    /**
     * Тест 3 - перевірка правильності обчислення опору.
     * U1=10V, U2=20V, U3=30V, I=2A → R_total=30 Ohm
     */
    private static void testComputation() {
        System.out.println("\n  [ Test 3 - Computation ]");

        ResistanceData data = new ResistanceData(10, 20, 30, 2);
        src.domain.ResistanceCalculator calc = new src.domain.ResistanceCalculator(data);
        double result = calc.calculate();

        boolean passed = Math.abs(result - 30.0) < DELTA;
        System.out.println("  Expected : 30.0 Ohm");
        System.out.println("  Got      : " + result + " Ohm");
        System.out.println("  Result   : " + (passed ? "PASSED ✓" : "FAILED ✗"));
    }
}
