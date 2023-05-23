package com.lzh.blog.utils;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MarkDownUtils {

    /**
     * markdown格式转成html格式
     */
    public static String markdown2html(String markdown){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
    /**
     * extension markdown
     */
    public static String markdownExtensions(String markdown){
        List<Extension> table = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(table)
                .build();
        Node node = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                        .extensions(table)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    @Override
                    public AttributeProvider create(AttributeProviderContext attributeProviderContext) {
                        return new CustomAttributeProvider();
                    }
                })
                .build();
        return renderer.render(node);
    }

    public static class CustomAttributeProvider implements AttributeProvider{

        @Override
        public void setAttributes(Node node, String s, Map<String, String> map) {
            if (node instanceof Link){
                map.put("target", "_blank");
//                Node n = node.getParent();
//                if (n instanceof Paragraph){
//                    map.put("style", "text-align : center");
//                }
            }
            if (node instanceof Paragraph && node.getFirstChild() instanceof Link && (node.getFirstChild()).getFirstChild() instanceof Image){
//                System.out.println("sddawwadwadawdwadawdadwadawdada");
                map.put("style", "text-align : center");
            }
            if (node instanceof TableBlock){
                map.put("class", "ui celled table");
            }
        }
    }
}
