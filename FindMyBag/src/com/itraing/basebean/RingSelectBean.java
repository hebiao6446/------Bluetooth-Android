package com.itraing.basebean;

public class RingSelectBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -490591369712143151L;
	private boolean flag;
	private int enname;
	private int raw;
	private String resname;
	
	
	
	
	public int getRaw() {
		return raw;
	}
	public void setRaw(int raw) {
		this.raw = raw;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public int getEnname() {
		return enname;
	}
	public void setEnname(int enname) {
		this.enname = enname;
	}
	public String getResname() {
		return resname;
	}
	public void setResname(String resname) {
		this.resname = resname;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + enname;
		result = prime * result + (flag ? 1231 : 1237);
		result = prime * result + raw;
		result = prime * result + ((resname == null) ? 0 : resname.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RingSelectBean other = (RingSelectBean) obj;
		if (enname != other.enname)
			return false;
		if (flag != other.flag)
			return false;
		if (raw != other.raw)
			return false;
		if (resname == null) {
			if (other.resname != null)
				return false;
		} else if (!resname.equals(other.resname))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RingSelectBean [flag=" + flag + ", enname=" + enname + ", raw="
				+ raw + ", resname=" + resname + "]";
	}
	
}
