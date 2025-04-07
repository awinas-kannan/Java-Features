package com.awinas.learning.interviewprep.filecollection;

import java.util.HashSet;
import java.util.Set;

class CollectionData {
	int totalSize;
	Set<String> files;

	public CollectionData() {
		this.totalSize = 0;
		this.files = new HashSet<>();
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public Set<String> getFiles() {
		return files;
	}

	public void setFiles(Set<String> files) {
		this.files = files;
	}

}