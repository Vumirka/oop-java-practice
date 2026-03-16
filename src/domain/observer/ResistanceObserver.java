package src.domain.observer;

import src.domain.observer.AppAnnotations;
import src.domain.core.ResistanceData;
import java.util.ArrayList;

// ============================================================
// Інтерфейс спостерігача - шаблон Observer
// ============================================================

/**
 * <h1>ResistanceObserver</h1>
 *
 * Інтерфейс спостерігача у шаблоні Observer.
 * Кожен спостерігач реагує на зміни колекції методом {@link #update}.
 */
@AppAnnotations.Author(name = "Rizhkevych Viktoriia", date = "2026")
public interface ResistanceObserver {

    /**
     * Викликається коли колекція змінилась.
     * @param items оновлена колекція
     */
    void update(ArrayList<ResistanceData> items);
}