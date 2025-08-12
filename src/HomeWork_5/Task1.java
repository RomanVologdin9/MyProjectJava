package HomeWork_5;

import java.util.Scanner; // Импорт класса Scanner для чтения ввода
public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Создание объекта Scanner для чтения из стандартного ввода
        char inputChar = scanner.nextLine().charAt(0); // Чтение строки из ввода и взятие первого символа
        String keyboard = "qwertyuiopasdfghjklzxcvbnm"; // Задание строки, представляющей клавиатуру в виде кольца
        int index = keyboard.indexOf(inputChar); // Поиск индекса введенного символа в строке клавиатуры
            // Вычисление индекса предыдущего символа с учетом замкнутости (кольца)
            // (index - 1) может быть отрицательным, поэтому прибавляем длину строки и берем по модулю длины
        int prevIndex = (index - 1 + keyboard.length()) % keyboard.length();
        System.out.println(keyboard.charAt(prevIndex)); // Вывод символа, находящегося слева от введенного
        scanner.close(); // Закрытие сканнера после окончания работы
    }
}