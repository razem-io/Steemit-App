package io.razem.steemitapp.controller;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.markdownj.MarkdownProcessor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by julia on 18.07.2016.
 */
public class Markdown {
    private static final MarkdownProcessor m = new MarkdownProcessor();
    private static final Pattern urlPattern = Pattern.compile("\\(?\\bhttps?://[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]");

    public static String toText(String markdown){
        String html = m.markdown(markdown);
        return Jsoup.clean(html, Whitelist.none()).replaceAll("https?://\\S+\\s?", "");
    }

    public static String getFirstImageUrl(String markdown){
        String html = m.markdown(markdown);
        Matcher m = urlPattern.matcher(html);

        if(m.find()){
            String url = m.group();
            if(url.endsWith("png") || url.endsWith("jpg") || url.endsWith("jpeg") || url.endsWith("gif")){
                return url;
            }
        }

        return null;
    }
}
