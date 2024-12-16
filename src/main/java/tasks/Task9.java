package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

//  private long count;   // Это не пригодится

  // Убираем пустое поле с человеком, и возвращаем список
  public List<String> getNames(List<Person> persons) {
    // Возвращаем новый список, пропуская первый элемент
    return persons.stream()
            .skip(1)
            .map(Person::firstName)
            .collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));  // переиспользуем getNames, возвращая только уникальные записи
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    // делаем лучше и красивее через string.join
    return String.join(" ",
            Optional.ofNullable(person.secondName()).orElse(""),  // если есть, то добавляем фамилию
            Optional.ofNullable(person.firstName()).orElse("")  // если есть, то добавляем имя
    ).trim(); // убиарем лишние пробелы, если чего-то не было
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    Map<Integer, String> map = new HashMap<>(1);
    // Напрямую возвращаем нужный словарь, без лишней проверки
    return persons.stream()
            .collect(Collectors.toMap(
                    Person::id, // Ключ — id человека
                    this::convertPersonToString, // Значение — строковое представление человека
                    (existing, replacement) -> existing // Если ключ дублируется, оставляем первое значение
            ));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    // Преобразуем одну из коллекций в Set, чтобы быстро найти общие элементы
    Set<Person> personSet = new HashSet<>(persons1);
    // Проверяем, есть ли хотя бы один общий элемент
    return persons2.stream().anyMatch(personSet::contains);
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    // Делаем гораздо проще и читабельнее
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    // Создаем список с элементами от 1 до 10000
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    // Создаем копию этого списка (снимок)
    List<Integer> snapshot = new ArrayList<>(integers);
    // Шаффлим изначальный список
    Collections.shuffle(integers);
    // Создаем из этого перемешанного списка множество
    Set<Integer> set = new HashSet<>(integers);
    // toString сортирует элементы в set при вызове, так уж он устроен внутри
    assert snapshot.toString().equals(set.toString());
  }
}

// Не совсем понял вопрос в первый раз. Почитал, и как я понял, причина в том, что
// числа вставляются в HashSet на основе их хэш-кодов.
// Для чисел хэш-код совпадает с их значением, и вставка происходит так,
// что элементы упорядочиваются по возрастанию.
// вот