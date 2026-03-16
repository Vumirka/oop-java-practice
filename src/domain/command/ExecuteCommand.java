package src.domain.command;
import src.domain.command.ConsoleCommand;
import src.domain.view.View;
import src.domain.view.ViewTable;
import src.domain.view.ViewResult;
import java.util.concurrent.TimeUnit;
import src.domain.thread.AvgResistanceCommand;
import src.domain.thread.CommandQueue;
import src.domain.thread.MaxResistanceCommand;
import src.domain.thread.MinResistanceCommand;

// ============================================================
// Консольна команда - запускає всі потоки паралельно
// ============================================================

/**
 * <h1>ExecuteCommand</h1>
 *
 * Консольна команда "Execute all threads" - шаблон Command.
 *
 * <p>При виклику {@link #execute()} створює дві черги {@link CommandQueue}
 * та розподіляє три завдання між ними для паралельного виконання:</p>
 * <ul>
 *   <li>Черга 1: {@link MinResistanceCommand}</li>
 *   <li>Черга 2: {@link MaxResistanceCommand}, {@link AvgResistanceCommand}</li>
 * </ul>
 */
public class ExecuteCommand implements ConsoleCommand {

    /** Об'єкт View з колекцією */
    private View view;

    /**
     * Конструктор.
     * @param view об'єкт View
     */
    public ExecuteCommand(View view) {
        this.view = view;
    }

    @Override
    public char getKey() { return 'e'; }

    @Override
    public String toString() { return "(e) execute all threads"; }

    /**
     * Запускає паралельне виконання трьох завдань у двох чергах.
     * Очікує завершення всіх потоків перед виходом.
     */
    @Override
public void execute() {
    // ViewTable extends ViewResult — кастимо через батьківський клас
    ViewResult vr;
    if (view instanceof ViewTable) {
        vr = (ViewTable) view;
    } else {
        vr = (ViewResult) view;
    }

    // Створюємо два обробники черги (два Worker Thread)
    CommandQueue queue1 = new CommandQueue();
    CommandQueue queue2 = new CommandQueue();

    // Три завдання
    MinResistanceCommand minCmd = new MinResistanceCommand(vr);
    MaxResistanceCommand maxCmd = new MaxResistanceCommand(vr);
    AvgResistanceCommand avgCmd = new AvgResistanceCommand(vr);

    System.out.println("\n  >> Launching parallel threads...");

    // Черга 1 — min, Черга 2 — max + avg
    queue1.put(minCmd);
    queue2.put(maxCmd);
    queue2.put(avgCmd);

    // Очікуємо завершення всіх трьох
    try {
        while (minCmd.running() || maxCmd.running() || avgCmd.running()) {
            java.util.concurrent.TimeUnit.MILLISECONDS.sleep(100);
        }
        queue1.shutdown();
        queue2.shutdown();
        java.util.concurrent.TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }

    System.out.println("  >> All threads done.");
}

    /** Undo не підтримується */
    @Override
    public void undo() {
        System.out.println("  >> Execute has no undo.");
    }
}