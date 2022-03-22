package fit.wenchao.utils.string;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static fit.wenchao.utils.basic.BasicUtils.forArr;
import static fit.wenchao.utils.basic.BasicUtils.gloop;
import static org.assertj.core.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class StrUtilsTest {

    @Test
    public void testNullElem() throws Exception {
        Object[] objects = new Object[3];
        Arrays.fill(objects, null);
        String outf = StrUtils.outf("objects:{}", asList(objects));
        assertThat(outf, equalTo("objects:[null, null, null]"));
    }

    @Test
    public void outf() {

        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");

        String[] arr = new String[list.size()];
        list.toArray(arr);

        String spliter = "===";
        Assert.assertEquals(StrUtils.outf("map:{},{},obj:{}", map, spliter, "test"),
                "map:{key1=value1, key2=value2}," + spliter + ",obj:test");
        Assert.assertEquals(StrUtils.outf("map:{},{},list:{}", map, spliter, list),
                "map:{key1=value1, key2=value2}," + spliter + ",list:[hello, world]");
        Assert.assertEquals(StrUtils.outf("map:''{},{},map:{}", map, spliter, map),
                "map:'{key1=value1, key2=value2}," + spliter + ",map:{key1=value1, key2=value2}");
        Assert.assertEquals(StrUtils.outf("map:{}'',{},map:{}", map, spliter, map),
                "map:{key1=value1, key2=value2}'," + spliter + ",map:{key1=value1, key2=value2}");
        Assert.assertEquals(StrUtils.outf("map:''{}'',{},map:{}", map, spliter, map),
                "map:'{key1=value1, key2=value2}'," + spliter + ",map:{key1=value1, key2=value2}");

        Assert.assertEquals(StrUtils.outf("arr:''{}'',{},arr:{}", arr, spliter, arr),
                "arr:'[hello, world]'," + spliter + ",arr:[hello, world]");

        Assert.assertEquals(StrUtils.outf("arr:'{},{},arr:{}", arr, spliter, arr),
                "arr:{},{},arr:{}");
        Assert.assertEquals(StrUtils.outf("arr:{},'{},arr:{}", arr, spliter, arr),
                "arr:[hello, world],{},arr:{}");
        Assert.assertEquals(StrUtils.outf("arr:'{}','{},arr:{}", arr, spliter, arr),
                "arr:{},{},arr:{}");


    }
}