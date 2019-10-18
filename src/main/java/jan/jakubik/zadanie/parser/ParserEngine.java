package jan.jakubik.zadanie.parser;

import jan.jakubik.zadanie.model.Person;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ParserEngine {
    public ParserEngine() {
    }

    public List<Person> parse(List<String> fileContent) throws ParseException {
        List<Person> personToAdd = new ArrayList<Person>();
        if (fileContent.get(0).contains("first_name")) fileContent.remove(0);
        for (String line : fileContent) {
            String[] params = line.split(";");
            if (!(params.length < 4)) {
                Person p = new Person();
                p.setFirstName((params[0].substring(0, 1).toUpperCase() + params[0].substring(1).toLowerCase()).replace(" ", "").replace("\t", ""));
                p.setLastName((params[1].substring(0, 1).toUpperCase() + params[1].substring(1).toLowerCase()).replace(" ", "").replace("\t", ""));
                Calendar cal = Calendar.getInstance();
                cal.setTime(new SimpleDateFormat("yyyy.MM.dd").parse(params[2].replace(" ", "")));
                p.setBorn(cal);
                p.setPhone(params[3].replace(" ", ""));
                personToAdd.add(p);
            }

        }
        return personToAdd;
    }
}
