package src.domain;

import java.io.IOException;

// ============================================================
// Команда збереження колекції у файл
// ============================================================

/**
 * <h1>SaveCommand</h1>
 *
 * Серіалізує поточну колекцію у файл.
 */
public class SaveCommand implements ConsoleCommand {

    /** Об'єкт View */
    private View view;

    /**
     * Конструктор.
     * @param view об'єкт View
     */
    public SaveCommand(View view) {
        this.view = view;
    }

    @Override
    public char getKey() { return 's'; }

    @Override
    public String toString() { return "(s) save to file"; }

    @Override
    public void execute() {
        try {
            view.viewSave();
            System.out.println("  >> Saved!");
        } catch (IOException e) {
            System.out.println("  >> Save error: " + e.getMessage());
        }
    }

    @Override
    public void undo() {
        System.out.println("  >> Save has no undo.");
    }
}