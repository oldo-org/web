package org.guppy4j.web.html;

import java.util.Arrays;

/**
 * TODO: Document this
 */
public class Head extends Tag {

    private final Iterable<HeadAttribute> attributes;
    private final Iterable<HeadContent> contents;

    public static class HeadBuilder {
        private final HeadAttribute[] attributes;

        private HeadBuilder(HeadAttribute... attributes) {
            this.attributes = attributes;
        }

        public Head _(HeadContent... contents) {
            return new Head(Arrays.asList(attributes),
                    Arrays.asList(contents));
        }
    }

    public static HeadBuilder head(HeadAttribute... attributes) {
        return new HeadBuilder(attributes);
    }

    public Head(Iterable<HeadAttribute> attributes,
                Iterable<HeadContent> contents) {
        this.attributes = attributes;
        this.contents = contents;
    }
}
