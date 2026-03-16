package src.domain.thread;

import src.domain.command.Command;
import src.domain.view.ViewResult;
import java.util.concurrent.TimeUnit;

// ============================================================
// Завдання: пошук максимального опору - Worker Thread
// ============================================================

/**
 * <h1>MaxResistanceCommand</h1>
 *
 * Завдання для обробника потоку - шаблон Worker Thread.
 * Знаходить елемент колекції з максимальним значенням R_total.
 */
public class MaxResistanceCommand implements Command {

    /** Індекс елемента з максимальним R_total */
    private int result = -1;

    /** Прогрес виконання (0–100) */
    private int progress = 0;

    /** Колекція для обробки */
    private ViewResult viewResult;

    /**
     * Конструктор.
     * @param viewResult колекція результатів
     */
    public MaxResistanceCommand(ViewResult viewResult) {
        this.viewResult = viewResult;
    }

    /**
     * Повертає індекс елемента з максимальним R_total.
     * @return індекс або -1 якщо колекція порожня
     */
    public int getResult() { return result; }

    /**
     * Перевіряє чи завдання ще виконується.
     * @return true - якщо ще не завершено
     */
    public boolean running() { return progress < 100; }

    /**
     * Виконує пошук максимального R_total у колекції.
     * Використовується обробником потоку {@link CommandQueue}.
     */
    @Override
    public void execute() {
        progress = 0;
        System.out.println("  [MaxR] Started...");
        int size = viewResult.getItems().size();
        result = 0;

        for (int i = 1; i < size; i++) {
            if (viewResult.getItems().get(result).getTotalResistance()
                    < viewResult.getItems().get(i).getTotalResistance()) {
                result = i;
            }
            progress = i * 100 / size;
            if (i % (size / 4) == 0) {
                System.out.println("  [MaxR] " + progress + "%");
            }
            try {
                TimeUnit.MILLISECONDS.sleep(2000 / size);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        double maxR = viewResult.getItems().get(result).getTotalResistance();
        System.out.printf("  [MaxR] Done. Max R_total = %.2f Ohm (index #%d)%n",
                maxR, result);
        progress = 100;
    }

    /** Undo не підтримується для статистичних команд */
    @Override
    public void undo() {
        System.out.println("  [MaxR] No undo.");
    }
}