package eggservice;

import org.immutables.value.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

class MiscLab {
  @Test
  void mutateList() {
    var list = new ArrayList<String>();
    list.add("a");
    list.add("b");

    var list2 = list.stream().map(String::toUpperCase).collect(Collectors.toList());
    System.out.println(list);
    System.out.println(list2);
  }

  @Test
  void hashMapTest() {
    var hm = new HashMap<Integer, List<String>>();
    var list = new ArrayList<String>();
    for (var i = 0; i < 3; i++) {
      list.add(String.valueOf(i));
      hm.put(i, list);
    }
    System.out.println(hm);
  }

  @Test
  void dateLab() {
    var date = new Date();
    var dateWrapper = ImmutableDateWrapper.of(date);
    date.setDate(2);
    System.out.println(dateWrapper.date());
  }

  @Test
  void mutateHmKey() {
    var date1 = new Date();
    date1.setDate(1);
    var dateWrapper1 = ImmutableDateWrapper.of(date1);
    var date2 = new Date();
    date2.setDate(2);
    var dateWrapper2 = ImmutableDateWrapper.of(date2);
    var hm = new HashMap<DateWrapper, String>();
    hm.put(dateWrapper1, "date1");
    hm.put(dateWrapper2, "date2");

    System.out.println("Before: " + hm);
    date1.setDate(2);
    System.out.println("After: " + hm);
    System.out.println("Size: " + hm.size());
    System.out.println(hm.get(dateWrapper1));
  }

  @Value.Style(allParameters = true)
  @Value.Immutable
  interface DateWrapper {
    Date date();
  }
}

