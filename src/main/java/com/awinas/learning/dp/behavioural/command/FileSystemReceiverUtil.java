package com.awinas.learning.dp.behavioural.command;


//Factory pattern to return appropriate type based on the input.
public class FileSystemReceiverUtil {

	public static FileSystemReceiver getUnderlyingFileSystem() {
		String osName = System.getProperty("os.name");
		System.out.println("Underlying OS is:" + osName);
		if (osName.contains("Windows")) {
			return new WindowsFileSystemReceiver();
		} else {
			return new UnixFileSystemReceiver();
		}
	}

}
