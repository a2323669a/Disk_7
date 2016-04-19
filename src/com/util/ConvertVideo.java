package com.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.stereotype.Component;

@Component
public class ConvertVideo {

	private String inputPath = "";

	private String outputPath = "";

	private String ffmpegPath = "";


	@Test
	public void test() {
		/*
		 * ConvertVideo cv = new ConvertVideo(); try {
		 * cv.convert("d:/temp/ffmpeg/test.avi","d:/temp/","d:/temp/ffmpeg/"); }
		 * catch (IOException e) { e.printStackTrace(); }
		 */

		System.out.println(System.getProperty("user.dir").replaceAll("\\\\",
				"/"));
	}

	public void convert(String inputPath, String outPutPath, String ffmpegPath)
			throws IOException {
		
		inputPath = inputPath.replaceAll("\\\\","/");
		outPutPath = outPutPath.replaceAll("\\\\","/");
		ffmpegPath = ffmpegPath.replaceAll("\\\\","/");

		getPath(inputPath, outPutPath, ffmpegPath);

		if (process()) {
			System.out.println("ok");
		}
	}

	private void getPath(String inputPath, String outPutPath, String ffmpegPath) { // 先获取当前项目路径，在获得源文件、目标文件、转换器的路径
		try {
			this.inputPath = inputPath;
			this.outputPath = outPutPath;
			this.ffmpegPath = ffmpegPath;
		} catch (Exception e) {
			System.out.println("getPath出错");
		}
	}

	private boolean process() {
		int type = checkContentType();
		boolean status = false;
		if (type == 0) {
			System.out.println("直接转成flv格式");
			status = processFLV(inputPath);// 直接转成flv格式
		} else if (type == 1) {
			String avifilepath = processAVI(type);
			if (avifilepath == null)
				return false;// 没有得到avi格式
			status = processFLV(avifilepath);// 将avi转成flv格式
		}
		return status;
	}

	private int checkContentType() {
		String type = inputPath.substring(inputPath.lastIndexOf(".") + 1,
				inputPath.length()).toLowerCase();

		// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
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
		// 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
		// 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
		else if (type.equals("wmv9")) {
			return 1;
		} else if (type.equals("rm")) {
			return 1;
		} else if (type.equals("rmvb")) {
			return 1;
		}
		return 9;
	}

	// 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
	private String processAVI(int type) {
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
		commend.add(outputPath +".avi");
		try {
			ProcessBuilder builder = new ProcessBuilder();
			Process process = builder.command(commend)
					.redirectErrorStream(true).start();
			new PrintStream(process.getInputStream());
			new PrintStream(process.getErrorStream());
			process.waitFor();
			return outputPath + ".avi";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	private boolean processFLV(String oldfilepath) {

		List<String> command = new ArrayList<String>();
		command.add(ffmpegPath + "ffmpeg");
		command.add("-i");
		command.add(oldfilepath);
		command.add("-ab");
		command.add("56");
		command.add("-ar");
		command.add("22050");
		command.add("-qscale");
		command.add("8");
		command.add("-r");
		command.add("25");
		command.add(outputPath + ".mp4");

		try {
			
			Process videoProcess = new ProcessBuilder(command)
			.redirectErrorStream(true).start();
			 
			new PrintStream(videoProcess.getErrorStream()).start();
			
			new PrintStream(videoProcess.getInputStream()).start();
			
//			videoProcess.waitFor();
			 
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getInputPath() {
		return inputPath;
	}

	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public String getFfmpegPath() {
		return ffmpegPath;
	}

	public void setFfmpegPath(String ffmpegPath) {
		this.ffmpegPath = ffmpegPath;
	}
}

class PrintStream extends Thread {
	java.io.InputStream _is = null;

	public PrintStream(java.io.InputStream is) {
		_is = is;
	}

	public void run() {
		try {
			while (this != null) {
				int _ch = _is.read();
				if (_ch != -1) {
					System.out.print((char) _ch);
				} else {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (_is != null) {
				try {
					_is.close();
					_is = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}