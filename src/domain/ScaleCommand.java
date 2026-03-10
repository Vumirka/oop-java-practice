package src.domain;

import java.util.ArrayList;
import java.util.Stack;

// ============================================================
// Команда масштабування колекції + підтримка undo
// ============================================================

/**
 * <h1>ScaleCommand</h1>
 *
 * Консольна команда масштабування - множить опір кожного
 * елемента колекції на випадковий коефіцієнт.
 *
 * <p>Підтримує undo - зберігає копію колекції перед виконанням.</p>
 */
public class ScaleCommand implements ConsoleCommand {

    /** Колекція для роботи */
    private ViewResult view;

    /** Стек виконаних команд для undo */
    private Stack<ConsoleCommand> history;

    /** Копія колекції до масштабування (для undo) */
    private ArrayList<ResistanceData> backup;

    /** Коефіцієнт масштабування */
    private double factor;

    /**
     * Конструктор.
     * @param view    об'єкт View з колекцією
     * @param history стек для збереження історії
     */
    public ScaleCommand(View view, Stack<ConsoleCommand> history) {
        this.view = (ViewResult) view;
        this.history = history;
    }

    @Override
    public char getKey() { return 'c'; }

    @Override
    public String toString() { return "(c) scale collection"; }

    /**
     * Виконує масштабування - множить R_total кожного елемента на factor.
     * Зберігає копію для undo.
     */
    @Override
    public void execute() {
        // Зберігаємо копію для undo
        backup = new ArrayList<>();
        for (ResistanceData d : view.getItems()) {
            ResistanceData copy = new ResistanceData(
                d.getVoltage1(), d.getVoltage2(),
                d.getVoltage3(), d.getCurrent());
            copy.setTotalResistance(d.getTotalResistance());
            backup.add(copy);
        }

        // Масштабування
        factor = 0.5 + Math.random() * 1.5;
        System.out.println("  >> Scale factor: "
                + String.format("%.2f", factor));
        for (ResistanceData d : view.getItems()) {
            d.setTotalResistance(d.getTotalResistance() * factor);
        }

        // Зберігаємо в стек
        history.push(this);
        view.viewShow();
    }

    /**
     * Скасовує масштабування — відновлює копію колекції.
     */
    @Override
    public void undo() {
        if (backup != null) {
            view.getItems().clear();
            view.getItems().addAll(backup);
            System.out.println("  >> Scale undo done!");
            view.viewShow();
        }
    }
}