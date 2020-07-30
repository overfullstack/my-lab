package labs;

import common.BeanRecord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * gakshintala created on 5/19/20.
 */
public class RecordLab {
    @Test
    void checkImmutability() {
        List<String> list = new ArrayList<>();
        var beanRecord = new BeanRecord(list);
        list.add("abc"); 
        System.out.println(beanRecord.props().size()); // Not immutable
    }
}
