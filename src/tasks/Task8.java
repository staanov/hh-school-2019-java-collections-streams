package tasks;

import common.Person;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

    //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
    public List<String> getNames(List<Person> persons) {
        /*
        * staanov: Вместо remove() можно прямо в стриме скипнуть 1ый элемент (фальшивую персону)
        * P.S. Да, if не нужен, стрим из пустой коллекции в конце вернет пустую коллекцию...
        * */
        return persons.stream().skip(1).map(Person::getFirstName).collect(Collectors.toList());
    }

    //ну и различные имена тоже хочется
    public Set<String> getDifferentNames(List<Person> persons) {
        /*
        * staanov: Был возврат Set с помощью Stream API: getNames(persons).stream().distinct().collect(Collectors.toSet())
        * distinct лишний, поскольку Set по умолчанию и так делает коллекцию из неповторяющихся объектов
        * убрав distinct получим: getNames(persons).stream().collect(Collectors.toSet())
        * поскольку нет промежуточных операций, то можно избавиться и от стрима
        * */
        return new HashSet<>(getNames(persons));
    }

    //Для фронтов выдадим полное имя, а то сами не могут
    public String convertPersonToString(Person person) {
        /*
        * staanov: Занес все данные персоны в стрим, отфильтровал на отсутствие какого-либо из имен и собрал их в строку
        * */
        return Stream.of(person.getSecondName(), person.getFirstName(), person.getMiddleName())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
    }

    // словарь id персоны -> ее имя
    public Map<Integer, String> getPersonNames(Collection<Person> persons) {
        /*
        * staanov: Заметил цикл for и создание отдельной переменной на стрим
        * merge-function в toMap() как замена проверки условия if (!map.containsKey(person.getId()))
        * */
        return persons.stream()
                .collect(Collectors.toMap(Person::getId,
                        this::convertPersonToString,
                        (existing, replacement) -> existing));
    }

    // есть ли совпадающие в двух коллекциях персоны?
    public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
        boolean has = false;
        /*
        * staanov: Было: 2 вложенных цикла for
        * Стало: превратил одну из коллекций в HashSet, по другой итерировался
        * и при нахождении первого вхождения меняем has = true и выходим из цикла
        * Сложность: O(n)
        * */
        Set<Person> personSet1 = new HashSet<>(persons1);
        for (Person person2 : persons2) {
            if (personSet1.contains(person2)) {
                has = true;
                break;
            }
        }
        return has;
    }

    //Выглядит вроде неплохо...
    public long countEven(Stream<Integer> numbers) {
        /*
        * staanov: Избавился от переменной count путем вызова у фильтрованного стрима метода count()
        * ПОЯСНЕНИЕ (чем было плохо использование переменной count):
        * Переменная count изначально была переменной всего класса, а не метода
        * но при этом использовалась только в countEven()
        * поэтому мы избавляемся от неё как от переменной класса.
        * Дальше, почему лучше избавиться и от локальной переменной count:
        * 1. отсутствие отдельной переменной для count гарантирует, что в эту переменную никто не залезет и не изменит значение в неё
        * 2. как я понял, считается хорошей практикой, когда метод, которому надо что-то вернуть, сам создает
        * этот объект (или примитив как в данном случае)
        * */
        return numbers.filter(num -> num % 2 == 0).count();
    }

    @Override
    public boolean check() {
        System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
        /*
        * staanov: Да, я дошел до сюда:)
        * Для исправления Fail на Success надо (в контексте этой задачи):
        * либо, чтобы код "хорошо пах" (отсылка к Code Smell)
        * либо, чтобы ревьюер был пьян.
        * Второе в нормальной, штатной ситуации нам не надо, поэтому исправляем все неприятные "запахи" кода
        * и меняем codeSmellsGood на true
        * Надеюсь, теперь код "пахнет" лучше:)
        * */
        boolean codeSmellsGood = true;
        boolean reviewerDrunk = false;
        return codeSmellsGood || reviewerDrunk;
    }
}
