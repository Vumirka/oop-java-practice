package src.domain;

import java.util.concurrent.TimeUnit;

// ============================================================
// Завдання: пошук мінімального опору  Worker Thread
// ============================================================

/**
 * <h1>MinResistanceCommand</h1>
 *
 * Завдання для обробника потоку - шаблон Worker Thread.
 * Знаходить елемент колекції з мінімальним значенням R_total.
 */
public class MinResistanceCommand implements Command {

    /** Індекс елемента з мінімальним R_total */
    private int result = -1;

    /** Прогрес виконання (0–100) */
    private int progress = 0;

    /** Колекція для обробки */
    private ViewResult viewResult;

    /**
     * Конструктор.
     * @param viewResult колекція результатів
     */
    public MinResistanceCommand(ViewResult viewResult) {
        this.viewResult = viewResult;
    }

    /**
     * Повертає індекс елемента з мінімальним R_total.
     * @return індекс або -1 якщо колекція порожня
     */
    public int getResult() { return result; }

    /**
     * Перевіряє чи завдання ще виконується.
     * @return true - якщо ще не завершено
     */
    public boolean running() { return progress < 100; }

    /**
     * Виконує пошук мінімального R_total у колекції.
     * Використовується обробником потоку {@link CommandQueue}.
     */
    @Override
    public void execute() {
        progress = 0;
        System.out.println("  [MinR] Started...");
        int size = viewResult.getItems().size();
        result = 0;

        for (int i = 1; i < size; i++) {
            if (viewResult.getItems().get(result).getTotalResistance()
                    > viewResult.getItems().get(i).getTotalResistance()) {
                result = i;
            }
            progress = i * 100 / size;
            if (i % (size / 4) == 0) {
                System.out.println("  [MinR] " + progress + "%");
            }
            try {
                TimeUnit.MILLISECONDS.sleep(2000 / size);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        double minR = viewResult.getItems().get(result).getTotalResistance();
        System.out.printf("  [MinR] Done. Min R_total = %.2f Ohm (index #%d)%n",
                minR, result);
        progress = 100;
    }

    /** Undo не підтримується */
    @Override
    public void undo() {
        System.out.println("  [MinR] No undo.");
    }
}