package src.domain.view;

import src.domain.view.View;
import src.domain.core.ResistanceCalculator;
import src.domain.core.ResistanceData;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

// ============================================================
// ЗАВДАННЯ 1 - розміщення результатів у колекції
// ЗАВДАННЯ 4 - реалізація методів відображення у текстовому вигляді
// ConcreteProduct шаблону Factory Method
// ============================================================

/**
 * <h1>ViewResult</h1>
 *
 * ConcreteProduct у шаблоні Factory Method.
 * Реалізує інтерфейс {@link View}.
 *
 * <p>Зберігає результати обчислень у колекції {@link ArrayList},
 * забезпечує їх відображення, збереження та відновлення.</p>
 *
 * <p>Використовує {@link ResistanceCalculator} через агрегування
 * для обчислення загального опору провідників.</p>
 */
public class ViewResult implements View {

    /** Файл для серіалізації колекції */
    private static final String FILE_NAME = "results.ser";

    /** Кількість елементів у колекції за замовчуванням */
    private static final int DEFAULT_SIZE = 5;

    /**
     * Колекція результатів обчислень.
     * Кожен елемент - об'єкт ResistanceData з порахованим опором.
     */
    private ArrayList<ResistanceData> items = new ArrayList<>();

    /**
     * Конструктор за замовчуванням.
     * Заповнює колекцію порожніми об'єктами.
     */
    public ViewResult() {
        // Заповнюємо колекцію порожніми об'єктами
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            items.add(new ResistanceData());
        }
    }

    /**
     * Повертає колекцію результатів.
     * @return items
     */
    public ArrayList<ResistanceData> getItems() {
        return items;
    }

    /**
     * Обчислює опір для одного набору даних.
     * Формула: R = (U1 + U2 + U3) / I
     *
     * @param data об'єкт з вхідними даними
     * @return загальний опір
     */
    private double compute(ResistanceData data) {
        ResistanceCalculator calc = new ResistanceCalculator(data);
        return calc.calculate();
    }

    /**
     * Ініціалізує колекцію випадковими значеннями напруг і струму.
     * Генерує 5 різних наборів вхідних даних та обчислює опір.
     */
    @Override
    public void viewInit() {
        items.clear();
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            // Генеруємо випадкові напруги від 1 до 50 В і струм від 1 до 10 А
            double u1 = 1 + Math.random() * 49;
            double u2 = 1 + Math.random() * 49;
            double u3 = 1 + Math.random() * 49;
            double current = 1 + Math.random() * 9;

            ResistanceData data = new ResistanceData(u1, u2, u3, current);
            compute(data);
            items.add(data);
        }
    }

    /**
     * Виводить заголовок таблиці результатів.
     */
    @Override
    public void viewHeader() {
        System.out.println("\n  +-----------------------------------------------+");
        System.out.println("  |             Resistance Results                |");
        System.out.println("  +-----------------------------------------------+");
        System.out.printf("  | %-4s | %-6s | %-10s | %-6s | %-6s |%n",
                "#", "I (A)", "R_total", "Octal", "Hex");
        System.out.println("  +------+--------+------------+--------+--------+");
    }

    /**
     * Виводить тіло таблиці - рядок для кожного елемента колекції.
     */
    @Override
    public void viewBody() {
        for (int i = 0; i < items.size(); i++) {
            ResistanceData data = items.get(i);
            int r = (int) data.getTotalResistance();
            System.out.printf("  | %-4d | %-6.2f | %-10.2f | %-6s | %-6s |%n",
                    i + 1,
                    data.getCurrent(),
                    data.getTotalResistance(),
                    Integer.toOctalString(r),
                    Integer.toHexString(r).toUpperCase());
        }
    }

    /**
     * Виводить завершення таблиці.
     */
    @Override
    public void viewFooter() {
        System.out.println("  +------+--------+------------+--------+--------+");
        System.out.println("  Total records: " + items.size() + "\n");
    }

    /**
     * Виводить повну таблицю результатів.
     */
    @Override
    public void viewShow() {
        viewHeader();
        viewBody();
        viewFooter();
    }

    /**
     * Серіалізує колекцію у файл.
     * @throws IOException помилка запису
     */
    @Override
    public void viewSave() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FILE_NAME));
        oos.writeObject(items);
        oos.close();
        System.out.println("  >> Saved " + items.size() + " records!");
    }

    /**
     * Десеріалізує колекцію з файлу.
     * @throws Exception помилка читання або клас не знайдено
     */
    @SuppressWarnings("unchecked")
    @Override
    public void viewRestore() throws Exception {
        ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FILE_NAME));
        items = (ArrayList<ResistanceData>) ois.readObject();
        ois.close();
        System.out.println("  >> Restored " + items.size() + " records!");
    }
}