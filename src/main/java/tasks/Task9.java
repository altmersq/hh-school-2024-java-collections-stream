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
    // Проверка на пустой список
    if (persons.isEmpty()) {
      return Collections.emptyList();
    }
    // Возвращаем новый список, пропуская первый элемент
    return persons.stream()
            .skip(1)
            .map(Person::firstName)
            .collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    // Не вызываем getNames, а напрямую возвращаем сет людей
    return persons.stream()
            .skip(1)
            .map(Person::firstName)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    String result = "";
    if (person.secondName() != null) {
      result += person.secondName();
    }

    if (person.firstName() != null) {
      result += " " + person.firstName();
    }

    if (person.secondName() != null) {
      result += " " + person.secondName();
    }
    return result;
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
