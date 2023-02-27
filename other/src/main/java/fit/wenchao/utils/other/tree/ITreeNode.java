package fit.wenchao.ldapauthdemo.utils.tree;

import java.util.List;

public
interface ITreeNode<N>{
    List<N> getChildren(N node);

    List<N> getChildren(N node, String condition, Object value);

    N getParent(N node);

    default List<N> getNextSibling(N node) {
        throw new UnsupportedOperationException();
    }

    default List<N> getPreviousSibling(N node) {
        throw new UnsupportedOperationException();
    };
}