package tasks;

import common.ApiPersonDto;
import common.Person;
import common.PersonConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
Задача 5
Расширим предыдущую задачу.
Есть список персон, и словарь сопоставляющий id каждой персоны и id региона
Необходимо выдать список персон ApiPersonDto, с правильно проставленными areaId
Конвертер одной персоны дополнен!
 */
public class Task5 {

  private final PersonConverter personConverter;

  public Task5(PersonConverter personConverter) {
    this.personConverter = personConverter;
  }

  public List<ApiPersonDto> convert(List<Person> persons, Map<Integer, Integer> personAreaIds) {
    List<ApiPersonDto> result = new ArrayList<>();

    // Проходим по каждому человеку в списке
    for (Person person : persons) {
      // Переводим Person в PersonDto
      ApiPersonDto personDto = personConverter.convert(person);

      // Получаем areaId из personAreaIds по id человека
      Integer areaId = personAreaIds.get(person.id());

      // Устанавливаем areId в personDto и добавляем в результат
      personDto.setAreaId(areaId);
      result.add(personDto);
    }
    return result;
  }
}
