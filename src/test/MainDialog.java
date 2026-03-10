package src.test;

import src.domain.View;
import src.domain.Viewable;
import src.domain.ViewableTable;
import src.domain.ViewTable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// ============================================================
// Діалоговий інтерфейс - користувач задає ширину таблиці
// Демонстрація поліморфізму: змінна типу View → об'єкт ViewTable
// ============================================================

/**
 * <h1>MainDialog</h1>
 *
 * Головний клас з інтерактивним меню.
 *
 * <p>Демонстрація поліморфізму: поле {@code view} має тип {@link View},
 * але фактично посилається на {@link ViewTable}.
 * При виклику {@code view.viewShow()} виконується реалізація з {@code ViewTable}
 * - це і є динамічне призначення методів (late binding).</p>
 */
public class MainDialog {

    /**
     * Поліморфне поле - тип View, об'єкт ViewTable.
     * Це демонстрація dynamic method dispatch:
     * який метод викликати вирішується під час виконання.
     */
    private View view;

    /**
     * Конструктор - ініціалізує view через фабрику ViewableTable.
     */
    public MainDialog() {
        // Поліморфізм: Viewable factory → ViewableTable
        // View view → ViewTable (фактичний тип)
        Viewable factory = new ViewableTable();
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
            System.out.println("  +--------------------------+");
            System.out.println("  |   Resistance Table       |");
            System.out.println("  +--------------------------+");
            System.out.println("  (g) generate data");
            System.out.println("  (w) set table width");
            System.out.println("  (v) view table");
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
                    // viewInit() - поліморфний виклик → виконується ViewTable.viewInit()
                    view.viewInit();
                    view.viewShow();
                    break;

                case 'w':
                    // Користувач задає ширину таблиці
                    if (view instanceof ViewTable) {
                        try {
                            System.out.print("  Enter table width (20-100): ");
                            int w = Integer.parseInt(in.readLine().trim());
                            if (w < 20 || w > 100) {
                                System.out.println("  >> Invalid width, using 50.");
                                w = 50;
                            }
                            // overloading - init(int width)
                            ((ViewTable) view).init(w);
                            view.viewShow();
                        } catch (NumberFormatException | IOException e) {
                            System.out.println("  >> Invalid input.");
                        }
                    }
                    break;

                case 'v':
                    // viewShow() - поліморфний виклик → ViewTable.viewShow()
                    // (успадковано з ViewResult, але viewHeader/Body/Footer перевизначені)
                    view.viewShow();
                    break;

                case 's':
                    try {
                        view.viewSave();
                        System.out.println("  >> Saved!");
                    } catch (IOException e) {
                        System.out.println("  >> Save error: " + e.getMessage());
                    }
                    break;

                case 'r':
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