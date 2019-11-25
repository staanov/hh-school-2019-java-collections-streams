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

    private long count;

    //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
    public List<String> getNames(List<Person> persons) {
        if (persons.size() == 0) {
            return Collections.emptyList();
        }
        persons.remove(0);
        return persons.stream().map(Person::getFirstName).collect(Collectors.toList());
    }

    //ну и различные имена тоже хочется
    public Set<String> getDifferentNames(List<Person> persons) {
        /*
        staanov: Был возврат Set с помощью Stream API: getNames(persons).stream().distinct().collect(Collectors.toSet())
        distinct лишний, поскольку Set по умолчанию и так делает коллекцию из неповторяющихся объектов
        убрав distinct получим: getNames(persons).stream().collect(Collectors.toSet())
        поскольку нет промежуточных операций, то можно избавиться и от стрима
        */
        return new HashSet<>(getNames(persons));
    }

    //Для фронтов выдадим полное имя, а то сами не могут
    public String convertPersonToString(Person person) {
        String result = "";
        result += nameNullChecking(person.getSecondName());
        result += nameNullChecking(person.getFirstName());
        result += nameNullChecking(person.getMiddleName());
        return result;
    }

    /*
    staanov: Метод проверки любого имени (first, second, middle) на null и возврата имени (если оно не null)
    во избежание дублирования кода в методе convertPersonToString
    private поскольку им должен пользоваться только метод convertToString, извне вызов метода nameNullChecking не нужен
    */
    private String nameNullChecking(String name) {
        if (name != null) {
            return " " + name;
        } else {
            return "";
        }
    }

    // словарь id персоны -> ее имя
    public Map<Integer, String> getPersonNames(Collection<Person> persons) {
        Map<Integer, String> map = new HashMap<>(1);
        for (Person person : persons) {
            if (!map.containsKey(person.getId())) {
                map.put(person.getId(), convertPersonToString(person));
            }
        }
        return map;
    }

    // есть ли совпадающие в двух коллекциях персоны?
    public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
        boolean has = false;
        /*
        staanov: Было 2 вложенных цикла for
        стал 1 цикл for и метод contains,
        по условию при первом нахождении совпадения цикл завершает свою работы.
        */
        for (Person person1 : persons1) {
            if (persons2.contains(person1)) {
                has = true;
                break;
            }
        }
        return has;
    }

    //Выглядит вроде неплохо...
    public long countEven(Stream<Integer> numbers) {
        count = 0;
        numbers.filter(num -> num % 2 == 0).forEach(num -> count++);
        return count;
    }

    @Override
    public boolean check() {
        System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
        boolean codeSmellsGood = false;
        boolean reviewerDrunk = false;
        return codeSmellsGood || reviewerDrunk;
    }
}
