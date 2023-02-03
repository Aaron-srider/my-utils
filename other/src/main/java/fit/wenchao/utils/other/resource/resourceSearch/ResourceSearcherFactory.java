package fit.wenchao.utils.other.resource.resourceSearch;

public class ResourceSearcherFactory {

    public static ISearchableResource getSystemSearcher() {
        return SystemSearchableResource.getSingleton();
    }

    //public static ISearchableResource getCustomSearcher() {
    //    return CustomSearchableResource.getSingleton();
    //}

}
