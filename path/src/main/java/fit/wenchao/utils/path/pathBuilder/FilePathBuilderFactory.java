package fit.wenchao.utils.path.pathBuilder;

public class FilePathBuilderFactory {

    public static IFilePathBuilder of() {
        return new FilePathBuilder();
    }

}
