package src.domain.core;

import src.domain.core.ResistanceData;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// ============================================================
// ЗАВДАННЯ 1 - Клас з агрегуванням для знаходження рішення
// задачі обчислення загального опору
// ============================================================

/**
 * <h1>ResistanceCalculator</h1>
 *
 * Клас використовує агрегування - містить об'єкт {@link ResistanceData},
 * але НЕ успадковує його (відношення HAS-A).
 *
 * <p>Обчислює загальний опір за законом Ома: R = U / I,
 * де R_total = R1 + R2 + R3.</p>
 *
 * <p>Також відповідає за серіалізацію та десеріалізацію
 * об'єкта {@link ResistanceData}.</p>
 */
public class ResistanceCalculator {

    /** Назва файлу для серіалізації */
    private static final String FILE_NAME = "resistance.ser";

    /**
     * Агрегований об'єкт з даними.
     * ResistanceCalculator МАЄ ResistanceData - це і є агрегування.
     */
    private ResistanceData data;

    /**
     * Конструктор за замовчуванням.
     */
    public ResistanceCalculator() {
        this.data = new ResistanceData();
    }

    /**
     * Конструктор з параметром.
     * @param data об'єкт з вхідними даними
     */
    public ResistanceCalculator(ResistanceData data) {
        this.data = data;
    }

    /**
     * Повертає поточний об'єкт ResistanceData.
     * @return data
     */
    public ResistanceData getData() { return data; }

    /**
     * Встановлює новий об'єкт ResistanceData.
     * @param data новий об'єкт
     */
    public void setData(ResistanceData data) { this.data = data; }

    /**
     * Обчислює загальний опір трьох послідовних провідників.
     * Формула: R_total = (U1 + U2 + U3) / I
     *
     * @return загальний опір в Омах
     */
    public double calculate() {
        // Закон Ома для кожного провідника: R = U / I
        double r1 = data.getVoltage1() / data.getCurrent();
        double r2 = data.getVoltage2() / data.getCurrent();
        double r3 = data.getVoltage3() / data.getCurrent();

        // Загальний опір послідовного з'єднання
        double total = r1 + r2 + r3;
        data.setTotalResistance(total);
        return total;
    }

    /**
     * Повертає 8-річне представлення загального опору.
     * @return рядок в вісімковій системі числення
     */
    public String toOctal() {
        return Integer.toOctalString((int) data.getTotalResistance());
    }

    /**
     * Повертає 16-річне представлення загального опору.
     * @return рядок в шістнадцятковій системі числення
     */
    public String toHex() {
        return Integer.toHexString(
            (int) data.getTotalResistance()).toUpperCase();
    }

    /**
     * Серіалізує об'єкт ResistanceData у файл.
     * @throws IOException якщо виникла помилка запису
     */
    public void save() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FILE_NAME));
        oos.writeObject(data);
        oos.close();
    }

    /**
     * Десеріалізує об'єкт ResistanceData з файлу.
     * @throws IOException            помилка читання файлу
     * @throws ClassNotFoundException клас не знайдено
     */
    public void restore() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FILE_NAME));
        data = (ResistanceData) ois.readObject();
        ois.close();
    }
}