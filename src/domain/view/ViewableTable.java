package src.domain.view;

// ============================================================

import src.domain.view.ViewableResult;
import src.domain.view.View;

// ConcreteCreator - розширення ієрархії Factory Method
// Фабрикує ViewTable замість ViewResult
// ============================================================

/**
 * <h1>ViewableTable</h1>
 *
 * Розширює {@link ViewableResult} - ConcreteCreator у Factory Method.
 * Перевизначає метод {@link #getView()} щоб повертати {@link ViewTable}
 * замість {@link ViewResult}.
 *
 * <p>Демонстрація overriding - метод {@code getView()} перевизначається.</p>
 */
public class ViewableTable extends ViewableResult {

    /**
     * Перевизначення (overriding) методу батьківського класу.
     * Тепер фабрика створює {@link ViewTable} замість {@link ViewResult}.
     *
     * @return новий об'єкт ViewTable
     */
    @Override
    public View getView() {
        // overriding - замінюємо поведінку батьківського класу
        return new ViewTable();
    }
}