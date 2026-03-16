package src.domain.observer;

import src.domain.observer.ResistanceObserver;
import src.domain.observer.AppAnnotations;
import src.domain.core.ResistanceCalculator;
import src.domain.core.ResistanceData;
import java.util.ArrayList;

// ============================================================
// Observable - спостережуваний об'єкт, шаблон Observer
// ============================================================

/**
 * <h1>ResistanceObservable</h1>
 *
 * Спостережуваний об'єкт - зберігає колекцію {@link ResistanceData}
 * та сповіщає всіх зареєстрованих спостерігачів про зміни.
 *
 * <p>Реалізує шаблон Observer: при будь-якій зміні колекції
 * викликає {@link #notifyObservers()} який розсилає оновлення
 * всім підписаним {@link ResistanceObserver}.</p>
 */
@AppAnnotations.Author(name = "Rizhkevych Viktoriia", date = "2026")
@AppAnnotations.Version("1.0")
public class ResistanceObservable {

    /** Колекція даних */
    private ArrayList<ResistanceData> items = new ArrayList<>();

    /** Список спостерігачів */
    private ArrayList<ResistanceObserver> observers = new ArrayList<>();

    /**
     * Реєструє нового спостерігача.
     * @param observer спостерігач
     */
    public void addObserver(ResistanceObserver observer) {
        observers.add(observer);
    }

    /**
     * Видаляє спостерігача.
     * @param observer спостерігач
     */
    public void removeObserver(ResistanceObserver observer) {
        observers.remove(observer);
    }

    /**
     * Сповіщає всіх спостерігачів про зміну колекції.
     */
    private void notifyObservers() {
        for (ResistanceObserver o : observers) {
            o.update(items);
        }
    }

    /**
     * Повертає колекцію.
     * @return колекція ResistanceData
     */
    public ArrayList<ResistanceData> getItems() {
        return items;
    }

    /**
     * Генерує нові випадкові дані і сповіщає спостерігачів.
     * @param count кількість записів
     */
    public void generate(int count) {
        items.clear();
        for (int i = 0; i < count; i++) {
            double u1 = 5 + Math.random() * 20;
            double u2 = 5 + Math.random() * 20;
            double u3 = 5 + Math.random() * 20;
            double I  = 1 + Math.random() * 9;
            ResistanceData d = new ResistanceData(u1, u2, u3, I);
            ResistanceCalculator calc = new ResistanceCalculator(d);
            calc.calculate();
            items.add(d);
        }
        notifyObservers();
    }

    /**
     * Масштабує всі значення R_total і сповіщає спостерігачів.
     * @param factor коефіцієнт масштабування
     */
    public void scale(double factor) {
        for (ResistanceData d : items) {
            d.setTotalResistance(d.getTotalResistance() * factor);
        }
        notifyObservers();
    }

    /**
     * Сортує колекцію за R_total і сповіщає спостерігачів.
     */
    public void sort() {
        items.sort((a, b) ->
            Double.compare(a.getTotalResistance(), b.getTotalResistance()));
        notifyObservers();
    }
}