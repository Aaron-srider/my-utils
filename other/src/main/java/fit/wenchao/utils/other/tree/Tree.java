package fit.wenchao.ldapauthdemo.utils.tree;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class Tree<T> {


    public static void main(String[] args) {
        List<List<String>> pathList = Arrays.asList(
                Arrays.asList("root"),
                Arrays.asList("root", "test"),
                Arrays.asList("root", "test", "test_dev"),
                Arrays.asList("root", "test", "test_design"),
                Arrays.asList("root", "product"),
                Arrays.asList("root", "product", "product_design"),
                Arrays.asList("root", "product", "product_support")
        );


        Tree<String> tree = Tree.construct(pathList);

        //tree.traverse((node) -> {
        //    System.out.println(node);
        //});
        tree.add(Arrays.asList("root", "product", "product_dev"));

        //tree.traverse();
        tree.preOrderTraverse((node, parent, level) -> {
            System.out.println(node);
            System.out.println(parent.value);
            System.out.println(level);
        });
    }

    Composite<T> composite = new Composite<T>();

    public void add(List<T> path) {
        if (composite == null) {
            composite = new Composite<T>();
        }
        composite.doAdd(path, 0);
    }

    public static <T> Tree<T> construct(List<List<T>> pathList) {

        Tree<T> tree = new Tree<>();

        pathList.forEach((path) -> {
            tree.add(path);
        });

        return tree;
    }


    public void preOrderTraverse() {
        if (composite.isLeaf()) {
            System.out.println("tree empty");
            return;
        }

        this.composite.doTraverse();
    }


    public void preOrderTraverse(Composite.TriConsumer<T, Composite<T>, Integer> consumer) {
        if (composite.isLeaf()) {
            System.out.println("tree empty");
            return;
        }

        this.composite.doTraverse(consumer, null, -1);
    }

}



