package fit.wenchao.utils.string;

import java.io.File;

import static fit.wenchao.utils.basic.BasicUtils.forArr;
import static fit.wenchao.utils.basic.BasicUtils.gloop;
import static fit.wenchao.utils.string.StrUtils.filePathBuilder;

public class DirStr {
    String dirStr;
    DirStringType dirStringType;

    enum DirStringType {
        PCK,
        PATH
    }

    public DirStr(String dirStr) {
        setDirStr(dirStr);
    }

    public void setDirStr(String dirStr) {
        this.dirStr = filePathBuilder(dirStr).build();
        if (dirStr.contains(".")) {
            dirStringType = DirStringType.PCK;
        } else if (dirStr.contains(File.separator)) {
            dirStringType = DirStringType.PATH;
        }
    }

    public String toPck() {
        if (this.dirStringType.equals(DirStringType.PCK)) {
            return dirStr;
        } else if (this.dirStringType.equals(DirStringType.PATH)) {
            return convertFromPathStrToPckStr(this.dirStr);
        }
        return null;
    }


    public String toPath() {
        if (this.dirStringType.equals(DirStringType.PATH)) {
            return dirStr;
        } else if (this.dirStringType.equals(DirStringType.PCK)) {
            return convertFromPckStrToPathStr(this.dirStr);
        }
        return null;
    }

    private String convertFromPathStrToPckStr(String pathStr) {
        StringBuilder sb = new StringBuilder();

        String splitSeparator = File.separator;

        if(File.separator.equals("\\")) {
            //正则中\也要转义，所以\的正则应该是\\
            splitSeparator = "\\\\";
        }

        String[] split = pathStr.split(splitSeparator);

        try {
            gloop(forArr(split), (i, e, s) -> {
                if (i == 0) {
                    s.continueLoop();
                    return;
                }
                sb.append(e);
                if (split.length - 1 != i) {
                    sb.append(".");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String str = filePathBuilder().ct("com").ct("example").build() ;

        DirStr dirStr = new DirStr("\\hello\\world/jiaf");

        StrUtils.outf("path:{}, pck:{}", dirStr.toPath(), dirStr.toPck());

        dirStr.setDirStr("com.example");

        StrUtils.outf("path:{}, pck:{}", dirStr.toPath(), dirStr.toPck());
    }

    private String convertFromPckStrToPathStr(String pckStr) {
        StringBuilder sb = new StringBuilder();
        sb.append(File.separator);

        String[] split = pckStr.split("\\.");

        try {
            gloop(forArr(split), (i, e, s) -> {
                sb.append(e);
                if (split.length - 1 != i) {
                    sb.append(File.separator);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}