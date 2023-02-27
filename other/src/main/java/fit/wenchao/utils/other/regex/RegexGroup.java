package fit.wenchao.ldapauthdemo.utils;

public class RegexGroup {
    private int index;
    private String value;

    public RegexGroup(int groupIndex, String value) {
        this.index = groupIndex;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }
}
