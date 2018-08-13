package com.github.anddd7.model.bilibili.entity;

import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import org.junit.Assert;
import org.junit.Test;

public class Top10OfPublishedRecordTest {

  @Test
  public void comparator_withKeyExtractor() {
    ToIntFunction<Integer> toIntFunction = i -> i;
    Comparator<Integer> intComparator = Comparator.comparingInt(toIntFunction);

    // ----equals----
    // Function<Integer, Integer> keyExtractor = i -> i;
    // Comparator<Integer> intComparator = Comparator.comparing(keyExtractor);

    List<Integer> list = Lists.newArrayList(2, 1, 3, 5, 4);
    list.sort(intComparator);
    Assert.assertEquals(1, (int) list.get(0));
  }

  @Test
  public void comparator_withKeyExtractorAndKeyComparator() {
    Function<Integer, Integer> keyExtractor = i -> i;
    Comparator<Integer> intComparator = Comparator.comparing(
        keyExtractor,
        (x, y) -> {
          // default : -1 保持位置, 即小的在前; 1 互换位置, 即大的在后; ()
          // return (x < y) ? -1 : ((x == y) ? 0 : 1);
          // return Integer.compare(x,y);
          return x < y ? 1 : x == y ? 0 : -1;
        }
    );

    List<Integer> list = Lists.newArrayList(2, 1, 3, 5, 4);
    list.sort(intComparator);
    Assert.assertEquals(5, (int) list.get(0));
  }
}