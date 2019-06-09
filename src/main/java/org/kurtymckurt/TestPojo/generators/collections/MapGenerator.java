package org.kurtymckurt.TestPojo.generators.collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MapGenerator<K,V> extends GenericMapGenerator<K,V> {

   @Override public boolean supports(Class<?> clazz) {
      return clazz.isAssignableFrom(Map.class) || clazz.isAssignableFrom(ConcurrentMap.class);
   }

   @Override <K, V> Map<K, V> createInstance() {
      return new ConcurrentHashMap<>();
   }

}
