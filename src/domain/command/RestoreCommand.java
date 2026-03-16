package src.domain.command;

// ============================================================

import src.domain.command.ConsoleCommand;
import src.domain.view.View;

// Команда відновлення колекції з файлу
// ============================================================

/**
 * <h1>RestoreCommand</h1>
 *
 * Десеріалізує колекцію з файлу.
 */
public class RestoreCommand implements ConsoleCommand {

    /** Об'єкт View */
    private View view;

    /**
     * Конструктор.
     * @param view об'єкт View
     */
    public RestoreCommand(View view) {
        this.view = view;
    }

    @Override
    public char getKey() { return 'r'; }

    @Override
    public String toString() { return "(r) restore from file"; }

    @Override
    public void execute() {
        try {
            view.viewRestore();
            System.out.println("  >> Restored!");
            view.viewShow();
        } catch (Exception e) {
            System.out.println("  >> Restore error: " + e.getMessage());
        }
    }

    @Override
    public void undo() {
        System.out.println("  >> Restore has no undo.");
    }
}