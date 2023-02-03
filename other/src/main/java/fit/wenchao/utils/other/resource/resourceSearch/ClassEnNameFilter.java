package fit.wenchao.utils.other.resource.resourceSearch;

public class ClassEnNameFilter implements INameFilter {
    @Override
    public boolean filter(String name) {
        return name.endsWith(".class");
    }
}
