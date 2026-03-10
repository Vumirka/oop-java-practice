package src.test;

import src.domain.ResistanceData;
import src.domain.ResistanceCalculator;
import java.io.File;
import java.io.IOException;

// ============================================================
// ЗАВДАННЯ 3 - Тестування коректності обчислень та
// серіалізації/десеріалізації
// ============================================================

/**
 * <h1>ResistanceTest</h1>
 *
 * Клас тестує три речі:
 * <ul>
 *   <li>Коректність обчислення загального опору</li>
 *   <li>Коректність серіалізації та десеріалізації</li>
 *   <li>Поведінку transient поля після десеріалізації</li>
 * </ul>
 */
public class ResistanceTest {

    /** Допустима похибка при порівнянні дробових чисел */
    private static final double DELTA = 1e-9;

    /**
     * Точка входу - запускає всі тести.
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        System.out.println("  +--------------------+");
        System.out.println("  |  Running tests...  |");
        System.out.println("  +--------------------+");

        testCalculation();
        testSerialization();
        testTransientField();
        
        System.out.println("  +-------------------+");
        System.out.println("  |  All tests done!  |");
        System.out.println("  +-------------------+\n");
    }

    /**
     * Тест 1 - перевірка правильності обчислення опору.
     * U1=10V, U2=20V, U3=30V, I=2A → очікуємо R_total=30 Ohm
     */
    private static void testCalculation() {
        System.out.println("\n  [ Test 1 - Calculation ]");

        // Створення тестових даних
        ResistanceData data = new ResistanceData(10, 20, 30, 2);
        ResistanceCalculator calc = new ResistanceCalculator(data);
        double result = calc.calculate();

        // Перевірка результату
        boolean passed = Math.abs(result - 30.0) < DELTA;
        System.out.println("  Expected : 30.0 Ohm");
        System.out.println("  Got      : " + result + " Ohm");
        System.out.println("  Octal    : " + calc.toOctal() + " (expected: 36)");
        System.out.println("  Hex      : " + calc.toHex()   + " (expected: 1E)");
        System.out.println("  Result   : " + (passed ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 2 - перевірка серіалізації та десеріалізації.
     * Значення totalResistance має зберегтися після відновлення.
     */
    private static void testSerialization() {
        System.out.println("\n  [ Test 2 - Serialization ]");

        ResistanceData data = new ResistanceData(6, 12, 18, 3);
        ResistanceCalculator calc = new ResistanceCalculator(data);
        double before = calc.calculate();

        try {
            // Зберігаємо об'єкт у файл
            calc.save();

            // Відновлюємо об'єкт з файлу
            calc.restore();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("  ERROR: " + e.getMessage());
            return;
        }

        double after = calc.getData().getTotalResistance();
        boolean passed = Math.abs(before - after) < DELTA;
        System.out.println("  Before restore : " + before + " Ohm");
        System.out.println("  After restore  : " + after  + " Ohm");
        System.out.println("  Result         : " + (passed ? "PASSED ✓" : "FAILED ✗"));

        // Видаляємо тимчасовий файл
        new File("resistance.ser").delete();
    }

    /**
     * Тест 3 - перевірка поведінки transient поля.
     * Поле current після десеріалізації має скинутися до 0.
     */
    private static void testTransientField() {
        System.out.println("\n  [ Test 3 - Transient field ]");

        ResistanceData data = new ResistanceData(5, 10, 15, 5);
        ResistanceCalculator calc = new ResistanceCalculator(data);
        calc.calculate();

        double currentBefore = calc.getData().getCurrent();

        try {
            calc.save();
            calc.restore();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("  ERROR: " + e.getMessage());
            return;
        }

        double currentAfter = calc.getData().getCurrent();
        boolean passed = currentAfter == 0.0;
        System.out.println("  Current before : " + currentBefore + " A");
        System.out.println("  Current after  : " + currentAfter
            + " A  <-- transient, reset to 0");
        System.out.println("  Result         : " + (passed ? "PASSED ✓" : "FAILED ✗"));

        new File("resistance.ser").delete();
    }
}