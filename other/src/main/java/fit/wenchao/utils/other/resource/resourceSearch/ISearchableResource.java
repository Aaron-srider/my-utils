package fit.wenchao.utils.other.resource.resourceSearch;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ISearchableResource {

    List<String> getSearchPath() throws
            NoSuchFieldException,
            IllegalAccessException,
            IOException;

    InputStream getResourceStream(String resourceName);

    /**
     * List resources names in specific directory.
     *
     * @param dirName Directory relative name in resource search path. Cannot start
     *                with "/", must end with "/"; For example, "com/example/"
     * @return Names of all resources in the dirName path;
     */
    List<String> listResourceAt(String dirName) throws IOException;
}
