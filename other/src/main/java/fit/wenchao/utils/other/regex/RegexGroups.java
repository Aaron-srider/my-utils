package fit.wenchao.ldapauthdemo.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RegexGroups implements Iterable<RegexGroup>{
    List<RegexGroup> groups  = new ArrayList<>();
    public void addGroupValue(String group) {
        this.groups.add(new RegexGroup(this.groups.size() + 1, group));
    }

    public int getSize() {
        return this.groups.size();
    }

    @NotNull
    @Override
    public Iterator<RegexGroup> iterator() {
        return this.groups.iterator();
    }

    public RegexGroup getByIndex(int i) {
        return this.groups.get(i - 1);
    }
}
