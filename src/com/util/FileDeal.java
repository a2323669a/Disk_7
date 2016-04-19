package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import org.springframework.stereotype.Component;

@Component
public class FileDeal {
	public void copy(File file, String copyPath) {
		int length = 2097152;
		FileInputStream in = null;
		FileOutputStream out = null;
		FileChannel inC = null;
		FileChannel outC = null;
		ByteBuffer b = null;
		System.out.println(copyPath);
		try {
			in = new FileInputStream(file);
			out = new FileOutputStream(copyPath);
			inC = in.getChannel();
			outC = out.getChannel();

			while (true) {
				if (inC.position() == inC.size()) {
					inC.close();
					outC.close();
					out.close();
					in.close();
					System.out.println(copyPath);
					return;
				}
				if ((inC.size() - inC.position()) < length) {
					length = (int) (inC.size() - inC.position());
				} else
					length = 2097152;
				b = ByteBuffer.allocateDirect(length);
				inC.read(b);
				b.flip();
				outC.write(b);
				outC.force(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
