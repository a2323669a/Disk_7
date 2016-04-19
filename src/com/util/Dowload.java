package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Dowload {

	public InputStream dowload(String aria2Path, String path, String link) throws IOException {

		List<String> command = new ArrayList<String>();
		String aria2c = aria2Path;
		command.add(aria2c + "aria2c");
		command.add("-d");
		command.add(path);
		command.add("--seed-time=0");
		command.add("-u");
		command.add("0");
		command.add("--bt-stop-timeout=6000");//¡˘ Æ∑÷÷”
		command.add("--max-overall-upload-limit=0");
		command.add("--bt-request-peer-speed-limit=200K");
		command.add(link);

		ProcessBuilder builder = new ProcessBuilder(command);
		builder.command(command);
		Process p = builder.redirectErrorStream(true).start();
		return p.getInputStream();

	}
}
