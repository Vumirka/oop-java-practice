package src.domain.command;

import src.domain.command.ConsoleCommand;
import java.util.Stack;

// ============================================================
// Команда скасування останньої дії (undo)
// ============================================================

/**
 * <h1>UndoCommand</h1>
 *
 * Скасовує останню виконану команду зі стеку історії.
 *
 * <p>Реалізує можливість undo операцій — дістає останню
 * команду зі стеку та викликає її метод {@link Command#undo()}.</p>
 */
public class UndoCommand implements ConsoleCommand {

    /** Стек виконаних команд */
    private Stack<ConsoleCommand> history;

    /**
     * Конструктор.
     * @param history стек виконаних команд
     */
    public UndoCommand(Stack<ConsoleCommand> history) {
        this.history = history;
    }

    @Override
    public char getKey() { return 'u'; }

    @Override
    public String toString() { return "(u) undo last action"; }

    /**
     * Скасовує останню команду зі стеку.
     */
    @Override
    public void execute() {
        if (history.isEmpty()) {
            System.out.println("  >> Nothing to undo.");
        } else {
            // Дістаємо останню команду і скасовуємо
            ConsoleCommand last = history.pop();
            System.out.println("  >> Undoing: " + last);
            last.undo();
        }
    }

    /** Undo самого undo не підтримується */
    @Override
    public void undo() {
        System.out.println("  >> Cannot undo undo.");
    }
}