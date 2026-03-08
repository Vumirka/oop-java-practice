<div align="center">

# 🌸 Завдання 1

![Java](https://img.shields.io/badge/Java-☕-pink?style=for-the-badge)
![NetBeans](https://img.shields.io/badge/IDE-NetBeans-purple?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Виконано-ff69b4?style=for-the-badge)

</div>

---

## 🎀 Постановка задачі

- Підготувати сховище до розміщення проекту
- Написати просту консольну програму (наприклад вивід на екран аргументів командної строки)
- Прикріпити посилання на GIT та архівований проект
---

## 💜 Про програму

Консольна програма на Java, яка приймає слова через аргументи командного рядка
та виводить детальний аналіз тексту - кількість слів, символів і список кожного слова.

---

## 📁 Структура проекту
```
├── img
│   └── result.png
├── src
│   ├── domain
│   │   └── TextAnalyzer.java 
│   └── test
│       └── Main.java
├── .gitignore
└── README.md
```

---

## ✨ Що робить програма

| Функція | Опис |
|--------|------|
| 📝 Підрахунок слів | Рахує кількість переданих аргументів |
| 🔤 Підрахунок символів | Рахує загальну кількість символів |
| 📋 Список слів | Виводить кожне слово з порядковим номером |
| 🌸 Порожній ввід | Обробляє випадок коли аргументи відсутні |

---

## 🖥️ Приклад запуску
```
Starting Text Analyzer...

~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
Text Analysis
~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~

Word count:9
Character count:56

Words:
 1. "A"
 2. "simple"
 3. "console"
 4. "application"
 5. "-"
 6. "outputting"
 7. "command"
 8. "line"
 9. "arguments"

~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~

Done! Program finished successfully.
```
---
## 📸 Скріншот

> ![Виконання програми](https://github.com/Vumirka/oop-java-practice/blob/task-1-(02.03.26)/img/result.png?raw=true)

---
## 🗂️ Архітектура класів

**TextAnalyzer** - серце програми.
Він отримує масив слів, рахує символи і будує відформатований звіт.

**Main** - точка входу.
Просто запускає аналізатор і передає йому аргументи командного рядка.

---
<div align="center">
Розроблено з 💜 | Ріжкевич Вікторія
</div>
