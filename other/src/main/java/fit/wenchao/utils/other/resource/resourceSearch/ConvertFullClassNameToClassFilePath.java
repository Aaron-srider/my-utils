package fit.wenchao.utils.other.resource.resourceSearch;

import com.ndsec.app.AppImageLoader.constant.CommonConstants;

public class ConvertFullClassNameToClassFilePath implements IStringMapper {
    @Override
    public String convert(String classFullName) {
        String jarRelativeName = classFullName.replace(".", "/");
        return jarRelativeName + CommonConstants.ENCRYPTED_CLASS_FILE_SUFFIX;
    }

}
