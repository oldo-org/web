package org.guppy4j.html.content.type;

/**
 * A content type
 *
 * @see <a href="http://www.w3.org/TR/html5/dom.html#phrasing-content">HTML5 spec</a>
 */
public interface Phrasing extends Flow {

    /*
    Phrasing content is the text of the document, as well as elements that mark up that text at the intra-paragraph level. Runs of phrasing content form paragraphs.

    a
    abbr
    area (if it is a descendant of a map element)
    audio
    b
    bdi
    bdo
    br
    button
    canvas
    cite
    code
    data
    datalist
    del
    dfn
    em
    embed
    i
    iframe
    img
    input
    ins
    kbd
    keygen
    label
    map
    mark
    math
    meter
    noscript
    object
    output
    progress
    q
    ruby
    s
    samp
    script
    select
    small
    span
    strong
    sub
    sup
    svg
    template
    textarea
    time
    u
    var
    video
    wbr
    Text

Most elements that are categorized as phrasing content can only contain elements that are themselves categorized as phrasing content, not any flow conten
     */
}
