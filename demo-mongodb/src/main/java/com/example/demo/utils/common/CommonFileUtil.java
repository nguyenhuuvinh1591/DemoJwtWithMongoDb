package com.example.demo.utils.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.exception.SystemException;

public class CommonFileUtil extends FilenameUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonFileUtil.class);

    public static byte[] readFileToByteArray(File file) throws IOException {
        return FileUtils.readFileToByteArray(file);
    }

    public static void moveFile(String fileName, String folderSource, String newFileName, String folderTarget)
            throws IOException {
        File source = new File(folderSource + fileName);
        File target = new File(folderTarget + newFileName);
        CommonFileUtil.setPermission(target);
        try {
            FileUtils.copyFile(source, target);
            if (!source.delete()) {
                logger.error("source not delete");
            }
        } catch (IOException e) {
            StringBuilder msgError = new StringBuilder("IOException:");
            msgError.append("Can not move file ");
            msgError.append(fileName);
            msgError.append(" from folderSource ");
            msgError.append(folderSource);
            msgError.append(" to folderTarget ");
            msgError.append(folderTarget);
            msgError.append(":");
            throw new SystemException(msgError.toString() + e.toString());
        }
    }

    public static void setPermission(File file) throws IOException {
        if (!file.setReadable(true, false)) {
            logger.error("file not set Readable");
        }
        if (!file.setWritable(true, false)) {
            logger.error("file not set Writable");
        }
        if (!file.setExecutable(true, false)) {
            logger.error("file not set Executable");
        }
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        return IOUtils.toByteArray(inputStream);
    }

    public static String getContentType(String fileNameFull) {
        String contentType = "application/octet-stream";

        String extensionFile = getExtension(fileNameFull);

        if (CommonStringUtil.isNotEmpty(extensionFile)) {
            if (extensionFile.equals("pdf"))
                contentType = "application/pdf";
            else if (extensionFile.equals("txt"))
                contentType = "text/plain";
            else if (extensionFile.equals("exe"))
                contentType = "application/octet-stream";
            else if (extensionFile.equals("zip"))
                contentType = "application/zip";
            else if (extensionFile.equals("doc"))
                contentType = "application/msword";
            else if (extensionFile.equals("xls"))
                contentType = "application/vnd.ms-excel";
            else if (extensionFile.equals("xlsx"))
                contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            else if (extensionFile.equals("ppt"))
                contentType = "application/vnd.ms-powerpoint";
            else if (extensionFile.equals("gif"))
                contentType = "image/gif";
            else if (extensionFile.equals("png"))
                contentType = "image/png";
            else if (extensionFile.equals("jpeg"))
                contentType = "image/jpeg";
            else if (extensionFile.equals("jpg"))
                contentType = "image/jpeg";
            else if (extensionFile.equals("mp3"))
                contentType = "audio/mpeg";
            else if (extensionFile.equals("wav"))
                contentType = "audio/x-wav";
            else if (extensionFile.equals("mpeg"))
                contentType = "video/mpeg";
            else if (extensionFile.equals("mpg"))
                contentType = "video/mpeg";
            else if (extensionFile.equals("mpe"))
                contentType = "video/mpeg";
            else if (extensionFile.equals("mov"))
                contentType = "video/quicktime";
            else if (extensionFile.equals("avi"))
                contentType = "video/x-msvideo";
            else if (extensionFile.equals("flv"))
                contentType = "video/flv";
        }

        return contentType;
    }

    public static String getContentTypeByBase64(String contentBase64) {
        byte[] content = CommonBase64Util.decodeBase64(contentBase64);
        String mimeType = "application/octet-stream";
        try (InputStream is = new BufferedInputStream(new ByteArrayInputStream(content))) {
            mimeType = URLConnection.guessContentTypeFromStream(is);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return mimeType;
    }
}
