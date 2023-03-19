package org.oldo.html;

/**
 * Enumeration of all valid HTML5 tag, based on the
 * <a href="https://developer.mozilla.org/en-US/docs/Web/HTML/Element">
 * HTML5 element list</a> at developer.mozilla.org.
 */
public enum Element {

    /* Root elements */

    /**
     * Root of an HTML or XHTML document. All other tag
     * must be descendants of this element.
     */
    html,

    /**
     * Main content of an HTML document.
     * There is only one body element in a document.
     */
    body,

    /* Document metadata */

    /**
     * A section for metadata about the document, including links to or
     * definitions of scripts and style sheets.
     */
    head,

    /**
     * Title of the document, typically shown in browser as window and tab
     * title. It can only contain text, contained tag are not interpreted.
     */
    title,
    /**
     * Base URL for relative URL in the page
     */
    base,
    /**
     * Can link JavaScript and external CSS with the current document
     */
    link,
    /**
     * Metadata that cannot be defined using other tag
     */
    meta,
    /**
     * Inline CSS that applies only to this document
     */
    style,

    /* Scripting */

    /**
     * Either an internal script or link to an external script.
     * The script language is JavaScript.
     */
    script,
    /**
     * Alternative content to display when the browser doesn't support scripting.
     */
    noscript,

    /* --- Content sectioning --- */

    /**
     * Content provides contact information for a person or people, or for an organization
     */
    address,

    /**
     * A standalone section, which doesn't have a more specific semantic element to represent it,
     * contained within an HTML document
     */
    section,

    /**
     * Defines a section that contains only navigation links, either within the current document
     * or to other documents. Examples are menus, tables of contents, and indexes.
     */
    nav,

    /**
     * Introductory content, typically a group of introductory or navigational aids. It may contain
     * some heading elements but also other elements like a logo, a search form, an author name, etc
     */
    header,

    /**
     * A footer for its nearest sectioning content or sectioning root element. A footer typically
     * contains info about the author of the section, copyright data or links to related documents.
     */
    footer,

    /**
     * A self-contained composition in a document, page, application, or site, intended to be
     * independently distributable or reusable (e.g., in syndication).
     * Examples include: a forum post, a magazine or newspaper article, or a blog entry.
     */
    article,

    /**
     * Content that is only indirectly related to the document's main content
     */
    aside,

    /**
     * Six levels of section headings, h1 is the highest and h6 is the lowest
     */
    h1, h2, h3, h4, h5, h6,

    /**
     * A multi-level heading for a section of a document. It groups a set of h1 - h6 elements.
     */
    hgroup,

    /* --- Grouping content --- */

    /**
     * A generic container with no special meaning.
     */
    div,

    /* --- Forms --- */

    /**
     * A formular, consisting of controls, that can be submitted to a server for processing.
     */
    form,
    /**
     * A set of controls
     */
    fieldset,
    /**
     * The caption for a fieldset
     */
    legend,
    /**
     * RThe caption of a form control.
     */
    label,

    /* --- Text-level semantics --- */

