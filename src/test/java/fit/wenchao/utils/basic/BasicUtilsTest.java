package fit.wenchao.utils.basic;

import fit.wenchao.utils.collection.MyArrayList;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static fit.wenchao.utils.basic.BasicUtils.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class BasicUtilsTest {

    private List<String> dataList() {
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("me");
        list.add("and");
        list.add("you");
        return list;
    }

    private String[] dataArr() {
        List<String> list = dataList();
        String[] strs = new String[list.size()];
        list.toArray(strs);
        return strs;
    }


    @Test
    public void testLoop_loopCount_state() throws Exception {
        //continue
        List<Integer> rst = new ArrayList<>();
        BasicUtils.loop(10, (i, state) -> {
            if (i == 5) {
                state.continueLoop();
                return;
            }
            rst.add(i);
        });
        assertTrue(!rst.contains(5));
        assertTrue(rst.size() == 9);
        rst.clear();

        //break;
        BasicUtils.loop(10, (i, state) -> {
            if (i == 5) {
                state.breakLoop();
                return;
            }
            rst.add(i);
        });
        for (int i = 0; i < 10; i++) {
            if (i >= 5) {
                assertTrue(!rst.contains(i));
            } else {
                assertTrue(rst.contains(i));
            }
        }
        assertTrue(rst.size() == 5);
    }

    @Test
    public void testLoop_loopCount_state_flag() throws Exception {
        //found
        List<Integer> rst = new ArrayList<>();
        boolean flag = BasicUtils.loop(10, (i, state, f) -> {
            if (i == 5) {
                state.breakLoop();
                f.set(true);
                return;
            }
            rst.add(i);
        });
        assertTrue(flag);
        for (int i = 0; i < 10; i++) {
            if (i >= 5) {
                assertTrue(!rst.contains(i));
            } else {
                assertTrue(rst.contains(i));
            }
        }
        assertTrue(rst.size() == 5);
        rst.clear();

        //not found
        flag = BasicUtils.loop(5, (i, state, f) -> {
            if (i == 6) {
                state.breakLoop();
                f.set(true);
                return;
            }
            rst.add(i);
        });
        assertFalse(flag);
        for (int i = 0; i < 10; i++) {
            if (i >= 5) {
                assertTrue(!rst.contains(i));
            } else {
                assertTrue(rst.contains(i));
            }
        }
        assertTrue(rst.size() == 5);
    }


    @Test
    public void testLoop_TraSeq_improve() throws Exception {
        //tra list
        List<String> list = dataList();
        List<String> traRst = new ArrayList<>();
        BasicUtils.gloop(forList(list), (index, item, state) -> {
            traRst.add(item);
        });
        for (int i = 0; i < list.size(); i++) {
            Assert.assertEquals(list.get(i), traRst.get(i));
        }
        traRst.clear();

        //tra arr
        String[] strs = dataArr();
        BasicUtils.gloop(forArr(strs), (index, item, state) -> {
            traRst.add(item);
        });
        for (int i = 0; i < list.size(); i++) {
            Assert.assertEquals(list.get(i), traRst.get(i));
        }

        //tra string
        String str = "hello";
        StringBuilder rstSb = new StringBuilder("");
        BasicUtils.gloop(forStr(str), (idx, ch, state) -> {
            rstSb.append(ch);
        });
        assertEquals(rstSb.toString(), str);

        //tra integer
        AtomicInteger count = new AtomicInteger();
        BasicUtils.gloop(forInt(5), (idx, item, state) -> {
            MatcherAssert.assertThat(count.getAndIncrement(), equalTo(item));
        });
        assertEquals(rstSb.toString(), str);
    }

    @Test
    public void testLoop_TraSeq_flag_improve() throws Exception {

        //found
        List<String> list = dataList();
        List<String> rst = new ArrayList<>();
        boolean flag = BasicUtils.gloop(forList(list), (i, item, state, f) -> {
            if ("me".equals(item)) {
                state.breakLoop();
                f.set(true);
                return;
            }
            rst.add(item);
        });
        assertTrue(flag);
        rst.contains(list.get(0));
        assertTrue(rst.size() == 1);
        rst.clear();

        //not found
        flag = BasicUtils.gloop(forList(list), (i, item, state, f) -> {
            if ("hello world".equals(item)) {
                state.breakLoop();
                f.set(true);
                return;
            }
            rst.add(item);
        });
        assertFalse(flag);
        assertEquals(4, rst.size());
    }

    @Test
    public void tra_enumeration() throws Exception {
        EnumList<String> eList = new EnumList<>();
        eList.add("hello");
        eList.add("world");
        eList.add("list");

        MyArrayList<Boolean> hasNextList = MyArrayList.getDefaultList(Boolean.class);
        MyArrayList<String> elemList = MyArrayList.getDefaultList(String.class);
        gloop(eList, (e, s, hasNext) -> {
            elemList.add(e);
            System.out.println("cur: " + e);
            hasNextList.add(hasNext);
            System.out.println("hasNext: " + hasNext);
        });

        for(int i = 0; i < hasNextList.size(); i++){
            if(hasNextList.isLastIdx(i)) {
                assertThat(hasNextList.get(i),equalTo(false));
            }else {
                assertThat(hasNextList.get(i),equalTo(true));
            }
            assertThat(elemList.get(i), equalTo(eList.get(i)));
        }
    }

    @Test
    public void testNullElem() throws Exception {
        Object[] objects = new Object[3];
        Arrays.fill(objects, null);
        gloop(forArr(objects), (i,e,s)->{
            System.out.println(e);
        });
    }

}

class EnumList<E> extends ArrayList<E> implements Enumeration<E> {

    int cur = 0;

    @Override
    public boolean hasMoreElements() {
        return cur < size();
    }

    @Override
    public E nextElement() {
        return get(cur++);
    }
}