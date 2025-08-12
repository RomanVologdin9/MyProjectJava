package Attestation_01;

import java.util.*;

/**
 * Класс, представляющий покупателя
 */
class Person {
    private String name;        // Имя покупателя
    private double money;       // Количество денег
    private final List<Product> bag = new ArrayList<>();  // Список купленных продуктов

    /**
     * Конструктор покупателя
     * @param name Имя покупателя
     * @param money Начальная сумма денег
     */
    public Person(String name, double money) {
        setName(name);    // Валидация и установка имени
        setMoney(money);  // Валидация и установка денег
    }

    public String getName() {
        return name;
    }

    /**
     * Установка имени с валидацией
     * @param name Имя покупателя
     * @throws IllegalArgumentException При нарушении условий валидации
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (name.trim().length() < 3) {
            throw new IllegalArgumentException("Имя не может быть короче 3 символов");
        }
        this.name = name.trim();  // Удаление лишних пробелов
    }

    public double getMoney() {
        return money;
    }

    /**
     * Установка суммы денег с валидацией
     * @param money Сумма денег
     * @throws IllegalArgumentException При отрицательном значении
     */
    public void setMoney(double money) {
        if (money < 0) {
            throw new IllegalArgumentException("Деньги не могут быть отрицательными");
        }
        this.money = money;
    }

    /**
     * @return Неизменяемый список купленных продуктов
     */
    public List<Product> getBag() {
        return Collections.unmodifiableList(bag);
    }

    /**
     * Покупка продукта
     * @param product Продукт для покупки
     */
    public void buy(Product product) {
        // Проверка достаточности средств
        if (money >= product.getPrice()) {
            bag.add(product);                // Добавление в "пакет"
            money -= product.getPrice();     // Списание средств
            System.out.println(name + " купил " + product.getName());
        } else {
            System.out.println(name + " не может позволить себе " + product.getName());
        }
    }

    /**
     * Форматированное строковое представление покупателя
     * @return Строка с именем и списком покупок
     */
    @Override
    public String toString() {
        if (bag.isEmpty()) {
            return name + " - Ничего не куплено";
        }
        List<String> productNames = new ArrayList<>();
        for (Product product : bag) {
            productNames.add(product.getName());  // Сбор названий продуктов
        }
        return name + " - " + String.join(", ", productNames);
    }

    // Переопределение equals и hashCode для корректного сравнения объектов
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Double.compare(money, person.money) == 0 &&
                Objects.equals(name, person.name) &&
                Objects.equals(bag, person.bag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, money, bag);
    }
}

/**
 * Класс, представляющий продукт
 */
class Product {
    private String name;    // Название продукта
    private double price;   // Цена продукта

    /**
     * Конструктор продукта
     * @param name Название продукта
     * @param price Цена продукта
     */
    public Product(String name, double price) {
        setName(name);      // Валидация и установка названия
        setPrice(price);    // Валидация и установка цены
    }

    public String getName() {
        return name;
    }

    /**
     * Установка названия с валидацией
     * @param name Название продукта
     * @throws IllegalArgumentException При пустом названии
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название продукта не может быть пустым");
        }
        this.name = name.trim();  // Удаление лишних пробелов
    }

    public double getPrice() {
        return price;
    }

    /**
     * Установка цены с валидацией
     * @param price Цена продукта
     * @throws IllegalArgumentException При отрицательной цене
     */
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Стоимость продукта не может быть отрицательной");
        }
        this.price = price;
    }

    // Переопределение стандартных методов
    @Override
    public String toString() {
        return name + " (Цена: " + price + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(price, product.price) == 0 &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}

/**
 * Основной класс приложения
 */
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Коллекции для хранения данных
        Map<String, Person> people = new HashMap<>();    // Покупатели по имени
        Map<String, Product> products = new HashMap<>(); // Продукты по названию

        // Этап 1: Ввод покупателей
        System.out.println("Введите покупателей (Формат: Имя = Сумма). Для завершения введите пустую строку:");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) break;  // Выход при пустой строке

            try {
                String[] parts = input.split("=");
                if (parts.length != 2) {
                    System.out.println("Ошибка формата. Используйте: Имя = Сумма");
                    continue;
                }
                String name = parts[0].trim();
                double money = Double.parseDouble(parts[1].trim());
                Person person = new Person(name, money);
                people.put(name, person);  // Сохранение покупателя
            } catch (Exception e) {
                // Обработка ошибок валидации
                System.out.println(e.getMessage());
                return;  // Завершение при ошибке
            }
        }

        // Этап 2: Ввод продуктов
        System.out.println("Введите продукты (Формат: Название = Цена). Для завершения введите пустую строку:");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) break;

            try {
                String[] parts = input.split("=");
                if (parts.length != 2) {
                    System.out.println("Ошибка формата. Используйте: Название = Цена");
                    continue;
                }
                String name = parts[0].trim();
                double price = Double.parseDouble(parts[1].trim());
                Product product = new Product(name, price);
                products.put(name, product);  // Сохранение продукта
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        // Этап 3: Обработка покупок
        System.out.println("Введите покупки (Формат: Имя покупателя - Название продукта). Для завершения введите END:");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("END")) break;  // Ключевое слово завершения

            try {
                String[] parts = input.split("-");
                if (parts.length != 2) {
                    System.out.println("Ошибка формата. Используйте: Имя покупателя - Название продукта");
                    continue;
                }
                String personName = parts[0].trim();
                String productName = parts[1].trim();

                // Поиск покупателя и продукта
                Person person = people.get(personName);
                Product product = products.get(productName);

                if (person == null || product == null) {
                    System.out.println("Ошибка: Покупатель или продукт не найдены");
                    continue;
                }

                // Совершение покупки
                person.buy(product);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        // Этап 4: Вывод итогов
        System.out.println("\nРезультаты:");
        for (Person person : people.values()) {
            System.out.println(person);  // Используется переопределенный toString()
        }
    }
}