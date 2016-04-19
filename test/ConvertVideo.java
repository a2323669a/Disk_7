import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

public class ConvertVideo {

	private static String inputPath = "";

	private static String outputPath = "";

	private static String ffmpegPath = "";

	public static void main(String args[]) throws IOException {

		getPath();

		if (!checkfile(inputPath)) {
			System.out.println(inputPath + " is not file");
			return;
		}
		if (process()) {
			System.out.println("ok");
		}
	}

	private static void getPath() { // �Ȼ�ȡ��ǰ��Ŀ·�����ڻ��Դ�ļ���Ŀ���ļ���ת������·��
		// File diretory = new File("");
		try {
			// String currPath = diretory.getAbsolutePath();
			String currPath = "";
			inputPath = currPath + "d:\\temp\\ffmpeg\\test.avi";
			outputPath = currPath + "d:\\temp\\ffmpeg\\";
			ffmpegPath = currPath + "d:\\temp\\ffmpeg\\";
			System.out.println(currPath);
		} catch (Exception e) {
			System.out.println("getPath����");
		}
	}

	private static boolean process() {
		int type = checkContentType();
		boolean status = false;
		if (type == 0) {
			System.out.println("ֱ��ת��flv��ʽ");
			status = processFLV(inputPath);// ֱ��ת��flv��ʽ
		} else if (type == 1) {
			String avifilepath = processAVI(type);
			if (avifilepath == null)
				return false;// û�еõ�avi��ʽ
			status = processFLV(avifilepath);// ��aviת��flv��ʽ
		}
		return status;
	}

	private static int checkContentType() {
		String type = inputPath.substring(inputPath.lastIndexOf(".") + 1,
				inputPath.length()).toLowerCase();
		// ffmpeg�ܽ����ĸ�ʽ����asx��asf��mpg��wmv��3gp��mp4��mov��avi��flv�ȣ�
		if (type.equals("avi")) {
			return 0;
		} else if (type.equals("mpg")) {
			return 0;
		} else if (type.equals("wmv")) {
			return 0;
		} else if (type.equals("3gp")) {
			return 0;
		} else if (type.equals("mov")) {
			return 0;
		} else if (type.equals("mp4")) {
			return 0;
		} else if (type.equals("asf")) {
			return 0;
		} else if (type.equals("asx")) {
			return 0;
		} else if (type.equals("flv")) {
			return 0;
		}
		// ��ffmpeg�޷��������ļ���ʽ(wmv9��rm��rmvb��),
		// �������ñ�Ĺ��ߣ�mencoder��ת��Ϊavi(ffmpeg�ܽ�����)��ʽ.
		else if (type.equals("wmv9")) {
			return 1;
		} else if (type.equals("rm")) {
			return 1;
		} else if (type.equals("rmvb")) {
			return 1;
		}
		return 9;
	}

	private static boolean checkfile(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return false;
		}
		return true;
	}

	// ��ffmpeg�޷��������ļ���ʽ(wmv9��rm��rmvb��), �������ñ�Ĺ��ߣ�mencoder��ת��Ϊavi(ffmpeg�ܽ�����)��ʽ.
	private static String processAVI(int type) {
		List<String> commend = new ArrayList<String>();
		commend.add(ffmpegPath + "mencoder");
		commend.add(inputPath);
		commend.add("-oac");
		commend.add("lavc");
		commend.add("-lavcopts");
		commend.add("acodec=mp3:abitrate=64");
		commend.add("-ovc");
		commend.add("xvid");
		commend.add("-xvidencopts");
		commend.add("bitrate=600");
		commend.add("-of");
		commend.add("avi");
		commend.add("-o");
		commend.add(outputPath + "a.avi");
		try {
			ProcessBuilder builder = new ProcessBuilder();
			Process process = builder.command(commend)
					.redirectErrorStream(true).start();
			new PrintStream(process.getInputStream());
			new PrintStream(process.getErrorStream());
			process.waitFor();
			return outputPath + "a.avi";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ffmpeg�ܽ����ĸ�ʽ����asx��asf��mpg��wmv��3gp��mp4��mov��avi��flv�ȣ�
	private static boolean processFLV(String oldfilepath) {

		if (!checkfile(inputPath)) {
			System.out.println(oldfilepath + " is not file");
			return false;
		}

		List<String> command = new ArrayList<String>();
		command.add(ffmpegPath + "ffmpeg");
		command.add("-i");
		command.add(oldfilepath);
		command.add("-ab");
		command.add("32");
		command.add("-ar");
		command.add("22050");
		command.add("-qscale");
		command.add("6");
		command.add("-r");
		command.add("25");
		command.add(outputPath + "t.mp4");

		try {

			Process videoProcess = new ProcessBuilder(command)
					.redirectErrorStream(true).start();

			new PrintStream(videoProcess.getErrorStream()).start();

			new PrintStream(videoProcess.getInputStream()).start();

			videoProcess.waitFor();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

class PrintStream extends Thread {
	java.io.InputStream __is = null;

	public PrintStream(java.io.InputStream is) {
		__is = is;
	}

	public void run() {
		try {
			while (this != null) {
				int _ch = __is.read();
				if (_ch != -1) {
					System.out.print((char) _ch);
				} else {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}