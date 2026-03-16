package src.domain.command;

import src.domain.command.ConsoleCommand;
import src.domain.view.ViewResult;
import src.domain.view.View;
import src.domain.core.ResistanceData;
import java.util.ArrayList;
import java.util.Stack;

// ============================================================
// Команда сортування колекції + підтримка undo
// ============================================================

/**
 * <h1>SortCommand</h1>
 *
 * Консольна команда сортування колекції за зростанням R_total.
 * Підтримує undo - зберігає порядок до сортування.
 */
public class SortCommand implements ConsoleCommand {

    /** Колекція для роботи */
    private ViewResult view;

    /** Стек для undo */
    private Stack<ConsoleCommand> history;

    /** Копія порядку до сортування */
    private ArrayList<ResistanceData> backup;

    /**
     * Конструктор.
     * @param view    об'єкт View
     * @param history стек історії
     */
    public SortCommand(View view, Stack<ConsoleCommand> history) {
        this.view = (ViewResult) view;
        this.history = history;
    }

    @Override
    public char getKey() { return 'o'; }

    @Override
    public String toString() { return "(o) sort by R_total"; }

    /**
     * Сортує колекцію за зростанням R_total.
     * Зберігає копію для undo.
     */
    @Override
    public void execute() {
        // Зберігаємо копію для undo
        backup = new ArrayList<>(view.getItems());

        // Сортування (bubble sort для наочності)
        ArrayList<ResistanceData> items = view.getItems();
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = 0; j < items.size() - i - 1; j++) {
                if (items.get(j).getTotalResistance()
                        > items.get(j + 1).getTotalResistance()) {
                    ResistanceData tmp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, tmp);
                }
            }
        }

        history.push(this);
        System.out.println("  >> Sorted by R_total!");
        view.viewShow();
    }

    /**
     * Відновлює порядок до сортування.
     */
    @Override
    public void undo() {
        if (backup != null) {
            view.getItems().clear();
            view.getItems().addAll(backup);
            System.out.println("  >> Sort undo done!");
            view.viewShow();
        }
    }
}