    /**
     * Doesn't mean anything on its own, but can be useful when used together with the global attributes,
     * e.g. class, lang, or dir. It represents its children.
     */
    span

/*
Element 	Description
<body>
Represents the main content of an HTML document. There is only one <body> element in a document.
<section> This element has been added in HTML5 	Defines a section in a document
<nav> This element has been added in HTML5 	Defines a section that contains only navigation links
<article> This element has been added in HTML5 	Defines self-contained content that could exist independantly of the rest of the content
<aside> This element has been added in HTML5 	Defines some content set aside from the rest of page content. If it is removed, the remaining content still make sense.
<h1>,<h2>,<h3>,<h4>,<h5>,<h6> 	Heading tag implement six levels of document headings, <h1> is the most important and <h6> is the least. A heading element briefly describes the topic of the section it introduces.
<hgroup> This element has been added in HTML5 	Groups a set of <h1> to <h6> tag when a heading has multiple levels
<header> This element has been added in HTML5 	Defines the header of a page or section. It often contains a logo, the title of the Web site and a navigational table of content.
<footer> This element has been added in HTML5 	Defines the footer for a page or section. It often contains a copyright notice, some links to legal information or addresses to give feedback.
<address> 	Defines a section containing contact information.
<main>This element has been added in HTML5 	Defines the main or important content in the document. There is only one <main> element in the document.

Grouping content

Element 	Description
<p> 	Defines a portion that should be displayed as a paragrah.
<hr> 	Represents a thematic break between paragraphs of a section or article or any longer content.
<pre> 	Indicates that its content is preformatted and that this format must be preserved.
<blockquote> 	Represents a citation.
<ol> 	Defines an ordered list of items, that is a list which change its meaning if we change the order of its tag
<ul> 	Defines an unordered list of items.
<li> 	Defines a item of a enumeration list often preceded by a bullet in English.
<dl> 	Defines a definition list, that is a list of terms and their associated definitions.
<dt> 	Represents a term defined by the next <dd>.
<dd> 	Represents the definition of the terms immediately listed before it.
<figure> This element has been added in HTML5 	Represents a figure illustrated a part of the document.
<figcaption> This element has been added in HTML5 	Represents the legend of a figure.
Text-level semantics
Element 	Description
<a> 	Represents an hyperlink, linking to another resource.
<em> 	Represents emphasized text, like a stress accent.
<strong> 	Represents especially important text.
<small> 	Represents a side comment, that is text like a disclaimer, a copyright which is not essential to the comprehension of the document.
<s> 	Represents content that is no longer accurate or relevant.
<cite> 	Represents the title of a work.
<q> 	Represents an inline quotation.
<dfn> 	Represents a term whose definition is contained in its nearest ancestor content.
<abbr> 	Represents an abbreviation or an acronym, eventually with its meaning.
<data> This element has been added in HTML5 	Associates to its content a machine-readable equivalent. (This element is only in the WHATWG version of the HTML standard, and not in the W3C version of HTML5).
<time> This element has been added in HTML5 	Represents a date and time value, eventually with a machine-readable equivalent.
<code> 	Represents some computer code.
<var> 	Represents a variable, that is an actual mathematical expression or programming context, an identifier representing a constant, a symbol identifying a physical quantity, a function parameter, or a mere placeholder in prose.
<samp> 	Represents the output of a program or a computer.
<kbd> 	Represents user input, often from the keyboard, but not necessary, it may represent other input, like transcribed voice commands.
<sub>,<sup> 	Represents a subscript, respectively a superscript.
<i> 	Represents some text in an alternate voice or mood, or at least of different quality, such as a taxonomic designation, a technical term, an idiomatic phrase, a thought or a ship name.
<b> 	Represents a text which to which attention is drawn for utilitarian purposes. It doesn't convey extra importance and doesn't implicate an alternate voice.
<u> 	Represents unarticulate non-textual annoatation, such labeling the text as being misspelt or labeling a proper name in Chinese text.
<mark> This element has been added in HTML5 	Represents text highlighted for reference purposes, that is for its relevance in another context.
<ruby> This element has been added in HTML5 	Represents content to be marked with ruby annotations, short runs of text presented alongside the text. This is often used in conjunction with East Asian language where the annotations act as a guide for pronunciation, like the Japanese furigana.
<rt> This element has been added in HTML5 	Represents the text of a ruby annotation.
<rp> This element has been added in HTML5 	Represents parenthesis around a ruby annotation, used to display the annotation in an alternate way by browsers not supporting the standard display for annotations.
<bdi> This element has been added in HTML5 	Represents text that must be isolated from its surrounding for bidirectional text formatting. It allows to embed element of text with a different, or unknown, directionality.
<bdo> 	Represents the directionality of its children, in order to explicitly override the Unicode bidirectional algorithm.
<element> 	Represents text with no specific meaning. This has to be used when no other text-semantic element conveys an adequate meaning, which, in this case, is often brought by global attribute like class, lang, or dir.
<br> 	Represents a line break.
<wbr> This element has been added in HTML5 	Represents a line break opportunity, that is a suggested wrapping point in order to improve readability of text split on several lines.
Edits
Element 	Description
<ins> 	Defines an addition to the document.
<del> 	Defines a removal from the document.
Embedded content
Element 	Description
<img> 	Represents an image.
<iframe> 	Represents a nested browsing context, that is an embedded HTML document.
<embed> This element has been added in HTML5 	Represents a integration point for an external, often non_HTML, application or interactive content.
<object> 	Represents an external resource, which will be treated as an image, an HTML sub-document or an external resource to be processed by a plugin.
<param> 	Defines parameters for use by plugins invoked by <object> tag.
<video> This element has been added in HTML5 	Represents a video, and its associated audio files and captions, with the necessary interface to play it.
<audio> This element has been added in HTML5 	Represents a sound, or an audio stream.
<source> This element has been added in HTML5 	Allows authors to specify alternative media resources for media tag like <video> or <audio>.
<track> This element has been added in HTML5 	Allows authors to specify timed text track for media tag like <video> or <audio>.
<canvas> This element has been added in HTML5 	Represents a bitmap area that scripts can be used to render graphics, like graphs, game graphics, any visual images on the fly.
<map> 	In conjunction with <area>, defines an image map.
<area> 	In conjunction with <map>, defines an image map.
<svg> This element has been added in HTML5 	Defines an embedded vectorial image.
<math> This element has been added in HTML5 	Defines a mathematical formula.
Tabular data
Element 	Description
<table> 	Represents data with more than one dimension.
<caption> 	Represents the title of a table.
<colgroup> 	Represents a set of one or more columns of a table.
<col> 	Represents a column of a table.
<tbody> 	Represents the block of rows that describes the concrete data of a table.
<thead> 	Represents the block of rows that describes the column labels of a table.
<tfoot> 	Represents the block of rows that describes the column summaries of a table.
<tr> 	Represents a row of cells in a table.
<td> 	Represents a data cell in a table.
<th> 	Represents a header cell in a table.

Forms

Element 	Description
<input> 	Represents a typed data field allowing the user to edit the data.
<button> 	Represents a button.
<select> 	Represents a control allowing the selection among a set of options.
<datalist> This element has been added in HTML5 	Represents a set of predefined options for other controls.
<optgroup> 	Represents a set of options, logically grouped.
<option> 	Represents an option in a <select> element, or a suggestion of a <datalist> element.
<textarea> 	Represents a multiline text edit control.
<keygen> This element has been added in HTML5 	Represents a key pair generator control.
<output> This element has been added in HTML5 	Represents the result of a calculation.
<progress> This element has been added in HTML5 	Represents the completion progress of a task.
<meter> This element has been added in HTML5 	Represents a scalar measurement (or a fractional value), within a known range
Interactive tag
Element 	Description
<details> This element has been added in HTML5 	Represents a widget from which the user can obtain additional information or controls.
<summary> This element has been added in HTML5 	Represents a summary, caption, or legend for a given <details>.
<command> This element has been added in HTML5 	Represents a command that the user can invoke.
<menu> This element has been added in HTML5 	Represents a list of commands.

     */
}
