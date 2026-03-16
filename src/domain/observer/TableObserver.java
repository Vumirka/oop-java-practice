package src.domain.observer;

import src.domain.observer.ResistanceObserver;
import src.domain.observer.AppAnnotations;
import src.domain.core.ResistanceData;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

// ============================================================
// Спостерігач 1 - оновлює JTable при зміні колекції
// ============================================================

/**
 * <h1>TableObserver</h1>
 *
 * Перший спостерігач у шаблоні Observer.
 * При отриманні сповіщення оновлює дані у {@link JTable}.
 */
@AppAnnotations.Author(name = "Rizhkevych Viktoriia", date = "2026")
public class TableObserver implements ResistanceObserver {

    /** Модель таблиці для оновлення */
    private DefaultTableModel model;

    /**
     * Конструктор.
     * @param model модель таблиці Swing
     */
    public TableObserver(DefaultTableModel model) {
        this.model = model;
    }

    /**
     * Оновлює таблицю при зміні колекції.
     * @param items оновлена колекція
     */
    @Override
    public void update(ArrayList<ResistanceData> items) {
        model.setRowCount(0); // очищаємо
        for (int i = 0; i < items.size(); i++) {
            ResistanceData d = items.get(i);
            model.addRow(new Object[]{
                i + 1,
                String.format("%.2f", d.getCurrent()),
                String.format("%.2f", d.getTotalResistance()),
                Integer.toOctalString((int) d.getTotalResistance()),
                Integer.toHexString((int) d.getTotalResistance()).toUpperCase()
            });
        }
    }
}