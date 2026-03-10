package src.test;

import src.domain.ResistanceData;
import src.domain.ResistanceCalculator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// ============================================================
// ЗАВДАННЯ 2 - Діалоговий режим: збереження/відновлення стану
// об'єкта через серіалізацію + демонстрація transient поля
// ============================================================

/**
 * <h1>MainDialog</h1>
 *
 * Інтерактивний діалог для роботи з програмою.
 *
 * <p>Демонструє особливість transient поля: після збереження
 * та відновлення об'єкта поле {@code current} скидається до 0,
 * оскільки воно оголошено як {@code transient} і не серіалізується.</p>
 */
public class MainDialog {

    /** Об'єкт калькулятора, з яким працює діалог */
    private ResistanceCalculator calculator = new ResistanceCalculator();

    /**
     * Запускає інтерактивне меню.
     * Команди вводяться з клавіатури.
     */
    private void menu() {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
        String input = null;

        do {
            // Вивід меню
            System.out.println("  +-------------------------+");
            System.out.println("  |  Resistance Calculator  |");
            System.out.println("  +-------------------------+");
            System.out.println("  (e) enter values");
            System.out.println("  (c) calculate resistance");
            System.out.println("  (v) view current data");
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

                case 'e':
                    // Введення даних користувачем
                    try {
                        System.out.print("  Enter U1 (V): ");
                        double u1 = Double.parseDouble(in.readLine().trim());
                        System.out.print("  Enter U2 (V): ");
                        double u2 = Double.parseDouble(in.readLine().trim());
                        System.out.print("  Enter U3 (V): ");
                        double u3 = Double.parseDouble(in.readLine().trim());
                        System.out.print("  Enter I  (A): ");
                        double i  = Double.parseDouble(in.readLine().trim());

                        // Створення нового об'єкта з введеними даними
                        calculator.setData(new ResistanceData(u1, u2, u3, i));
                        System.out.println("  >> Values accepted!");
                    } catch (NumberFormatException | IOException ex) {
                        System.out.println("  >> Invalid input, try again.");
                    }
                    break;

                case 'c':
                    // Обчислення та вивід результату
                    double result = calculator.calculate();
                    System.out.println("  >> R_total  = " + result + " Ohm");
                    System.out.println("  >> Octal    = " + calculator.toOctal());
                    System.out.println("  >> Hex      = " + calculator.toHex());
                    break;

                case 'v':
                    // Перегляд поточних даних
                    System.out.println("  >> " + calculator.getData());
                    break;

                case 's':
                    // Збереження - поле current (transient) не збережеться!
                    try {
                        calculator.save();
                        System.out.println("  >> Saved successfully!");
                        System.out.println("  >> Note: 'current' is transient"
                            + " — it will NOT be restored!");
                    } catch (IOException e) {
                        System.out.println("  >> Save failed: " + e.getMessage());
                    }
                    break;

                case 'r':
                    // Відновлення - демонстрація скидання transient поля
                    try {
                        calculator.restore();
                        System.out.println("  >> Restored successfully!");
                        System.out.println("  >> " + calculator.getData());
                        System.out.println("  >> current after restore = "
                            + calculator.getData().getCurrent()
                            + "  <-- transient field, reset to 0!");
                    } catch (Exception e) {
                        System.out.println("  >> Restore failed: " + e.getMessage());
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