package com.zhouww.first.springboot.CommonUtils;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class ZipDownLoadUtils {
    /**
     * 直接下载zip文件 多个文件通过流处理
     * @param fileName1
     * @param bytes1
     * @param filename2
     * @param bytes2
     * @param response
     */
    public static  void zipByteInfo(String fileName1, byte[] bytes1, String filename2, byte[] bytes2, HttpServletResponse response) {
        try {
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition", "form-data; name=attachment;filename=" + java.net.URLEncoder.encode("XXXX.zip", "UTF-8"));
            OutputStream outputStream = response.getOutputStream();
            ZipArchiveOutputStream zous = new ZipArchiveOutputStream(outputStream);
            zous.setUseZip64(Zip64Mode.Always);
            // 第一个文件的读取封装
            if(StringUtils.isNotBlank(fileName1) && (bytes1!=null&&bytes1.length>0)){
                ArchiveEntry entry = new ZipArchiveEntry(fileName1);
                zous.putArchiveEntry(entry);
                zous.write(bytes1);
            }
            // 第二个文件的读取封装 如果更多可以进行 创建多个 ArchiveEntry对象
            if(StringUtils.isNotBlank(filename2) && (bytes2!=null&&bytes2.length>0)) {
                ArchiveEntry entry1 = new ZipArchiveEntry(filename2);
                zous.putArchiveEntry(entry1);
                zous.write(bytes2);
            }
            zous.closeArchiveEntry();
            outputStream.flush();
            zous.flush();
            zous.finish();
            zous.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // return bt;
    }

}
