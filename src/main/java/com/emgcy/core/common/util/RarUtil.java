package com.emgcy.core.common.util;

import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

public class RarUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(RarUtil.class);
	
	/**
     * 
     * 对rar文件进行解压
     * <p>描述</p>
     * @date 2014-7-16 下午1:59:28
     * @version 
     * @param sourceRar
     * @param destDir
     * @throws Exception
     */
    public static void unRar(String sourceRar, String destDir) 
               throws Exception {
        Archive a = null;
        FileOutputStream fos = null;
        try {
            a = new Archive(new File(sourceRar));
            FileHeader fh = a.nextFileHeader();
            
            while (fh != null) {
                if (!fh.isDirectory()) {
                     // 1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
                    String compressFileName = fh.getFileNameW().trim();
                    String destFileName = "";
                    String destDirName = "";
                     // 非windows系统
                    if (File.separator.equals("/")) {
                      destFileName = destDir +"//"+ compressFileName.replaceAll("\\\\", "/");
                      destDirName = destFileName.substring(0, destFileName.lastIndexOf("/"));
                      // windows系统
                     } else {
                      destFileName = destDir+"//"+ compressFileName.replaceAll("/", "\\\\");
                      destDirName = destFileName.substring(0,destFileName.lastIndexOf("\\"));
                     }
                     // 2创建文件夹
                     File dir = new File(destDirName);
                     if (!dir.exists() || !dir.isDirectory()) {
                      dir.mkdirs();
                     }
                     // 3解压缩文件
                     fos = new FileOutputStream(new File(destFileName));
                     a.extractFile(fh, fos);
                     fos.close();
                     fos = null;
                }
                fh = a.nextFileHeader();
            }
            a.close();
            a = null;
        } catch (Exception e) {
        	logger.error("the methord unRar is fiail ", e);
            throw new Exception("Rar格式解压失败！");
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                    fos = null;
                }
                if (a != null) {
                    a.close();
                    a = null;
                }
            } catch (Exception e) {
            	logger.error("the methord unRar close is fiail ", e);
            }
            
            
        }
    }

}
