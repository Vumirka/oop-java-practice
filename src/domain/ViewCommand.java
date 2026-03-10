package src.domain;

// ============================================================
// Команда перегляду колекції
// ============================================================

/**
 * <h1>ViewCommand</h1>
 *
 * Виводить поточний стан колекції.
 */
public class ViewCommand implements ConsoleCommand {

    /** Об'єкт View */
    private View view;

    /**
     * Конструктор.
     * @param view об'єкт View
     */
    public ViewCommand(View view) {
        this.view = view;
    }

    @Override
    public char getKey() { return 'v'; }

    @Override
    public String toString() { return "(v) view table"; }

    @Override
    public void execute() {
        view.viewShow();
    }

    @Override
    public void undo() {
        System.out.println("  >> View has no undo.");
    }
}