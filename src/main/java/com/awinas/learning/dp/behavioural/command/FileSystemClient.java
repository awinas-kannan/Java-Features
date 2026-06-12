package com.awinas.learning.dp.behavioural.command;

public class FileSystemClient {

	public static void main(String[] args) {
		// Creating the receiver object || Factory pattern to return appropriate type based on the input.
		FileSystemReceiver fs = FileSystemReceiverUtil.getUnderlyingFileSystem();

		// creating command and associating with receiver
		OpenFileCommand openFileCommand = new OpenFileCommand(fs);

		// Creating invoker and associating with Command
		FileInvoker file = new FileInvoker(openFileCommand);

		// perform action on invoker object
		file.execute();

		WriteFileCommand writeFileCommand = new WriteFileCommand(fs);
		file = new FileInvoker(writeFileCommand);
		file.execute();

		CloseFileCommand closeFileCommand = new CloseFileCommand(fs);
		file = new FileInvoker(closeFileCommand);
		file.execute();
	}

}