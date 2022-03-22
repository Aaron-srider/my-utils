package fit.wenchao.utils.collection;

import java.util.HashMap;
import java.util.Map;

public class MapService {

    Map<String, Object> innerMap;

    private MapService(Map<String, Object> map) {
        this.innerMap = map;
    }

    public MapService put(String k, Object v) {
        innerMap.put(k, v);
        return this;
    }

    public Map<String, Object> getInnerMap(){
        return innerMap;
    }

    public static MapService getDefaultMap() {
        return new MapService(new HashMap<String, Object>());
    }

    public static <K, V> Map<K, V> ofMap(Object... input) {

        if ((input.length & 1) != 0) {
            throw new IllegalArgumentException("length is odd");
        }

        HashMap<K, V> kvHashMap = new HashMap<>();
        for (int i = 0; i < input.length; i += 2) {
            K k = (K) input[i];
            V v = (V) input[i + 1];
            kvHashMap.put(k, v);
        }
        return kvHashMap;
    }

    public static <K, V> Map<K, V> of(K k1, V v1) {
        return ofMap(k1, v1);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
        return ofMap(k1, v1, k2, v2);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        return ofMap(k1, v1, k2, v2, k3, v3);
    }


    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return ofMap(k1, v1, k2, v2, k3, v3, k4, v4);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4,
                                      K k5, V v5) {
        return ofMap(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4,
                                      K k5, V v5, K k6, V v6) {
        return ofMap(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4,
                                      K k5, V v5, K k6, V v6, K k7, V v7) {
        return ofMap(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7);
    }
}