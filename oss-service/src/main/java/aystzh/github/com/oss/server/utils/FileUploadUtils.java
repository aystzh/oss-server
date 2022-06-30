package aystzh.github.com.oss.server.utils;

import cn.hutool.core.util.ReUtil;

import java.io.File;
import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Created by zhanghuan on 2022/6/28.
 */
public class FileUploadUtils {

    public static final int KB_SIZE = 1024;
    public static final int MB_SIZE = 1024 * KB_SIZE;
    public static final int GB_SIZE = 1024 * MB_SIZE;



    /***
     * 创建文件夹
     * @param file
     */
    public static void createDirectoryQuietly(File file) {
        if (file != null) {
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    throw new RuntimeException(file.getName() + " is invalid,can't be create directory");
                }
            }
        }
    }

    public static String transforSysSpec(String path){
        //获取操作系统
        String regex=".*?window.*";
        String nPath="";
        if (ReUtil.isMatch(Pattern.compile(regex,Pattern.CASE_INSENSITIVE),System.getProperty("os.name"))){
            //如果是windows
            nPath=path.replaceAll("\\\\","/");
        }else{
            nPath=path;
        }
        return nPath;
    }

    /***
     * byte字节转换为字符串
     * @param fileBytes
     * @return
     */
    public static String byteToString(long fileBytes){
        StringBuffer byteStr=new StringBuffer();
        BigDecimal fullSize=new BigDecimal(fileBytes);
        //mb
        BigDecimal mbSize=new BigDecimal(MB_SIZE);
        float gbsize=fullSize.divide(new BigDecimal(GB_SIZE),2,BigDecimal.ROUND_HALF_UP).floatValue();
        if (gbsize>1){
            byteStr.append(gbsize).append("GB");
        }else{
            float dvsize=fullSize.divide(mbSize,2,BigDecimal.ROUND_HALF_UP).floatValue();
            if (dvsize>1){
                byteStr.append(dvsize).append("MB");
            }else{
                //kb显示
                BigDecimal kbSize=new BigDecimal(KB_SIZE);
                byteStr.append(fullSize.divide(kbSize,2,BigDecimal.ROUND_HALF_UP).floatValue()).append("KB");
            }
        }
        return byteStr.toString();
    }
}
