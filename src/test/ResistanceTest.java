package src.test;

import src.domain.*;
import java.io.File;
import java.util.Stack;

// ============================================================
// Тестування команд: ScaleCommand, SortCommand, UndoCommand
// ============================================================

/**
 * <h1>ResistanceTest</h1>
 *
 * Тестує функціональність команд та Singleton.
 */
public class ResistanceTest {

    /** Похибка порівняння */
    private static final double DELTA = 1e-9;

    /**
     * Точка входу тестів.
     * @param args аргументи (не використовуються)
     */
    public static void main(String[] args) {
        System.out.println("\n  +--------------------------------+");
        System.out.println("  |       Running tests...         |");
        System.out.println("  +--------------------------------+");

        testSingleton();
        testScaleCommand();
        testSortCommand();
        testUndoCommand();

        System.out.println("  +--------------------------------+");
        System.out.println("  |        All tests done!         |");
        System.out.println("  +--------------------------------+\n");
    }

    /**
     * Тест 1 - перевірка Singleton.
     * Два виклики getInstance() мають повертати один об'єкт.
     */
    private static void testSingleton() {
        System.out.println("\n  [ Test 1 - Singleton ]");

        Application a1 = Application.getInstance();
        Application a2 = Application.getInstance();
        boolean passed = (a1 == a2);

        System.out.println("  Same instance : " + passed);
        System.out.println("  Result        : "
                + (passed ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 2 - перевірка ScaleCommand та undo.
     */
    private static void testScaleCommand() {
        System.out.println("\n  [ Test 2 - ScaleCommand + undo ]");

        ViewResult view = new ViewResult();
        view.viewInit();
        Stack<ConsoleCommand> history = new Stack<>();

        // Запам'ятовуємо перший опір
        double before = view.getItems().get(0).getTotalResistance();

        ScaleCommand scale = new ScaleCommand(view, history);
        scale.execute();

        double after = view.getItems().get(0).getTotalResistance();
        boolean scaled = Math.abs(before - after) > DELTA;

        scale.undo();
        double restored = view.getItems().get(0).getTotalResistance();
        boolean undone = Math.abs(before - restored) < DELTA;

        System.out.println("  Before scale : " + String.format("%.2f", before));
        System.out.println("  After scale  : " + String.format("%.2f", after));
        System.out.println("  After undo   : " + String.format("%.2f", restored));
        System.out.println("  Scale worked : " + (scaled ? "PASSED ✓" : "FAILED ✗"));
        System.out.println("  Undo worked  : " + (undone ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 3 - перевірка SortCommand та undo.
     */
    private static void testSortCommand() {
        System.out.println("\n  [ Test 3 - SortCommand + undo ]");

        ViewResult view = new ViewResult();
        view.viewInit();
        Stack<ConsoleCommand> history = new Stack<>();

        // Запам'ятовуємо перший елемент до сортування
        double firstBefore = view.getItems().get(0).getTotalResistance();

        SortCommand sort = new SortCommand(view, history);
        sort.execute();

        // Перевіряємо що відсортовано
        boolean sorted = true;
        for (int i = 0; i < view.getItems().size() - 1; i++) {
            if (view.getItems().get(i).getTotalResistance()
                    > view.getItems().get(i + 1).getTotalResistance()) {
                sorted = false;
                break;
            }
        }

        sort.undo();
        double firstAfterUndo = view.getItems().get(0).getTotalResistance();
        boolean undone = Math.abs(firstBefore - firstAfterUndo) < DELTA;

        System.out.println("  Sorted       : " + (sorted ? "PASSED ✓" : "FAILED ✗"));
        System.out.println("  Undo worked  : " + (undone ? "PASSED ✓" : "FAILED ✗"));
    }

    /**
     * Тест 4 - перевірка UndoCommand на порожньому стеку.
     */
    private static void testUndoCommand() {
        System.out.println("\n  [ Test 4 — UndoCommand empty stack ]");

        Stack<ConsoleCommand> history = new Stack<>();
        UndoCommand undo = new UndoCommand(history);

        // Виклик на порожньому стеку не має кидати виняток
        try {
            undo.execute();
            System.out.println("  No exception : PASSED ✓");
        } catch (Exception e) {
            System.out.println("  Exception!   : FAILED ✗ — " + e.getMessage());
        }
    }
}