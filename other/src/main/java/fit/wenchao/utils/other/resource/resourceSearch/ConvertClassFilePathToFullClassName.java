package fit.wenchao.utils.other.resource.resourceSearch;

public class ConvertClassFilePathToFullClassName implements IStringMapper {


    @Override
    public String convert(String classFilePath) {
        int i = classFilePath.lastIndexOf(".class");
        classFilePath = classFilePath.substring(0, i);
        classFilePath = classFilePath.replace("/", ".");
        classFilePath = classFilePath.substring(1, classFilePath.length());
        return classFilePath;
    }
}
