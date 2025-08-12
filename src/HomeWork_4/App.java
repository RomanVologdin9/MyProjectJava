import java.util.Scanner;

public class App {
    public static void main(String[] args) { // Точка входа JVM
        // Создаём телевизор с фиксированными параметрами
        Televizor tv1 = new Televizor("Sony", "Bravia", 55);// Параметры для тв1 по умолчанию
        tv1.turnOn(); // Телевизор включен
        tv1.changeChannel(5);//Смена канала
        tv1.increaseVolume();//Изменени е громкости
        System.out.println(tv1 + "\n");//Вывод на новую строку с переносом \n

        // Создаём телевизор с параметрами с клавиатуры
        Scanner scanner = new Scanner(System.in);//Создаём сканер из терминала
        System.out.println("\nСоздание телевизора:");// выводим сроку заголовка
        System.out.print("Бренд: ");//Выводим текст
        String brand = scanner.nextLine();//Сканируем поле ввода
        System.out.print("Модель: ");//Выводим текст
        String model = scanner.nextLine();//Сканируем поле ввода
        System.out.print("Диагональ (дюймы): ");//Выводим текст
        int size = scanner.nextInt();//Сканируем целое число в дюймах

        Televizor tv2 = new Televizor(brand, model, size);// Параметры для тв2 для ручного ввода параметров
        tv2.turnOn();
        tv2.changeChannel(10);
        System.out.println(tv2 + "\n");

        // Создаём телевизор тв3 со случайными параметрами
        Televizor tv3 = Televizor.createRandomTelevizor();// Обьявление случайных параметров телевизора
        tv3.turnOn();
        for (int i = 0; i < 3; i++) tv3.increaseVolume();//цикл случайного выбора параметров громкости от 50 до 54
        System.out.println(tv3 + "\n");//вывод в терминал

        scanner.close();//Закрываем сканирование
    }
}