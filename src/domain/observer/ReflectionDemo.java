package src.domain.observer;

import src.domain.observer.AppAnnotations;
import java.lang.reflect.Method;

// ============================================================
// Демонстрація рефлексії - читання RUNTIME анотацій
// ============================================================

/**
 * <h1>ReflectionDemo</h1>
 *
 * Демонструє концепцію рефлексії (Reflection) у Java.
 * Читає анотації {@link AppAnnotations.Author} через Reflection API.
 *
 * <p>Порівняння політик утримання:</p>
 * <ul>
 *   <li>RUNTIME - читається тут через getAnnotation()</li>
 *   <li>CLASS - є у .class але Reflection її НЕ бачить</li>
 *   <li>SOURCE - зникла при компіляції, недоступна</li>
 * </ul>
 */
public class ReflectionDemo {

    /**
     * Виводить інформацію про анотації класу через Reflection.
     * @param clazz клас для аналізу
     * @return рядок з результатами
     */
    public static String inspect(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        sb.append("Клас: ").append(clazz.getSimpleName()).append("\n");

        // Читаємо RUNTIME анотацію через Reflection
        AppAnnotations.Author author =
            clazz.getAnnotation(AppAnnotations.Author.class);
        if (author != null) {
            sb.append("  @Author: ").append(author.name())
              .append(" (").append(author.date()).append(")\n");
        } else {
            sb.append("  @Author: не знайдено\n");
        }

        // VERSION — RetentionPolicy.CLASS — Reflection не бачить
        sb.append("  @Version: недоступна (CLASS retention)\n");

        // SOURCE — зникла при компіляції
        sb.append("  @Todo: недоступна (SOURCE retention)\n");

        // Методи класу через Reflection
        sb.append("  Методи:\n");
        for (Method m : clazz.getDeclaredMethods()) {
            sb.append("    - ").append(m.getName()).append("()\n");
        }

        return sb.toString();
    }
}