package io.razem.steemitapp.controller;

import org.commonmark.Extension;
import org.commonmark.parser.Parser;

/**
 * Created by julia on 20.07.2016.
 */
public class AutoMediaExtension implements Parser.ParserExtension {

    private AutoMediaExtension() {
    }

    public static Extension create() {
        return new AutoMediaExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessor(new AutoMediaPostProcessor());
    }

}