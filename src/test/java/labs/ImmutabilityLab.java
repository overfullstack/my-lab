package labs;

import lombok.Value;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * gakshintala created on 5/11/20.
 */
public class ImmutabilityLab {
    @Test
    void checkImmutability() {
        var props = new ArrayList<String>();
        props.add("abc");
        var immutableBean = new ImmutableBean(props);
        System.out.println(immutableBean.getProps()); // abc
        props.add("pqr");
        System.out.println(immutableBean.getProps()); // abc pqr
    }
}


@Value
class ImmutableBean {
    List<String> props;
}
