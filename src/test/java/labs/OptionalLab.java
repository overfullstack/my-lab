package labs;/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * http://creativecommons.org/licenses/by-sa/4.0/
 */

import common.Bean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalLab {

    @Test
    void optionalWithNull() {
        Assertions.assertThrows(NullPointerException.class, () -> Optional.of(null));
    }

    @Test
    void immutability() {
        var bean = new Bean("str1", "str1", "");
        var beanFromOptional = Optional.of(bean)
                .map(b -> {
                    b.setProp("str2");
                    return b;
                })
                .get();
        System.out.println("Optional Mutates the value inside it" + bean.getProp());
        Assertions.assertSame(bean, beanFromOptional);
    }

    String optionalIfPresentBad() {
        Optional<Bean> mayBean = getMayBean();
        if (mayBean.isPresent()) {
            final var bean = mayBean.get();
            return bean.getProp();
        } else {
            return "";
        }
    }

    private Optional<Bean> getMayBean() {
        return Optional.of(new Bean());
    }

    String nullCheck() {
        final Bean bean = getBean();
        if (bean != null) {
            return bean.getProp();
        } else {
            return "";
        }
    }

    private Bean getBean() {
        return new Bean();
    }

    String OptionalComposable() {
        return getMayBean().map(Bean::getProp).orElse("");
    }

    String nestedNullCheck() {
        final Bean bean = getBean();
        if (bean != null) {
            final Bean nestedBean = bean.getNestedBean();
            if (nestedBean != null) {
                return nestedBean.getProp();
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    String optionalComposableNested() {
        return getMayBean().map(Bean::getNestedBean).map(Bean::getProp).orElse("");
    }

    @Test
    void emptyGet() {
        Assertions.assertThrows(NoSuchElementException.class, () -> Optional.empty().get());
    }
}
