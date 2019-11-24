package tasks;

import common.Person;
import common.Task;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, затем по имени, а потом по дате создания
 */
public class Task3 implements Task {

  // !!! Редактируйте этот метод !!!
  private List<Person> sort(Collection<Person> persons) {
    /* Алгоритм:
    * 1. Отсортировать по фамилии
    * 2. Отсортировать по имени
    * 3. Отсортировать по дате создания
    * 4. Вернуть отсортированный список
    * */
    return new ArrayList<>(persons);
  }

  @Override
  public boolean check() {
    Instant time = Instant.now();
    List<Person> persons = List.of(
        new Person(1, "Oleg", "Ivanov", time),
        new Person(2, "Vasya", "Petrov", time),
        new Person(3, "Oleg", "Petrov", time.plusSeconds(1)),
        new Person(4, "Oleg", "Ivanov", time.plusSeconds(1))
    );
    List<Person> sortedPersons = List.of(
        new Person(1, "Oleg", "Ivanov", time),
        new Person(4, "Oleg", "Ivanov", time.plusSeconds(1)),
        new Person(3, "Oleg", "Petrov", time.plusSeconds(1)),
        new Person(2, "Vasya", "Petrov", time)
    );
    return sortedPersons.equals(sort(persons));
  }
}
