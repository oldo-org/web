package org.oldo.web.css;

import java.util.Arrays;


/**
 * TODO: Document this
 */
public class Stylesheet implements IStylesheet {

    private final Iterable<Rule> rules;

    public Stylesheet(Rule... rules) {
        this.rules = Arrays.asList(rules);
    }

    public Stylesheet(Iterable<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public Iterable<Rule> getRules() {
        return rules;
    }
}
