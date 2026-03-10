package src.test;

import src.domain.View;
import src.domain.Viewable;
import src.domain.ViewableResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// ============================================================
// Діалоговий інтерфейс користувача
// Використовує Factory Method для отримання об'єкта View
// ============================================================

/**
 * <h1>MainDialog</h1>
 *
 * Головний клас програми з інтерактивним меню.
 * Використовує патерн Factory Method для створення
 * об'єкта відображення через {@link domain.Viewable}.
 *
 * @author Rizhkevi Viktoriia
 * @version 1.0
 */
public class MainDialog {

    /**
     * Об'єкт View, отриманий через Factory Method.
     */
    private View view;

    /**
     * Конструктор — ініціалізує view через фабричний метод.
     */
    public MainDialog() {
        // Використовуємо інтерфейс Viewable — це і є Factory Method
        Viewable factory = new ViewableResult();
        this.view = factory.getView();
    }

    /**
     * Запускає інтерактивне меню.
     */
    private void menu() {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
        String input = null;

        do {
            System.out.println("  +-----------------------------+");
            System.out.println("  |   Resistance Calculator     |");
            System.out.println("  +-----------------------------+");
            System.out.println("  (g) generate new data");
            System.out.println("  (v) view results");
            System.out.println("  (s) save to file");
            System.out.println("  (r) restore from file");
            System.out.println("  (q) quit");
            System.out.print("  >> ");

            try {
                input = in.readLine().trim();
            } catch (IOException e) {
                System.out.println("  Input error: " + e.getMessage());
                break;
            }

            if (input.isEmpty()) continue;

            switch (input.charAt(0)) {

                case 'g':
                    // Генерація нових випадкових даних
                    view.viewInit();
                    view.viewShow();
                    break;

                case 'v':
                    // Перегляд поточної колекції
                    view.viewShow();
                    break;

                case 's':
                    // Збереження колекції у файл
                    try {
                        view.viewSave();
                    } catch (IOException e) {
                        System.out.println("  >> Save error: " + e.getMessage());
                    }
                    break;

                case 'r':
                    // Відновлення колекції з файлу
                    try {
                        view.viewRestore();
                        view.viewShow();
                    } catch (Exception e) {
                        System.out.println("  >> Restore error: " + e.getMessage());
                    }
                    break;

                case 'q':
                    System.out.println("  >> Bye! ^^");
                    break;

                default:
                    System.out.println("  >> Unknown command.");
            }

        } while (input.isEmpty() || input.charAt(0) != 'q');
    }

    /**
     * Точка входу програми.
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        new MainDialog().menu();
    }
}