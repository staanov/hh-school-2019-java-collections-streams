package tasks;

import common.Person;
import common.PersonService;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис (он выдает несортированный Set<Person>)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */
public class Task1 implements Task {

    // !!! Редактируйте этот метод !!!
    /* Оценка сложности алгоритма:
     * Время: O(n + n) = O(2n) = O(n) - два НЕ вложенных цикла for
     * Память: O(n + n) = O(2n) = O(n) - за счёт дополнительных HashMap и ArrayList
     * Причемание:
     * HashMap нужен для того, чтобы можно было по id извлекать элементы Person из коллекции
     * */
    private List<Person> findOrderedPersons(List<Integer> personIds) {
        Map<Integer, Person> personMap = PersonService.findPersons(personIds).stream()
                .collect(Collectors.toMap(Person::getId, person -> person));
        return personMap.values().stream()
                .sorted((person1, person2) -> Integer.compare(personIds.indexOf(person1.getId()), personIds.indexOf(person2.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public boolean check() {
        List<Integer> ids = List.of(3, 1, 2);

        return findOrderedPersons(ids).stream()
                .map(Person::getId)
                .collect(Collectors.toList())
                .equals(ids);
    }

}
