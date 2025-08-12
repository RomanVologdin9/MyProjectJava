import java.util.Random;//Добавление утилиты Random

public class Televizor {//Обявляем класс Televizor

    // Приватные поля класса Televizor
    private String brand;//Марка тв
    private String model;//Модель тв
    private int screenSize;//Диагональ
    private boolean isOn;//Состояние тв, включен выключен
    private int currentChannel;//Текущий канал
    private int volume;//Громкость

    // Конструктор с тремя параметрами Брэнд, Модель, Диагональ
    public Televizor(String brand, String model, int screenSize) {//
        this.brand = brand;//присвоение значения полям обьекта
        this.model = model;
        this.screenSize = screenSize;
        this.isOn = false;// Начальное состояние тв выключен
        this.currentChannel = 1;
        this.volume = 50;
    }

    // Свойства (геттеры/сеттеры)
    public String getBrand() {//Возвращает значение поля Брэнд
        return brand;
    }

    public String getModel() {//Возвращает значение поля Модель
        return model;
    }

    public int getScreenSize() {//Возвращает значение поля Диагональ
        return screenSize;
    }

    public boolean isOn() {//Возвращает значение поля - состояние тв
        return isOn;
    }

    public int getCurrentChannel() {//Возвращает значение поля Канал
        return currentChannel;
    }

    public int getVolume() {//Возвращает значение поля Звук
        return volume;
    }

    // Методы
    public void turnOn() {//Метод включения тв
        isOn = true;
        System.out.println("Телевизор включен");
    }

    public void turnOff() {//Метод выключения тв
        isOn = false;
        System.out.println("Телевизор выключен");
    }

    public void changeChannel(int channel) {//Метод для изменения/переключения канала
        if (isOn && channel > 0) {//Если тв включен и канал больше 0, то можно переключить канал
            currentChannel = channel;
            System.out.println("Канал изменён на: " + channel);
        }
    }

    public void increaseVolume() {//Метод для регулировки звука
        if (isOn && volume < 100) {//если тв включен и громкость меньше 100, можно прибавить звук
            volume++;
            System.out.println("Громкость: " + volume);
        }
    }

    public void decreaseVolume() {//если тв включен и громкость больше 0, можно убавить звук
        if (isOn && volume > 0) {
            volume--;
            System.out.println("Громкость: " + volume);
        }
    }

    // Статический метод для создания телевизора со случайными параметрами
    public static Televizor createRandomTelevizor() {
        Random rand = new Random();
        String[] brands = {"Samsung", "LG", "Sony", "Philips"};
        String[] models = {"UltraHD", "SmartTV", "Curved", "OLED"};
        return new Televizor(
                brands[rand.nextInt(brands.length)],
                models[rand.nextInt(models.length)],
                30 + rand.nextInt(41) //Задаём диапазон 30-70 дюймов для случайного выбора
        );
    }

    @Override//Определение подкласса для родительского метода App
    public String toString() {
        return String.format(
                "Телевизор: %s %s, %d\"\nСостояние: %s\nКанал: %d\nГромкость: %d",
                brand, model, screenSize,
                isOn ? "ВКЛ" : "ВЫКЛ",
                currentChannel,
                volume
        );
    }
}