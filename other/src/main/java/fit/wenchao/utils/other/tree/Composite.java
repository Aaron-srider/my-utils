package fit.wenchao.ldapauthdemo.utils.tree;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public
class Composite<T> {
    List<Composite<T>> children = new ArrayList<>();

    T value;

    public void doAdd(List<T> path, int level) {
        if (path.size() < level + 1) {
            return;
        }
        T curNode = path.get(level);
        Composite<T> child = getChild(curNode);
        if (child == null) {
            child = new Composite<T>();
            child.value = curNode;
            this.children.add(child);
        }

        child.doAdd(path, level + 1);
    }

    private Composite<T> getChild(T curNode) {
        for (Composite<T> child : this.children) {
            if (curNode.equals(child.value)) {
                return child;
            }
        }
        return null;
    }

    private static List<String> pathList2List(String path) {
        if (path == null || path.equals("")) {
            return null;
        }

        List<String> result = new ArrayList<>();
        String[] split = path.split("/");
        for (int i = 1; i < split.length; i++) {
            result.add(split[i]);
        }
        return result;
    }

    private static String getHead(String path) {
        if (path == null || path.equals("")) {
            return null;
        }
        String[] split = path.split("/");
        return split[1];
    }

    public Boolean isLeaf() {
        return this.children.isEmpty();
    }

    public Boolean isComposite() {
        return !this.isLeaf();
    }

    public void doTraverse() {
        if (this.value != null) {
            System.out.println(value);
        }
        for (int i = 0; i < this.children.size(); i++) {
            children.get(i).doTraverse();
        }
    }

    public void doTraverse(TriConsumer<T, Composite<T>, Integer> consumer, Composite<T> parent, Integer level) {
        if (this.value != null) {
            consumer.accept(value,parent , level);
        }
        for (int i = 0; i < this.children.size(); i++) {
            children.get(i).doTraverse(consumer, this, level + 1);
        }
    }
    public static interface TriConsumer<T, U, F>{
        void accept(T t, U u, F f) ;
    }
}