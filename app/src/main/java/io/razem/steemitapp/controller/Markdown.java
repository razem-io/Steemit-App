package io.razem.steemitapp.controller;

import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.html.HtmlRenderer;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkType;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by julia on 18.07.2016.
 */
public class Markdown {
    private static final List<Extension> extensions = Arrays.asList(
            TablesExtension.create(),
            AutoMediaExtension.create());
    private static final Parser parser = Parser.builder().extensions(extensions).build();

    private static final LinkExtractor linkExtractor = LinkExtractor.builder()
            .linkTypes(EnumSet.of(LinkType.URL)) // limit to URLs
            .build();

    public static String toText(String markdown){
        String html = toHtml(markdown);
        return Jsoup.clean(html, Whitelist.none()).replaceAll("https?://\\S+\\s?", "");
    }

    public static String getFirstImageUrl(String markdown){
        Document doc = Jsoup.parse(toHtml(markdown));

        Element media = doc.select("[src]").first();
        if(media != null){
            return media.attr("abs:src");
        }

        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String href = link.attr("abs:href");
            try {
                URLConnection connection = new URL(href).openConnection();
                String contentType = connection.getHeaderField("Content-Type");
                if(contentType.startsWith("image/")){
                    return href;
                }
            } catch (IOException e) {} //WE DO NOT CARE
        }

        return null;
    }

    public static String toHtml(String markdown){
        Node doc = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        return renderer.render(doc);
    }
}
