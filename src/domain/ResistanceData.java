package src.domain;

import java.io.Serializable;

// ============================================================
// ЗАВДАННЯ 1 - Серіалізований клас для зберігання параметрів
// та результатів обчислень загального опору провідників
// ============================================================

/**
 * <h1>ResistanceData</h1>
 *
 * Клас зберігає вхідні параметри (напруги та струм)
 * і результат обчислення загального опору трьох
 * послідовно з'єднаних провідників.
 *
 * <p>Реалізує інтерфейс {@link Serializable} для можливості
 * збереження та відновлення стану об'єкта.</p>
 *
 * <p>Поле {@code current} оголошено як {@code transient} -
 * воно НЕ серіалізується і після відновлення буде рівне 0.</p>
 */
public class ResistanceData implements Serializable {

    /** Ідентифікатор версії для серіалізації */
    private static final long serialVersionUID = 1L;

    /** Напруга на першому провіднику (В) - серіалізується */
    private double voltage1;

    /** Напруга на другому провіднику (В) - серіалізується */
    private double voltage2;

    /** Напруга на третьому провіднику (В) - серіалізується */
    private double voltage3;

    /**
     * Струм через ланцюг (А).
     * Оголошено як transient - НЕ буде збережено при серіалізації.
     * Після десеріалізації значення скидається до 0.
     */
    private transient double current;

    /** Загальний опір (Ом) - результат обчислення, серіалізується */
    private double totalResistance;

    /**
     * Конструктор за замовчуванням.
     * Ініціалізує всі поля нулями.
     */
    public ResistanceData() {
        this.voltage1 = 0;
        this.voltage2 = 0;
        this.voltage3 = 0;
        this.current = 0;
        this.totalResistance = 0;
    }

    /**
     * Конструктор з параметрами.
     *
     * @param voltage1 напруга на першому провіднику
     * @param voltage2 напруга на другому провіднику
     * @param voltage3 напруга на третьому провіднику
     * @param current  струм через ланцюг
     */
    public ResistanceData(double voltage1, double voltage2,
                          double voltage3, double current) {
        this.voltage1 = voltage1;
        this.voltage2 = voltage2;
        this.voltage3 = voltage3;
        this.current = current;
        this.totalResistance = 0;
    }

    /**
     * Повертає напругу на першому провіднику.
     * @return voltage1
     */
    public double getVoltage1() { return voltage1; }

    /**
     * Повертає напругу на другому провіднику.
     * @return voltage2
     */
    public double getVoltage2() { return voltage2; }

    /**
     * Повертає напругу на третьому провіднику.
     * @return voltage3
     */
    public double getVoltage3() { return voltage3; }

    /**
     * Повертає струм через ланцюг (transient поле).
     * @return current
     */
    public double getCurrent() { return current; }

    /**
     * Встановлює струм (використовується для відновлення
     * після десеріалізації, бо поле transient).
     * @param current значення струму
     */
    public void setCurrent(double current) { this.current = current; }

    /**
     * Повертає загальний опір.
     * @return totalResistance
     */
    public double getTotalResistance() { return totalResistance; }

    /**
     * Встановлює загальний опір після обчислення.
     * @param totalResistance результат обчислення
     */
    public void setTotalResistance(double totalResistance) {
        this.totalResistance = totalResistance;
    }

    /**
     * Рядкове представлення об'єкта.
     * @return рядок з усіма параметрами
     */
    @Override
    public String toString() {
        return "U1=" + voltage1 + "V | U2=" + voltage2
             + "V | U3=" + voltage3 + "V | I=" + current
             + "A | R_total=" + totalResistance + " Ohm";
    }
}