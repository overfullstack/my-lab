package vavr;/* gakshintala created on 10/1/19 */

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class TupleLab {

    @Test
    void map() {
        Tuple.of(1, 2).map(Tuple::of);
    }

    @Test
    void tupleCompare() {
        var tupleList = new ArrayList<Tuple2<Date, Date>>();
        tupleList.add(Tuple.of(new Date(), new Date()));
        tupleList.sort(Comparator.comparing(Tuple2::_1));
        System.out.println(tupleList);
    }
}
