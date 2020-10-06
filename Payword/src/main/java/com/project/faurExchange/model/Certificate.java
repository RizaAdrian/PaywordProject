package com.project.faurExchange.model;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class Certificate {

	private Map<BytesSection, byte[]> certificateMap;

	private byte[] signed;

	private int size;

	public Certificate() {
		this.certificateMap = new EnumMap<> (BytesSection.class);
	}

	public byte[] getSection(BytesSection bytesSection) {
		return certificateMap.get(bytesSection);
	}

	public void addSection(BytesSection section, byte[] bytes) {
		this.certificateMap.put(section, bytes);
		this.size += bytes.length;
	}

	public byte[] unsigned() {
		byte[] value = new byte[size];
		int i = 0;
		for (BytesSection sectionKey : certificateMap.keySet()) {
			byte[] sectionBytes = certificateMap.get(sectionKey);
			for (byte sectionByte : sectionBytes) {
				value[i++] = sectionByte;
			}
		}
		return value;
	}

	public int getSize() {
		return size;
	}

	public byte[] signed() {
		return signed;
	}

	public void setSigned(byte[] sign) {
		this.signed = sign;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (BytesSection sectionKey : certificateMap.keySet()) {
			sb.append(sectionKey.toString() + " [" + Arrays.toString(certificateMap.get(sectionKey)) + "] \n");
		}
		return sb.toString();
	}

}