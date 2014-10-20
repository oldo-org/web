package org.guppy4j.web.html;

/**
 * TODO: Document this
 */
public enum Lang {
    en, fr, de;


    public static HtmlAttribute lang(Lang lang) {
        return new HtmlAttributeValue(lang) ;
    }
}
