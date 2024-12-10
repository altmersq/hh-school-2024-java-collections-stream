package tasks;

import common.Person;
import common.PersonService;

import java.util.*;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds); // Получаем set Person из сервиса

    Map<Integer, Integer> idToIndex = new HashMap<>();
    for (int i = 0; i < personIds.size(); i++) {
      idToIndex.put(personIds.get(i), i);
    }

    return persons.stream()
            .sorted(Comparator.comparingInt(person -> idToIndex.get(person.id())))
            .collect(Collectors.toList());
  }
}

// Получение данных из сервиса personSerivce - O(N), где N - количество объектов
// Перебираем все элементы списка, и добавляем их в HashMap. У этой части сложность O(K), где K - длина списка personIds
// При преобразовании в поток и сортировке, сложность O(NlogN).
// Преобразование в список за O(K)
// В итоге имеем O(N) + O(K) + O(NlogN) + O(K) = O(NlogN + K)
