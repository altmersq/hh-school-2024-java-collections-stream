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

    // делаем мапу id -> Person
    Map<Integer, Person> idToPerson = persons.stream()
            .collect(Collectors.toMap(Person::id, person -> person));

    return personIds.stream()
            .map(idToPerson::get)
            .collect(Collectors.toList());
  }
}

// в таком случае O(N) будет, так как мы просто проходимся по N элементам, а получение ID имеет сложность O(1), так как
// это взятие элемента из хэшмапы

