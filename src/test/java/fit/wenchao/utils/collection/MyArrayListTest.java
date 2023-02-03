package fit.wenchao.utils.collection;

import fit.wenchao.utils.basic.BasicUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static fit.wenchao.utils.basic.BasicUtils.forList;
import static fit.wenchao.utils.string.placeholderString.TemplateString.ft;
import static java.lang.System.out;
import static org.junit.Assert.assertTrue;


public class MyArrayListTest {

    MyArrayList<String> list = new MyArrayList<>();

    @Before
    public void init() {
        list.add("string");
        list.add("adding");
        list.add("list");
    }

    @Test
    public void last() {


        Assert.assertEquals(ft("{}", list.last()),
                "list");

        list.clear();

        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                {
                    out.println(list.last());
                }
        );
    }

    @Test
    public void asList() throws Exception {
        String[] arr = new String[list.size()];
        list.toArray(arr);

        MyArrayList<String> strings =
                MyArrayList.asList(arr);

        BasicUtils.gloop(forList(strings), (i, e, s) -> {
            assertEquals(e, arr[i]);
        });
    }

    @Test
    public void isLastIdx() {
        assertTrue(list.isLastIdx(list.size() - 1));
    }
}