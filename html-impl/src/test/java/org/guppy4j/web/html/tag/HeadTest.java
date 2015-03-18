package org.guppy4j.web.html.tag;

import org.junit.Test;

import static org.guppy4j.web.html.tag.Head.head;
import static org.guppy4j.web.html.tag.Title.title;

/**
 * Tests the "head" tag
 */
public class HeadTest {

    @Test(expected = IllegalArgumentException.class)
    public void testMissingTitleCheck() {
        head();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateTitleCheck() {
        head(title(), title());
    }

    @Test
    public void testSingleTitle() {
        head(title());
    }
}
