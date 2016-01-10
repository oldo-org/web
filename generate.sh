#!/bin/bash -x

for tag_file in $(ls html-impl/src/main/java/org/guppy4j/html/tag/*.java)
do
    tag="$(basename -s .java ${tag_file})"

    sed -i "s/Span/${tag}/g" \
    html/src/main/java/org/guppy4j/html/marker/${tag}Attribute.java \
    html/src/main/java/org/guppy4j/html/marker/${tag}Content.java \
    html-impl/src/main/java/org/guppy4j/html/tag/${tag}.java

    tag_lowered=$(echo ${tag} | tr '[:upper:]' '[:lower:]')
    sed -i "s/span/${tag_lowered}/g" \
    html/src/main/java/org/guppy4j/html/marker/${tag}Attribute.java \
    html/src/main/java/org/guppy4j/html/marker/${tag}Content.java \
    html-impl/src/main/java/org/guppy4j/html/tag/${tag}.java

    test $(cat ${tag_file} | wc -l) -gt 7 && continue

    cp html/src/main/java/org/guppy4j/html/marker/SpanAttribute.java \
       html/src/main/java/org/guppy4j/html/marker/${tag}Attribute.java
    cp html/src/main/java/org/guppy4j/html/marker/SpanContent.java \
       html/src/main/java/org/guppy4j/html/marker/${tag}Content.java
    cp html-impl/src/main/java/org/guppy4j/html/tag/Span.java \
       html-impl/src/main/java/org/guppy4j/html/tag/${tag}.java

done
