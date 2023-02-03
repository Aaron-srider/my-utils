package fit.wenchao.utils.other.resource.resourceSearch;

public class ClassNameFilter implements INameFilter {
    @Override
    public boolean filter(String name) {
        return name.endsWith(".class");
    }
}
