package vavr;

import common.Bean;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.function.Function;

public class CollectionsLab {

    @Test
    void reduceApply() {
        List<Function<Bean, String>> functionList = List.of(Bean::getProp1, Bean::getProp2, bean -> bean.getProp1() + bean.getProp2());
        var bean = new Bean("1", "A", Collections.emptyList());
    }
}
