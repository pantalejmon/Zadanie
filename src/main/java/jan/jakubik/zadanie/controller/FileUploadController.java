package jan.jakubik.zadanie.controller;


import java.io.IOException;
import java.text.ParseException;

import jan.jakubik.zadanie.parser.FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
public class FileUploadController {
    private Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @GetMapping("/upload")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("upload");
        return modelAndView;
    }

    private final FileStorage fileStorage;

    @Autowired
    public FileUploadController(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    @PostMapping("/upload")
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file,
                                         RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            fileStorage.addDataFromFile(file);
        } catch (IOException e) {
            modelAndView.setViewName("error");
            logger.error("Wrong file was uploaded, Filename: " + file.getOriginalFilename());
        } catch (ParseException e) {
            modelAndView.setViewName("error");
            logger.error("Wrong file was uploaded, Filename: " + file.getOriginalFilename());
        } finally {
            modelAndView.setViewName("upload");
            logger.info("Loaded file with name:" + file.getOriginalFilename());
            logger.info("File size:" + file.getSize());
        }
        return modelAndView;
    }
}

