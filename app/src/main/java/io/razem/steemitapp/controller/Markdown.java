package io.razem.steemitapp.controller;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.markdownj.MarkdownProcessor;

/**
 * Created by julia on 18.07.2016.
 */
public class Markdown {
    private static final MarkdownProcessor m = new MarkdownProcessor();

    public static String toText(String markdown){
        String html = m.markdown(markdown);
        return Jsoup.clean(html, Whitelist.none()).replaceAll("https?://\\S+\\s?", "");
    }
}
