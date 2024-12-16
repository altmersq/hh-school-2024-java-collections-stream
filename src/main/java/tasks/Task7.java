package tasks;

import common.Company;
import common.Vacancy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/*
Из коллекции компаний необходимо получить всевозможные различные названия вакансий
 */
public class Task7 {

  public static Set<String> vacancyNames(Collection<Company> companies) {
    return companies.stream() // переводим в стрим
            .flatMap(company -> company.getVacancies().stream())  // переводим вакансии в стрим
            .map(Vacancy::getTitle) // преобразуем вакансии в названия
            .collect(Collectors.toSet()); // собираем уникальный названия в сет
  }

}
