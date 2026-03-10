package src.domain;

// ============================================================
// ЗАВДАННЯ 2 -  ConcreteCreator шаблону Factory Method
// Фабрикує конкретний об'єкт ViewResult
// ============================================================

/**
 * <h1>ViewableResult</h1>
 *
 * ConcreteCreator у шаблоні Factory Method.
 * Реалізує інтерфейс {@link Viewable} та повертає
 * конкретний об'єкт {@link ViewResult}.
 */
public class ViewableResult implements Viewable {

    /**
     * Створює та повертає об'єкт {@link ViewResult}.
     * @return новий ViewResult
     */
    @Override
    public View getView() {
        return new ViewResult();
    }
}