import java.util.Scanner; // Импорт класса Scanner

public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Создание объекта Scanner
        String sequence = scanner.nextLine(); // Чтение строки последовательности
        int count = 0; // Инициализация счетчика стрелок

        // Проход по строке от начала до позиции, за которой остается минимум 5 символов
        for (int i = 0; i <= sequence.length() - 5; i++) {
            // Извлечение подстроки из 5 символов, начиная с позиции i
            String substring = sequence.substring(i, i + 5);
            // Проверка, является ли подстрока одной из стрелок
            if (substring.equals(">>-->") || substring.equals("<--<<")) {
                count++; // Увеличение счетчика при нахождении стрелки
            }
        }
        System.out.println(count); // Вывод количества найденных стрелок
    }
}