### Использование jmh для анализа скорости исполнения кода

Сборка и запуск:

     gradle jmh
    
    
# Параметры запуска 
   
    # JMH version: 1.28
    # VM version: JDK 15.0.2, Java HotSpot(TM) 64-Bit Server VM, 15.0.2+7-27
    # VM options: -Dfile.encoding=windows-1251 -Duser.country=RU -Duser.language=ru -Duser.variant
    # Blackhole mode: full + dont-inline hint
    # Warmup: 3 iterations, 3 s each
    # Measurement: 5 iterations, 10 s each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    
    
# Результаты:
 
 
| Метод           | 12432345             | 12431234512345           | 135679238489a23197582   | definitely not a number |
| --------------- | -------------------- | ------------------------ | ----------------------- | ----------------------- |
| Character check | 11,772 ± 0,045 ns/op | 17,011 ± 0,107 ns/op     | 16,303 ± 0,285 ns/op    | 8,382 ± 0,075 ns/op     |
| parseInt        | 14,121 ± 0,122 ns/op | 1169,313 ± 28,928 ns/op  | 1220,079 ± 20,490 ns/op | 1217,218 ± 15,327 ns/op |
| regex           | 37,907 ± 0,249 ns/op | 43,951 ± 4,705 ns/op     | 50,729 ± 5,744 ns/op    | 29,837 ± 0,022 ns/op    |

# Итоги

Посимвольное сравнение обладает наибольшей скоростью. Вариант с использованием Integer.parseInt работает медленее из-за проверки того, 
что число входит в диапазон [INT_MIN, INT_MAX] (тест 2). В случае, если строка не является числом или не входит в 
нужный диапазон выбрасывается исключение, что является очень дорогостоящей операцией по сравнению с поставленнйо задачей.
Вариант с regex работает хуже из-за большого количества дополнительных вызовов и проверок.
