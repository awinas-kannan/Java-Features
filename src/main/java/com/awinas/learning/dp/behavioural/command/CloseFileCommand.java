package com.awinas.learning.dp.behavioural.command;

public class CloseFileCommand implements Command {

	private FileSystemReceiver fileSystem;

	public CloseFileCommand(FileSystemReceiver fs) {
		this.fileSystem = fs;
	}

	@Override
	public void execute() {
		this.fileSystem.closeFile();
	}

}