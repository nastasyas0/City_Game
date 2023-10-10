package com.example.cities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    private Button btnOk;
    private EditText edCity;
    private String city, last_city;
    private TextView tvEnterCity, tvCounter;
    int clicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOk = findViewById(R.id.button_ok);
        edCity = findViewById(R.id.enter_city);
        tvEnterCity = findViewById(R.id.city);
        tvCounter = findViewById(R.id.counter);

        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // счетчик баллов
                clicked++;
                tvCounter.setText(String.valueOf(clicked));

                city = edCity.getText().toString();  // город, который ввел пользователь
                // убрать пробелы в названии города
                city = deleteWhitespace(city);

                // город введен с той буквы, с кот. закончился город, вывед. программой
                boolean flag = true;
                if (clicked > 1) {
                    // последняя буква в названии выведенного программой города
                    String last_letter_last_city = lastLetter(last_city);
                    // первая буква следующего введенного города
                    String first_letter_city = city.substring(0, 1).toLowerCase(Locale.ROOT);

                    if (!first_letter_city.equals(last_letter_last_city)) {
                        tvEnterCity.setText(" Вы неверно ввели\n            город.\n    Игра окончена!");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvEnterCity.setText("   Попробуй снова!");
                            }
                        }, 2000);
                        clicked = 0;
                        tvCounter.setText(String.valueOf(clicked));
                        edCity.setText("");
                        flag = false;
                    }
                }

                if (flag) {
                    // последняя буква в названии введенного города
                    String last_letter_city = lastLetter(city);

                    Dictionary<String, Integer> dictCities = dictCities();
                    String[][] arrCities = arrayCities();

                    // по последней букве получить номер в словаре с городами на последнюю букву
                    Integer num = dictCities.get(last_letter_city);
                    if (num == null) {
                        num = Integer.valueOf(1);
                    }

                    // программа выводит город
                    int randomNum = ThreadLocalRandom.current().nextInt(0, 3);
                    last_city = arrCities[num][randomNum];
                    tvEnterCity.setText(last_city);
                    edCity.setText("");
                }
            }
        });
    }

    private String[][] arrayCities() {
        String[][] arrCities = new String[][] {
                {"", "", ""},
                {"Абу-Даби", "Алания", "Амстердам"},
                {"Барселона", "Берлин", "Бангкок"},
                {"Венеция", "Владивосток", "Владикавказ"},
                {"Гагры", "Гуанчжоу", "Гонконг"},
                {"Дубай", "Детройт", "Джакарта"},
                {"Екатеринбург", "Ереван", "Елец"},
                {"Женева", "Житомир", "Железнодорожный"},
                {"Зеленоград", "Загреб", "Звенигород"},
                {"Иркутск", "Измир", "Иваново"},
                {"Йошкар-Ола", "Йена", "Йорк"},
                {"Казань", "Калининград", "Коломна"},
                {"Лиссабон", "Лондон", "Лос-Анджелес"},
                {"Москва", "Макао", "Мармарис"},
                {"Нанкин", "Новый Афон", "Новосибирск"},
                {"Орёл", "Омск", "Осло"},
                {"Пекин", "Прага", "Пхеньян"},
                {"Рязань", "Рига", "Ростов-на-Дону"},
                {"Санкт-Петербург", "Стамбул", "Салехард"},
                {"Тула", "Таллин", "Торонто"},
                {"Ухань", "Уфа", "Ульяновск"},
                {"Феодосия", "Флоренция", "Франкфурт-на-Майне"},
                {"Харбин", "Ханчжоу", "Хельсинки"},
                {"Циндао", "Цюрих", "Цандрыпш"},
                {"Чернобыль", "Чикаго", "Чэнду"},
                {"Шарм-эш-Шейх", "Шанхай", "Шэнчьжэнь"},
                {"Щёлково", "Щербинка", "Щецин"},
                {"Эр-Рияд", "Элиста", "Энгельс"},
                {"Южно-Сахалинск", "Юрмала", "Юрга"},
                {"Янтарный", "Ялта", "Якутск"},
        };
        return arrCities;
    }

    private Dictionary<String, Integer> dictCities() {
        Dictionary<String, Integer> dictionary = new Hashtable<String, Integer>() {{
            put("a", 1); put("б", 2); put("в", 3); put("г", 4); put("д", 5); put("е", 6);
            put("ж", 7); put("з", 8); put("и", 9); put("й", 10); put("к", 11); put("л", 12);
            put("м", 13); put("н", 14); put("о", 15); put("п", 16); put("р", 17); put("с", 18);
            put("т", 19); put("у", 20); put("ф", 21); put("х", 22); put("ц", 23); put("ч", 24);
            put("ш", 25); put("щ", 26); put("э", 27); put("ю", 28); put("я", 29);
        }};
        return dictionary;
    }

    // убрать пробелы в названии города
    private String deleteWhitespace(String name_city) {
        if (name_city.trim().length() > 0) {
            name_city = name_city.trim();
        }
        return name_city;
    }

    // последняя буква в названии города
    private String lastLetter(String name_city) {
        String last_letter_city = name_city.substring(name_city.length() - 1);
        // если название города оканчивается на ь или ы
        if (last_letter_city.equals("ь") || last_letter_city.equals("ы")) {
            last_letter_city = name_city.substring(name_city.length() - 2, name_city.length() - 1);
        }
        return last_letter_city;
    }
}