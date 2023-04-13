package com.bootlabs.springreactivei18n.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Locale;
import java.util.Objects;

/**
 * this class is responsible for choosing the right message based on the locale specified or/and arguments.
 */
@Component
public class Translator {

    private static MessageSource messageSource;

    private static LocaleResolver localeResolver;

    @Autowired
    Translator(MessageSource messageSource, LocaleResolver localeResolver) {
        Translator.messageSource = messageSource;
        Translator.localeResolver = localeResolver;
    }

    /**
     * @param msg  message code that should be translated.
     * @param args message parameters
     * @return translated message
     */
    public static String getMessage(String msg, Object[] args, ServerWebExchange exchange) {
        LocaleContext localeContext = localeResolver.resolveLocaleContext(exchange);
        return messageSource.getMessage(msg, args, msg, Objects.requireNonNull(localeContext.getLocale()));
    }
    /**
     * @param msg  message code that should be translated.
     * @param args message parameters
     * @return translated message
     */
    public static String getMessage(String msg, Object[] args) {
        LocaleContext localeContext = new SimpleLocaleContext(Locale.getDefault());
        return messageSource.getMessage(msg, args, msg, Objects.requireNonNull(localeContext.getLocale()));
    }

    /**
     * @param msg message code that should be translated.
     * @return translated message
     */
    public static String getMessage(String msg, ServerWebExchange exchange) {
        LocaleContext localeContext = localeResolver.resolveLocaleContext(exchange);
        return messageSource.getMessage(msg, null, msg, Objects.requireNonNull(localeContext.getLocale()));
    }

    /**
     * @param msg message code that should be translated.
     * @return translated message
     */
    public static String getMessage(String msg) {
        LocaleContext localeContext = new SimpleLocaleContext(Locale.getDefault());
        return messageSource.getMessage(msg, null, msg, Objects.requireNonNull(localeContext.getLocale()));
    }
}