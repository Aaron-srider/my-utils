package fit.wenchao.utils.collection;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class SimpleFactories {
    public static <T> T[] ofArr(T... arr) {
        return arr;
    }

    public static <T> List<T> ofList(T... elems) {
        return new ArrayList<T>(asList(elems));
    }

    // region ofMap
    public static <K, V> Map<K, V> ofMap0(Object... input) {

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

    public static <K, V> Map<K, V> ofMap(K k1, V v1) {
        return ofMap0(k1, v1);
    }

    public static <K, V> Map<K, V> ofMap(K k1, V v1, K k2, V v2) {
        return ofMap0(k1, v1, k2, v2);
    }

    public static <K, V> Map<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3) {
        return ofMap0(k1, v1, k2, v2, k3, v3);
    }

    public static <K, V> Map<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return ofMap0(k1, v1, k2, v2, k3, v3, k4, v4);
    }

    public static <K, V> Map<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4,
                                         K k5, V v5) {
        return ofMap0(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
    }

    public static <K, V> Map<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4,
                                         K k5, V v5, K k6, V v6) {
        return ofMap0(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6);
    }

    public static <K, V> Map<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4,
                                         K k5, V v5, K k6, V v6, K k7, V v7) {
        return ofMap0(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7);
    }
    // endregion

    // region ofJson
    public static JSONObject ofJson(String key, Object value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, value);
        return jsonObject;
    }

    public static JSONObject ofJson(String k1, Object v1, String k2, Object v2) {
        JSONObject json = ofJson(k1, v1);
        json.put(k2, v2);
        return json;
    }


    public static JSONObject ofJson(String k1, Object v1, String k2, Object v2,
                                    String k3, Object v3) {
        JSONObject json = ofJson(k1, v1,
                k2, v2);
        json.put(k3, v3);
        return json;
    }

    public static JSONObject ofJson(String k1, Object v1, String k2, Object v2,
                                    String k3, Object v3, String k4, Object v4) {
        JSONObject json = ofJson(k1, v1,
                k2, v2,
                k3, v3);
        json.put(k4, v4);
        return json;
    }

    public static JSONObject ofJson(String k1, Object v1, String k2, Object v2,
                                    String k3, Object v3, String k4, Object v4,
                                    String k5, Object v5) {
        JSONObject json = ofJson(k1, v1,
                k2, v2,
                k3, v3,
                k4, v4);
        json.put(k5, v5);
        return json;
    }
    // endregion


}
