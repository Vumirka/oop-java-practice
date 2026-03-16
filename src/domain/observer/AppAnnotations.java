package src.domain.observer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// ============================================================
// Анотації з різними Retention policies
// ============================================================

/**
 * <h1>AppAnnotations</h1>
 *
 * Демонстрація трьох різних політик утримання анотацій:
 * <ul>
 *   <li>{@link Author} - RUNTIME: доступна під час виконання через Reflection</li>
 *   <li>{@link Version} - CLASS: зберігається у .class але не в JVM</li>
 *   <li>{@link Todo} - SOURCE: лише у вихідному коді, зникає при компіляції</li>
 * </ul>
 *
 */
public class AppAnnotations {

    // ----------------------------------------------------------
    // RUNTIME - доступна через Reflection під час виконання
    // ----------------------------------------------------------

    /**
     * Анотація автора - RetentionPolicy.RUNTIME.
     * Доступна через Reflection під час виконання програми.
     * Саме цю анотацію можна прочитати через getDeclaredAnnotations().
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD})
    public @interface Author {
        /** Ім'я автора */
        String name() default "Rizhkevych Viktoriia";
        /** Дата створення */
        String date() default "2026";
    }

    // ----------------------------------------------------------
    // CLASS - зберігається у .class, але недоступна в JVM
    // ----------------------------------------------------------

    /**
     * Анотація версії - RetentionPolicy.CLASS (за замовчуванням).
     * Зберігається у скомпільованому .class файлі,
     * але НЕ завантажується у JVM - Reflection її не бачить.
     */
    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.TYPE)
    public @interface Version {
        /** Номер версії */
        String value() default "1.0";
    }

    // ----------------------------------------------------------
    // SOURCE - існує лише у вихідному коді
    // ----------------------------------------------------------

    /**
     * Анотація нотатки - RetentionPolicy.SOURCE.
     * Існує лише у вихідному коді .java - компілятор її видаляє.
     * Не потрапляє ні у .class ні у JVM.
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    public @interface Todo {
        /** Текст нотатки */
        String value() default "";
    }
}