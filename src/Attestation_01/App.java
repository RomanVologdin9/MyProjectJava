package Attestation_01;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Класс, представляющий покупателя
 */
class Person {
    private String name; // Имя покупателя
    private double money; // Количество денег
    private final List<Product> bag = new ArrayList<>(); // Список купленных продуктов

    /**
     * Конструктор покупателя
     * @param name Имя покупателя
     * @param money Начальная сумма денег
     */
    public Person(String name, double money) {
        setName(name); // Валидация и установка имени
        setMoney(money); // Валидация и установка денег
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
        this.name = name.trim(); // Удаление лишних пробелов
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
     * @return Немодифицируемый список купленных продуктов
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
            bag.add(product); // Добавление в "пакет"
            money -= product.getPrice(); // Списание средств
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
            productNames.add(product.getName()); // Сбор названий продуктов
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
 * Базовый класс для представления продукта
 */
class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        setName(name);
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    /**
     * Установка названия продукта с валидацией
     * - Не пустое
     * - Не короче 3 символов
     * - Не состоит только из цифр
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название продукта не может быть пустым");
        }
        String trimmedName = name.trim();
        if (trimmedName.length() < 3) {
            throw new IllegalArgumentException("Название продукта не может быть короче 3 символов");
        }
        if (trimmedName.matches("^\\d+$")) {
            throw new IllegalArgumentException("Название продукта не может состоять только из цифр");
        }
        this.name = trimmedName;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Установка цены продукта с валидацией
     * - Должна быть положительной (>0)
     */
    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Цена продукта должна быть положительной");
        }
        this.price = price;
    }

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
 * Класс для представления продукта со скидкой
 * Наследует базовые свойства Product и добавляет:
 * - Размер скидки
 * - Срок действия скидки
 */
class DiscountProduct extends Product {
    private double discount;
    private String validUntil; // Дата в формате "dd.MM.yyyy"

    public DiscountProduct(String name, double price, double discount, String validUntil) {
        super(name, price);
        setDiscount(discount);
        setValidUntil(validUntil);
    }

    public double getDiscount() {
        return discount;
    }

    /**
     * Установка размера скидки с валидацией
     * - Не может быть отрицательной
     */
    public void setDiscount(double discount) {
        if (discount < 0) {
            throw new IllegalArgumentException("Скидка не может быть отрицательной");
        }
        this.discount = discount;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    /**
     * Получение актуальной цены с учетом скидки
     * - Если скидка действует: цена = базовая цена - скидка (но не менее 0)
     * - Если срок скидки истек: возвращает базовую цену
     */
    @Override
    public double getPrice() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate discountDate = LocalDate.parse(validUntil, formatter);

            if (LocalDate.now().isAfter(discountDate)) {
                return super.getPrice(); // Скидка недействительна
            }
        } catch (DateTimeParseException e) {
            return super.getPrice(); // Неверный формат даты
        }

        // Применяем скидку
        double discountedPrice = super.getPrice() - discount;
        return Math.max(discountedPrice, 0); // Не может быть отрицательной
    }

    @Override
    public String toString() {
        return super.getName() + " (Базовая цена: " + super.getPrice() +
                ", Скидка: " + discount +
                ", Действует до: " + validUntil + ")";
    }
}

/**
 * Основной класс приложения с доработанным вводом продуктов
 */
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Person> people = new HashMap<>();
        Map<String, Product> products = new HashMap<>();

        // Ввод покупателей
        System.out.println("Введите покупателей (Формат: Имя = Сумма). Для завершения введите пустую строку:");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) break;

            try {
                String[] parts = input.split("=");
                if (parts.length != 2) {
                    System.out.println("Ошибка формата. Используйте: Имя = Сумма");
                    continue;
                }
                String name = parts[0].trim();
                double money = Double.parseDouble(parts[1].trim());
                Person person = new Person(name, money);
                people.put(name, person);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        // Ввод продуктов с поддержкой скидочных товаров
        System.out.println("Введите продукты (Формат: Название = Цена [для обычных]");
        System.out.println("Или Название = Цена : Скидка : dd.MM.yyyy [для скидочных]");
        System.out.println("Для завершения введите пустую строку:");

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) break;

            try {
                if (input.contains(":")) {
                    // Обработка скидочного продукта
                    String[] mainParts = input.split(":");
                    if (mainParts.length != 3) {
                        throw new IllegalArgumentException("Неверный формат для скидочного продукта. Используйте: Название = Цена : Скидка : dd.MM.yyyy");
                    }

                    String[] namePrice = mainParts[0].split("=");
                    if (namePrice.length != 2) {
                        throw new IllegalArgumentException("Неверный формат названия и цены");
                    }

                    String name = namePrice[0].trim();
                    double price = Double.parseDouble(namePrice[1].trim());
                    double discount = Double.parseDouble(mainParts[1].trim());
                    String validUntil = mainParts[2].trim();

                    DiscountProduct product = new DiscountProduct(name, price, discount, validUntil);
                    products.put(name, product);
                } else {
                    // Обработка обычного продукта
                    String[] parts = input.split("=");
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Неверный формат. Используйте: Название = Цена");
                    }

                    String name = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim());
                    Product product = new Product(name, price);
                    products.put(name, product);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        // Обработка покупок
        System.out.println("Введите покупки (Формат: Имя покупателя - Название продукта). Для завершения введите END:");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("END")) break;

            try {
                String[] parts = input.split("-");
                if (parts.length != 2) {
                    System.out.println("Ошибка формата. Используйте: Имя покупателя - Название продукта");
                    continue;
                }

                String personName = parts[0].trim();
                String productName = parts[1].trim();
                Person person = people.get(personName);
                Product product = products.get(productName);

                if (person == null || product == null) {
                    System.out.println("Ошибка: Покупатель или продукт не найдены");
                    continue;
                }

                person.buy(product);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        // Вывод результатов
        System.out.println("\nРезультаты:");
        for (Person person : people.values()) {
            System.out.println(person);
        }
    }
}