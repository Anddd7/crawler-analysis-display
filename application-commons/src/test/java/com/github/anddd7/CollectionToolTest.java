package com.github.anddd7;

import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;

public class CollectionToolTest {

  @Test
  public void getFirst_FindExistedElementAndReturn() {
    Integer[] ints = {1, 2, 3, 4, 5};

    Optional<Integer> first = CollectionTool.findFirst(ints, integer -> integer > 3);

    Assert.assertTrue(first.isPresent());
    Assert.assertEquals(Integer.valueOf(4), first.get());
  }
}