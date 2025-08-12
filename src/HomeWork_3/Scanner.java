import java.util.Scanner; // Вызываем утилиту для считывания ввода данных

class scanner { //Обозначаем класс, присваиваем имя

    public static void main(String[] args) {    //Обозначаем метод или точка входа для JVM

        try(Scanner scanner = new Scanner( System.in )) //Чтение из системного ввода с автозакрытием (try)
        {
            String usersName; //переменная, возвращает имя пользователя из "userName"
            System.out.print("Пожалуйста введите Ваше имя: ");  //Выводим строку
            usersName = scanner.next(); //Ждём ввода имени
            System.out.println("Привееет, " + usersName+"! ");  //Выводим новую строку с приветствием
        }
    }
}