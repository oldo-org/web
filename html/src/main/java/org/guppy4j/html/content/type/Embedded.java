package org.guppy4j.html.content.type;

/**
 * A content type
 *
 * @see <a href="http://www.w3.org/TR/html5/dom.html#phrasing-content">HTML5 spec</a>
 */
public interface Embedded extends Phrasing {

    /*
    Embedded content is content that imports another resource into the document, or content from another vocabulary that is inserted into the document.

    audio
    canvas
    embed
    iframe
    img
    math
    object
    svg
    video

Elements that are from namespaces other than the HTML namespace and that convey content but not metadata, are embedded content for the purposes of the content models defined in this specification. (For example, MathML, or SVG.)

Some embedded content elements can have fallback content: content that is to be used when the external resource cannot be used (e.g. because it is of an unsupported format). The element definitions state what the fallback is, if any.
     */
}
