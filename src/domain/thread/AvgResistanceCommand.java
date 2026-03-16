package src.domain.thread;

import src.domain.command.Command;
import src.domain.view.ViewResult;
import src.domain.core.ResistanceData;
import java.util.concurrent.TimeUnit;

// ============================================================
// Завдання: обчислення середнього опору - Worker Thread
// ============================================================

/**
 * <h1>AvgResistanceCommand</h1>
 *
 * Завдання для обробника потоку - шаблон Worker Thread.
 * Обчислює середнє арифметичне значення R_total колекції.
 */
public class AvgResistanceCommand implements Command {

    /** Результат - середнє значення R_total */
    private double result = 0.0;

    /** Прогрес виконання (0–100) */
    private int progress = 0;

    /** Колекція для обробки */
    private ViewResult viewResult;

    /**
     * Конструктор.
     * @param viewResult колекція результатів
     */
    public AvgResistanceCommand(ViewResult viewResult) {
        this.viewResult = viewResult;
    }

    /**
     * Повертає середнє значення R_total.
     * @return середнє арифметичне
     */
    public double getResult() { return result; }

    /**
     * Перевіряє чи завдання ще виконується.
     * @return true - якщо ще не завершено
     */
    public boolean running() { return progress < 100; }

    /**
     * Обчислює середнє R_total у колекції.
     * Використовується обробником потоку {@link CommandQueue}.
     */
    @Override
    public void execute() {
        progress = 0;
        result   = 0.0;
        System.out.println("  [AvgR] Started...");
        int size = viewResult.getItems().size();
        int idx  = 1;

        for (ResistanceData d : viewResult.getItems()) {
            result += d.getTotalResistance();
            progress = idx * 100 / size;
            if (idx % (size / 4) == 0) {
                System.out.println("  [AvgR] " + progress + "%");
            }
            try {
                TimeUnit.MILLISECONDS.sleep(2000 / size);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            idx++;
        }

        result /= size;
        System.out.printf("  [AvgR] Done. Avg R_total = %.2f Ohm%n", result);
        progress = 100;
    }

    /** Undo не підтримується */
    @Override
    public void undo() {
        System.out.println("  [AvgR] No undo.");
    }
}