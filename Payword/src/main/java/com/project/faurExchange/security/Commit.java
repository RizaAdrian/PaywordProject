package com.project.faurExchange.security;

import java.util.HashMap;
import java.util.Map;

import com.project.faurExchange.model.BytesSection;

public class Commit {

	private Map<BytesSection, byte[]> commitMap;

	private byte[] signed;

	private int size;

    public Commit() {
        this.commitMap = new HashMap<>();
    }

    public byte[] getSection(BytesSection section) {
    	return commitMap.get(section);
    }

    public void addSection(BytesSection section, byte[] bytes) {
		this.commitMap.put(section, bytes);
		this.size += bytes.length;
	}

    public byte[] unsigned() {
		byte[] value = new byte[size];
		int i = 0;
		for (BytesSection sectionKey : commitMap.keySet()) {
			byte[] sectionBytes = commitMap.get(sectionKey);
			for (byte sectionByte : sectionBytes) {
				value[i++] = sectionByte;
			}
		}
		return value;
	}

	public byte[] signed() {
		return signed;
	}

	public void setSigned(byte[] signed) {
		this.signed = signed;
	}

	public Map<BytesSection, byte[]> getCommitMap() {
		return commitMap;
	}

	public int getSize() {
		return size;
	}

	@Override
	public String toString() {
		String reprezentation;
		reprezentation="Date: "+getSection(BytesSection.CURRENT_DATE)+"\n";
		reprezentation+="PAY: "+getSection(BytesSection.PAY_ONE)+"\n";
		return reprezentation;
	}
}
