package src.domain;

import java.util.Stack;

// ============================================================
// Команда генерації нових даних
// ============================================================

/**
 * <h1>GenerateCommand</h1>
 *
 * Генерує нові випадкові дані у колекцію.
 */
public class GenerateCommand implements ConsoleCommand {

    /** Об'єкт View */
    private View view;

    /** Стек для undo */
    private Stack<ConsoleCommand> history;

    /**
     * Конструктор.
     * @param view    об'єкт View
     * @param history стек історії
     */
    public GenerateCommand(View view, Stack<ConsoleCommand> history) {
        this.view = view;
        this.history = history;
    }

    @Override
    public char getKey() { return 'g'; }

    @Override
    public String toString() { return "(g) generate data"; }

    @Override
    public void execute() {
        view.viewInit();
        System.out.println("  >> Generated!");
        view.viewShow();
    }

    /** Undo генерації не підтримується */
    @Override
    public void undo() {
        System.out.println("  >> Generate cannot be undone.");
    }
}