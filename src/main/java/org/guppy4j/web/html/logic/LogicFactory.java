package org.guppy4j.web.html.logic;

import org.guppy4j.web.html.Content;
import org.guppy4j.web.html.model.Variable;
import org.guppy4j.web.html.render.Renderer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.function.Function;

/**
 * Provides logic functions
 */
public class LogicFactory {

    @SafeVarargs
    public static <M, I, CM extends Content<M>> CM forEach(
            Function<M, Iterable<I>> items,
            Function<M, Variable<I>> holder,
            Class<? super CM> contentType,
            CM... contents) {

        final InvocationHandler handler = (proxy, method, args) -> {
            final Renderer renderer = (Renderer) args[0];
            final M model = (M) args[1];
            for (I item : items.apply(model)) {
                holder.apply(model).set(item);
                for (CM content : contents) {
                    content.render(renderer, model);
                }
            }
            return null;
        };

        final Object proxy = Proxy.newProxyInstance(contentType.getClassLoader(),
                new Class[]{contentType}, handler);

        return (CM) proxy;
    }
}
