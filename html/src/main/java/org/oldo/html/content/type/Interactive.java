package org.oldo.html.content.type;

/**
 * A content type
 *
 * @see <a href="http://www.w3.org/TR/html5/dom.html#interactive-content">HTML5 spec</a>
 */
public interface Interactive extends Flow {

    /*
    Interactive content is content that is specifically intended for user interaction.

    a
    audio (if the controls attribute is present)
    button
    embed
    iframe
    img (if the usemap attribute is present)
    input (if the type attribute is not in the Hidden state)
    keygen
    label
    object (if the usemap attribute is present)
    select
    textarea
    video (if the controls attribute is present)

     */
}
