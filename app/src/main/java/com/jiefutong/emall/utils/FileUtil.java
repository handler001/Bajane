package com.jiefutong.emall.utils;

/**
 * 文件处理工具类
 *
 * @author menglingfang
 * @since 2014-7-22
 */

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 文件操作工具类
 *
 * @author lizhiyang
 */
public class FileUtil {

    // 拷贝文件夹
    public static boolean copyFolder(String srcDirPath, String desDirPath) {
        boolean reb = false;
        try {

            File in = new File(srcDirPath);
            File out = new File(desDirPath);
            if (!in.exists()) {
                return false;
            }
            /*
             * else{ LogUtil.i("源文件路径"+in.getAbsolutePath());
             * LogUtil.i("目标路径"+out.getAbsolutePath()); }
             */
            if (!out.exists()) {
                out.mkdirs();
            }
            File[] file = in.listFiles();
            FileInputStream fis = null;
            FileOutputStream fos = null;
            for (int i = 0; i < file.length; i++) {
                if (file[i].isFile())
                    try {
                        fis = new FileInputStream(file[i]);
                        fos = new FileOutputStream(new File(desDirPath + "\\" + file[i].getName()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                int c;
                byte[] b = new byte[1024 * 5];

                try {
                    while ((c = fis.read(b)) != -1) {
                        fos.write(b, 0, c);
                    }

                    fis.close();
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            reb = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return reb;
    }

    // 重命名文件
    public static boolean renameFile(String oldFullFilePath, String newFullFilePath) {
        boolean reb = false;
        try {
            if (!oldFullFilePath.equals(newFullFilePath)) {
                File oldFile = new File(oldFullFilePath);
                File newFile = new File(newFullFilePath);
                if (newFile.exists()) {
                } else {
                    oldFile.renameTo(newFile);
                    reb = true;
                }

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return reb;
    }

    public static void copy(File f1, File f2) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f1));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f2));
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = bis.read(buff, 0, 1024)) != -1) {
                bos.write(buff, 0, len);
            }
            bis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 通过 sPath.matches(matches) 方法的返回值判断是否正确
    // sPath 为路径字符串

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String sPath) {
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) { // 不存在返回 false
            return false;
        } else {
            // 判断是否为文件
            if (file.isFile()) { // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else { // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            flag = file.delete();
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        boolean reb = false;
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            reb = true;
        }
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                // 删除子文件
                if (files[i].isFile()) {
                    reb = deleteFile(files[i].getAbsolutePath());
                    if (!reb)
                        break;
                } // 删除子目录
                else {
                    reb = deleteDirectory(files[i].getAbsolutePath());
                    if (!reb)
                        break;
                }
            }
        }
        if (!reb)
            return false;
        // 删除当前目录
        if (dirFile.delete()) {
            reb = true;
        }
        return reb;
    }

    /**
     * Check if external storage is built-in or removable.
     *
     * @return True if external storage is removable (like an SD card), false
     * otherwise.
     */
    @TargetApi(9)
    public static boolean isExternalStorageRemovable() {
        if (VersionUtils.hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    /**
     * 18 * 检验SDcard状态
     * <p/>
     * 19 * @return boolean
     * <p/>
     * 20
     */

    public static boolean checkSDCard()

    {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    @SuppressLint("NewApi")
    public static String getDriverFileDir(Context context, String redir) {// 获取存放文件的磁盘路径
        String filepath = "";
        try {
            filepath = "";
            if (FileUtil.checkSDCard()) {
                filepath = context.getExternalFilesDir("").getAbsolutePath();
            } else {
                filepath = context.getCacheDir().getAbsolutePath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filepath + File.separator + redir;

    }

    /**
     * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
     *
     * @param context
     */
    public static void write(Context context, String fileName, String content) {
        if (content == null)
            content = "";

        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(content.getBytes());

            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文本文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String read(Context context, String fileName) {
        try {
            FileInputStream in = context.openFileInput(fileName);
            return readInStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String readInStream(InputStream inStream) {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            int length = -1;
            while ((length = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, length);
            }

            outStream.close();
            inStream.close();
            return outStream.toString();
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * 创建文件（牧人）
     *
     * @param folderPath
     * @param fileName
     * @return
     */
    public static File createFile(String folderPath, String fileName) {
        if (createDir(folderPath)) {
            // boolean mkdirs = destDir.mkdirs();
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + folderPath, fileName);
            try {
                boolean newFile = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file;
        }
        return null;
    }

    public static File createFile() {
        if (createDir(Constant.PIC_PATH)) {
            // boolean mkdirs = destDir.mkdirs();
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.PIC_PATH, System.currentTimeMillis() + ".jpg");
            try {
                boolean newFile = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file;
        }
        return null;
    }

    /**
     * 向手机写图片
     *
     * @param buffer
     * @param folder
     * @param fileName
     * @return
     */
    public static boolean writeFile(byte[] buffer, String folder, String fileName) {
        boolean writeSucc = false;

        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

        String folderPath = "";
        if (sdCardExist) {
            folderPath = Environment.getExternalStorageDirectory() + File.separator + folder + File.separator;
        } else {
            writeSucc = false;
        }

        File fileDir = new File(folderPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File file = new File(folderPath + fileName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(buffer);
            writeSucc = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return writeSucc;
    }

    /**
     * 根据文件绝对路径获取文件名
     *
     * @param filePath 文件绝对路径
     * @return 文件名
     */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return "";
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    /**
     * 根据文件的绝对路径获取文件名但不包含扩展名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileNameNoFormat(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        int point = filePath.lastIndexOf('.');
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1, point);
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件全名
     * @return 文件扩展名
     */
    public static String getFileFormat(String fileName) {
        if (TextUtils.isEmpty(fileName))
            return "";

        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);
    }

    /**
     * 获取文件大小
     *
     * @param filePath 文件绝对路径
     * @return 文件大小
     */
    public static String getFileSize(String filePath) {
        long size = 0;

        File file = new File(filePath);
        if (file != null && file.exists()) {
            size = file.length();
        }
        return getSize(size);
    }

    /**
     * 大小转换
     *
     * @param size 文件大小bt
     * @return 转换后的大小M, K
     */
    public static String getSize(long size) {
        if (size <= 0)
            return "0";
        java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
        float temp = (float) size / 1024;
        if (temp >= 1024) {
            return df.format(temp / 1024) + "M";
        } else {
            return df.format(temp) + "K";
        }
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取目录文件大小
     *
     * @param dir 要查找的目录文件
     * @return 目录大小
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 获取目录文件个数
     *
     * @param dir 目录文件
     * @return 目录文件个数
     */
    public long getFileList(File dir) {
        long count = 0;
        File[] files = dir.listFiles();
        count = files.length;
        for (File file : files) {
            if (file.isDirectory()) {
                count = count + getFileList(file);// 递归
                count--;
            }
        }
        return count;
    }

    public static byte[] toBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            out.write(ch);
        }
        byte buffer[] = out.toByteArray();
        out.close();
        return buffer;
    }

    /**
     * 检查文件是否存在
     *
     * @param name 文件名
     * @return 文件是否存在
     */
    public static boolean checkFileExists(String name) {
        boolean status;
        if (!TextUtils.isEmpty(name)) {
            File path = Environment.getExternalStorageDirectory();
            File newPath = new File(path.toString() + name);
            status = newPath.exists();
        } else {
            status = false;
        }
        return status;
    }

    /**
     * 检查路径是否存在
     *
     * @param path
     * @return 路径是否存在
     */
    public static boolean checkFilePathExists(String path) {
        return new File(path).exists();
    }

    /**
     * 计算SD卡的剩余空间
     *
     * @return 返回-1，说明没有安装sd卡
     */
    public static long getFreeDiskSpace() {
        String status = Environment.getExternalStorageState();
        long freeSpace = 0;
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                freeSpace = availableBlocks * blockSize / 1024;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return (freeSpace);
    }

    /**
     * 新建目录（默认创建在sd卡根目录）
     *
     * @param directoryName 目录名
     * @return 创建的目录
     */
    public static boolean createDir(String directoryName) {
        boolean mkdir = false;
        if (!TextUtils.isEmpty(directoryName)) {
            File path = Environment.getExternalStorageDirectory();
            File newPath = new File(path.toString() + directoryName);
            mkdir = newPath.exists() ? true : newPath.mkdirs();
        }
        return mkdir;
    }

    /**
     * 重命名
     *
     * @param oldName
     * @param newName
     * @return
     */
    public static boolean reNamePath(String oldName, String newName) {
        File f = new File(oldName);
        return f.renameTo(new File(newName));
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static boolean deleteFileWithPath(String filePath) {
        SecurityManager checker = new SecurityManager();
        File f = new File(filePath);
        checker.checkDelete(filePath);
        if (f.isFile()) {
            f.delete();
            return true;
        }
        return false;
    }

    /**
     * @return 获取SD卡的根目录
     */
    public static String getSDRoot() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 获取手机外置SD卡的根目录
     *
     * @return SD卡的根目录
     */
    public static String getExternalSDRoot() {

        Map<String, String> evn = System.getenv();

        return evn.get("SECONDARY_STORAGE");
    }

    /**
     * 列出root目录下所有子目录
     *
     * @param root
     * @return 绝对路径
     */
    public static List<String> listPath(String root) {
        List<String> allDir = new ArrayList<String>();
        SecurityManager checker = new SecurityManager();
        File path = new File(root);
        checker.checkRead(root);
        // 过滤掉以.开始的文件夹
        if (path.isDirectory()) {
            for (File f : path.listFiles()) {
                if (f.isDirectory() && !f.getName().startsWith("")) {
                    allDir.add(f.getAbsolutePath());
                }
            }
        }
        return allDir;
    }

    /**
     * 列出目录下的所有文件（不包括文件夹）
     *
     * @param root
     * @return
     */
    public static List<File> listFile(String root) {
        if (root == null && root.isEmpty())
            return null;

        List<File> allDir = new ArrayList<File>();
        SecurityManager checker = new SecurityManager();
        checker.checkRead(root);
        LinkedList<File> list = new LinkedList<File>();
        File dir = new File(root);
        if (dir.isDirectory()) {
            File file[] = dir.listFiles();
            for (int i = 0; i < file.length; i++) {
                if (file[i].isDirectory())
                    list.add(file[i]);
                else
                    allDir.add(file[i]);
            }
            File tmp;
            while (!list.isEmpty()) {
                tmp = (File) list.removeFirst();
                if (tmp.isDirectory()) {
                    file = tmp.listFiles();
                    if (file == null)
                        continue;
                    for (int i = 0; i < file.length; i++) {
                        if (file[i].isDirectory())
                            list.add(file[i]);
                        else
                            allDir.add(file[i]);
                    }
                } else {
                }
            }
        }
        return allDir;
    }

    public enum PathStatus {
        SUCCESS, EXITS, ERROR
    }

    /**
     * 创建目录
     *
     * @param newPath 文件的文件绝对路径
     */
    public static PathStatus createPath(String newPath) {
        File path = new File(newPath);
        if (path.exists()) {
            return PathStatus.EXITS;
        }
        if (path.mkdir()) {
            return PathStatus.SUCCESS;
        } else {
            return PathStatus.ERROR;
        }
    }

    /**
     * 截取文件路径名
     *
     * @param absolutePath 文件绝对路径
     * @return 文件名
     */
    public static String getPathName(String absolutePath) {
        int start = absolutePath.lastIndexOf(File.separator) + 1;
        int end = absolutePath.length();
        return absolutePath.substring(start, end);
    }

    /**
     * 是否为空文件
     *
     * @return
     */
    public static boolean isEmptyFile(File file) {
        return file == null || file.length() == 0;
    }

    /**
     * 根据网络url获取文件名(url中须包含.xxx文件名)
     *
     * @param url url路径
     * @return 重新编码后的url
     */
    public static String getFileNameByUrl(String url) {
        String ret = "";

        String lastfilename = url.substring(url.lastIndexOf("/") + 1);
        int tmpIndex = lastfilename.indexOf("?");
        if (tmpIndex == -1) {
            ret = lastfilename;
        } else {
            ret = lastfilename.substring(0, tmpIndex);
        }

        ret = decodeUrl(ret);

        return ret;
    }

    /**
     * 递归创建目录
     *
     * @param file
     * @return
     */
    public static boolean mkDir(File file) {
        boolean ret = true;
        if (file.getParentFile().exists()) {
            ret = file.mkdir();
        } else {
            mkDir(file.getParentFile());
            ret = file.mkdir();
        }
        return ret;
    }

    /**
     * 解码网络Url字符串（默认为GBk）
     *
     * @param src
     * @return 编码后的url
     */
    public static String decodeUrl(String src, String charset) {
        String ret = src;

        try {
            ret = java.net.URLDecoder.decode(ret, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 解码网络Url字符串（默认为GBk）
     *
     * @return 编码后的url
     */
    public static String decodeUrl(String url) {
        return decodeUrl(url, "UTF-8");

    }
}
