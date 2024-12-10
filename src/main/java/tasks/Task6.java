package tasks;

import common.Area;
import common.Person;

import java.util.*;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {
    Set<String> result = new HashSet<>();

    Map<Integer, String> areaIdToName = new HashMap<>();
    for (Area area : areas) {
      areaIdToName.put(area.getId(), area.getName());
    }

    for (Person person : persons) {
      Set<Integer> regionIds = personAreaIds.getOrDefault(person.id(), Collections.emptySet());

      for (Integer regionId : regionIds) {
        String regionName = areaIdToName.get(regionId);
        if (regionName != null) {
          result.add(person.firstName() + " - " + regionName);
        }
      }
    }

    return result;
  }
}
