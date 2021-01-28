package com.hfad.AVc;

import com.hfad.starbuzz.R;

public class Drink {
    private String name;
    private String description;
    private int imageResourceId;
    /**
     * Напитки объеденены массив Drinks
     * Каждый объект Drink состоит из полей имени,
     * описания и идентификатора ресурса изображения. Идентификаторы ресурсов принадлежат
     * изображениям напитков
     */
    public static final Drink[] drinks = {
            new Drink("Latte", "A couple of espresso shots with steamed milk",
                    R.drawable.latte),
            new Drink("Cappuccino", "Espresso, hot milk, and a steamed milk foam",
                    R.drawable.cappuccino),
            new Drink("Filter", "Highest quality beans roasted and brewed fresh",
                    R.drawable.filter)
    };

    /**
     * Конструктор Drink
     * У каждого напитка есть название, описание и изображение.
     * @param name - Имя
     * @param description - Описание
     * @param imageResourceId - Изображение
     */
    private Drink(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    /**
     * Get-методы для приватных переменных.
     */
    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }
    public int getImageResourceId() {
        return imageResourceId;
    }

    /**
     * В качестве строкового представления Drink используется название напитка.
     */
    public String toString() {
        return this.name;
    }
}