package io.geobit.common.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Block implements Serializable {
	private static final long serialVersionUID = 2L;
	private Integer height;
	private Long    time;
	private String  hash;

	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	@Override
	public String toString() {
		return "Block [height=" + height + ", time=" + time + ", hash=" + hash
				+ "]";
	}

	
	

	

}
