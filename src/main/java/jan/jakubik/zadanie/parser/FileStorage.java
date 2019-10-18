package jan.jakubik.zadanie.parser;

import jan.jakubik.zadanie.model.Person;
import jan.jakubik.zadanie.model.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

@Service
public class FileStorage {
    private ParserEngine parserEngine = new ParserEngine();
    private PersonRepo personRepo;

    @Autowired
    public FileStorage(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    public void addDataFromFile(MultipartFile mfile) throws IOException, ParseException {
        File file = new File(mfile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(mfile.getBytes());
        fout.close();
        List<String> content = Files.readAllLines(Paths.get(file.getPath()), StandardCharsets.UTF_8);
        List<Person> personList = parserEngine.parse(content);
        for (Person p : personList) {
            if (!p.getPhone().matches("[0-9]{9}")) p.setPhone("");
            if (personRepo.findByPhone(p.getPhone()) != null) p.setPhone("");
            personRepo.save(p);
        }
    }
}