package com.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

public class Zip {

	public static final String ZIP_FILENAME = "C:\\XJPDA.zip";// ��Ҫ��ѹ�����ļ���
	public static final String ZIP_DIR = "C:\\XJPDA\\";// ��Ҫѹ�����ļ���
	public static final String UN_ZIP_DIR = "C:\\";// Ҫ��ѹ���ļ�Ŀ¼
	public static final int BUFFER = 1024;// �����С

	@Test
	public void main() {
		try {
			upZipFile("d:/temp/ffmpeg/test.zip", "d:\\temp\\ffmpeg\\test\\");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * zipѹ������. ѹ��baseDir(�ļ���Ŀ¼)�������ļ���������Ŀ¼
	 * 
	 * @throws Exception
	 */
	public static void zipFile(String baseDir, String fileName)
			throws Exception {
		List fileList = getSubFiles(new File(baseDir));
		ZipOutputStream zos = new ZipOutputStream(
				new FileOutputStream(fileName));
		ZipEntry ze = null;
		byte[] buf = new byte[BUFFER];
		int readLen = 0;
		for (int i = 0; i < fileList.size(); i++) {
			File f = (File) fileList.get(i);
			ze = new ZipEntry(getAbsFileName(baseDir, f));
			ze.setSize(f.length());
			ze.setTime(f.lastModified());
			zos.putNextEntry(ze);
			InputStream is = new BufferedInputStream(new FileInputStream(f));
			while ((readLen = is.read(buf, 0, BUFFER)) != -1) {
				zos.write(buf, 0, readLen);
			}
			is.close();
		}
		zos.close();
	}

	/**
	 * ������Ŀ¼��������һ���ļ��������·��������zip�ļ��е�·��.
	 * 
	 * @param baseDir
	 *            java.lang.String ��Ŀ¼
	 * @param realFileName
	 *            java.io.File ʵ�ʵ��ļ���
	 * @return ����ļ���
	 */
	private static String getAbsFileName(String baseDir, File realFileName) {
		File real = realFileName;
		File base = new File(baseDir);
		String ret = real.getName();
		while (true) {
			real = real.getParentFile();
			if (real == null)
				break;
			if (real.equals(base))
				break;
			else
				ret = real.getName() + "/" + ret;
		}
		return ret;
	}

	/**
	 * ȡ��ָ��Ŀ¼�µ������ļ��б�������Ŀ¼.
	 * 
	 * @param baseDir
	 *            File ָ����Ŀ¼
	 * @return ����java.io.File��List
	 */
	private static List getSubFiles(File baseDir) {
		List ret = new ArrayList();
		File[] tmp = baseDir.listFiles();
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].isFile())
				ret.add(tmp[i]);
			if (tmp[i].isDirectory())
				ret.addAll(getSubFiles(tmp[i]));
		}
		return ret;
	}

	/**
	 * ��ѹ������. ��ZIP_FILENAME�ļ���ѹ��ZIP_DIRĿ¼��.
	 * 
	 * @throws Exception
	 */
	public static void upZipFile(String zipFilename, String outputDirectory)
			throws Exception {
		File outFile = new File(outputDirectory);
		if (!outFile.exists()) {
			outFile.mkdirs();
		}
		ZipFile zipFile = new ZipFile(zipFilename);
		Enumeration en = zipFile.entries();
		ZipEntry zipEntry = null;
		while (en.hasMoreElements()) {
			zipEntry = (ZipEntry) en.nextElement();
			System.out.println(zipEntry.getName());
			if (zipEntry.isDirectory()) {
				// mkdir directory
				String dirName = zipEntry.getName();
				System.out.println("=dirName is:=" + dirName + "=end=");
				dirName = dirName.substring(0, dirName.length() - 1);
				File f = new File(outFile.getPath() + File.separator + dirName);
				f.mkdirs();
			} else {
				// unzip file
				String strFilePath = outFile.getPath() + File.separator
						+ zipEntry.getName();
				File f = new File(strFilePath);
				// the codes remedified by can_do on 2010-07-02 =begin=
				// ///begin/////
				// �ж��ļ������ڵĻ����ʹ������ļ������ļ��е�Ŀ¼
				if (!f.exists()) {
					String[] arrFolderName = zipEntry.getName().split("/");
					String strRealFolder = "";
					for (int i = 0; i < (arrFolderName.length - 1); i++) {
						strRealFolder += arrFolderName[i] + File.separator;
					}
					strRealFolder = outFile.getPath() + File.separator
							+ strRealFolder;
					File tempDir = new File(strRealFolder);
					// �˴�ʹ��.mkdirs()��������������.mkdir()
					tempDir.mkdirs();
				}
				// ////end///
				// the codes remedified by can_do on 2010-07-02 =end=
				f.createNewFile();
				InputStream in = zipFile.getInputStream(zipEntry);
				FileOutputStream out = new FileOutputStream(f);
				try {
					int c;
					byte[] by = new byte[1024];
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.flush();
				} catch (IOException e) {
					throw e;
				} finally {
					out.close();
					in.close();
				}
			}
		}
	}


}
