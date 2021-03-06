package org.trimou.engine.interpolation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.junit.Test;

/**
 *
 * @author Martin Kouba
 */
public class BracketDotKeySplitterTest {

    @Test
    public void testSplit() {
        BracketDotKeySplitter splitter = new BracketDotKeySplitter();
        assertIterator(splitter.split("a..bar:.c"), "a", "bar:", "c");
        assertIterator(splitter.split(". "), " ");
        assertIterator(splitter.split("."), ".");
        assertIterator(splitter.split("foo"), "foo");
        assertIterator(splitter.split("foo[\"bar\"]"), "foo", "bar");
        assertIterator(splitter.split("foo[\"a.b.c\"]"), "foo", "a.b.c");
        assertIterator(splitter.split("foo[\"bar\"].baz"), "foo", "bar", "baz");
        assertIterator(splitter.split("a[\"b\"].c.d[\"e\"][\"f\"]"), "a", "b",
                "c", "d", "e", "f");
    }

    private void assertIterator(Iterator<String> iterator, Object... elements) {
        int idx = 0;
        while (iterator.hasNext()) {
            assertEquals(elements[idx], iterator.next());
            idx++;
        }
        if (idx != elements.length) {
            fail("Incorrect number of elements");
        }
    }

}
