package com.example.LearningLog.advices;

import java.io.IOException;
import java.io.Writer;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

@ControllerAdvice
public class BaseAdvice {
    
    @ModelAttribute("base")
    public Mustache.Lambda base() {
        return new Base();
    }
}


class Base implements Mustache.Lambda {
    
    public String body;

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
        this.body = frag.execute();
    }
}