package src.domain;

import java.util.ArrayList;

// ============================================================
// ConcreteProduct - розширення ієрархії Factory Method
// Виводить колекцію у вигляді текстової таблиці
// Демонструє: overriding, overloading, поліморфізм
// ============================================================

/**
 * <h1>ViewTable</h1>
 *
 * Розширює {@link ViewResult}. Виводить результати обчислень
 * у вигляді форматованої текстової таблиці.
 *
 * <p>Ширина таблиці задається користувачем через діалог.</p>
 *
 * <h2>Демонстрація концепцій ООП:</h2>
 * <ul>
 *   <li><b>Overriding</b> - перевизначення методів {@code viewHeader},
 *       {@code viewBody}, {@code viewFooter}, {@code viewInit}</li>
 *   <li><b>Overloading</b> - перевантажені конструктори та метод {@code init}</li>
 *   <li><b>Поліморфізм</b> - об'єкт типу {@link View} поводиться як
 *       {@code ViewTable} під час виконання</li>
 * </ul>
 *
 * @author Rizhkevi Viktoriia
 * @version 1.0
 */
public class ViewTable extends ViewResult {

    /** Ширина таблиці за замовчуванням */
    private static final int DEFAULT_WIDTH = 50;

    /** Поточна ширина таблиці (задається користувачем) */
    private int width;

    // ----------------------------------------------------------
    // Конструктори - демонстрація OVERLOADING
    // ----------------------------------------------------------

    /**
     * Конструктор за замовчуванням. (overloading 1/3)
     * Ширина таблиці = DEFAULT_WIDTH.
     */
    public ViewTable() {
        this.width = DEFAULT_WIDTH;
    }

    /**
     * Конструктор з шириною таблиці. (overloading 2/3)
     *
     * @param width ширина таблиці в символах
     */
    public ViewTable(int width) {
        this.width = width;
    }

    /**
     * Конструктор з шириною та кількістю елементів. (overloading 3/3)
     *
     * @param width ширина таблиці
     * @param size  початкова кількість елементів у колекції
     */
    public ViewTable(int width, int size) {
        super();
        this.width = width;
    }

    // ----------------------------------------------------------
    // Гетер та сетер ширини
    // ----------------------------------------------------------

    /**
     * Повертає поточну ширину таблиці.
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Встановлює нову ширину таблиці. (overloading метод 1/2)
     *
     * @param width нова ширина
     * @return встановлена ширина
     */
    public int setWidth(int width) {
        return this.width = width;
    }

    /**
     * Перевантажений метод - встановлює ширину і друкує повідомлення. (overloading метод 2/2)
     *
     * @param width   нова ширина
     * @param message повідомлення для виводу
     * @return встановлена ширина
     */
    public int setWidth(int width, String message) {
        System.out.println("  >> " + message);
        return setWidth(width);
    }

    // ----------------------------------------------------------
    // Допоміжні методи для малювання таблиці
    // ----------------------------------------------------------

    /**
     * Виводить горизонтальну лінію заданої ширини.
     */
    private void drawLine() {
        System.out.print("  +");
        for (int i = 0; i < width - 2; i++) System.out.print("-");
        System.out.println("+");
    }

    /**
     * Форматує один рядок таблиці.
     *
     * @param left  лівий стовпець
     * @param right правий стовпець
     * @return відформатований рядок
     */
    private String formatRow(String left, String right) {
        int col = (width - 5) / 2;
        return String.format("  | %-" + col + "s | %-" + col + "s |", left, right);
    }

    // ----------------------------------------------------------
    // Перевизначення методів ViewResult - демонстрація OVERRIDING
    // ----------------------------------------------------------

    /**
     * Перевизначення (overriding) методу {@link ViewResult#viewHeader()}.
     * Виводить заголовок таблиці з назвами стовпців.
     */
    @Override
    public void viewHeader() {
        // overriding - замінюємо вивід заголовка на табличний формат
        drawLine();
        System.out.println(formatRow("I (A)", "R_total (Ohm)"));
        System.out.println(formatRow("Octal", "Hex"));
        drawLine();
    }

    /**
     * Перевизначення (overriding) методу {@link ViewResult#viewBody()}.
     * Виводить кожен елемент колекції як рядок таблиці.
     */
    @Override
    public void viewBody() {
        // overriding — замінюємо вивід тіла на табличний формат
        ArrayList<ResistanceData> items = getItems();
        if (items.isEmpty()) {
            System.out.println(formatRow("no data", ""));
            return;
        }
        for (ResistanceData data : items) {
            int r = (int) data.getTotalResistance();
            System.out.println(formatRow(
                String.format("%.2f", data.getCurrent()),
                String.format("%.2f", data.getTotalResistance())
            ));
            System.out.println(formatRow(
                Integer.toOctalString(r),
                Integer.toHexString(r).toUpperCase()
            ));
            drawLine();
        }
    }

    /**
     * Перевизначення (overriding) методу {@link ViewResult#viewFooter()}.
     * Виводить кількість записів.
     */
    @Override
    public void viewFooter() {
        // overriding — замінюємо вивід підсумку
        System.out.println("  Total records: " + getItems().size() + "\n");
    }

    /**
     * Перевизначення (overriding) методу {@link ViewResult#viewInit()}.
     * Виводить повідомлення та викликає ініціалізацію батьківського класу.
     */
    @Override
    public void viewInit() {
        // overriding — додаємо повідомлення перед ініціалізацією
        System.out.println("  >> Initializing table data...");
        super.viewInit();   // виклик методу батьківського класу
        System.out.println("  >> Done. Records: " + getItems().size());
    }

    // ----------------------------------------------------------
    // Перевантажений метод init - демонстрація OVERLOADING
    // ----------------------------------------------------------

    /**
     * Перевантажений метод ініціалізації з шириною. (overloading)
     * Встановлює ширину таблиці і викликає {@link #viewInit()}.
     *
     * @param width нова ширина таблиці
     */
    public final void init(int width) {
        // overloading — інша сигнатура порівняно з viewInit()
        this.width = width;
        viewInit();
    }
}