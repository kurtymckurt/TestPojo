package org.kurtymckurt.TestPojo.generators.collections;

import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class NavigableMapGenerator<K,V> extends GenericMapGenerator<K,V> {


   @Override public boolean supports(Class<?> clazz) {
      return clazz.isAssignableFrom(NavigableMap.class);
   }

   @Override <K, V> Map<K, V> createInstance() {
      return new ConcurrentSkipListMap<>();
   }
}
