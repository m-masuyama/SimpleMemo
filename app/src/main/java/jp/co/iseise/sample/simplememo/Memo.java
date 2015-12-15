package jp.co.iseise.sample.simplememo;

/**
 * メモクラス.
 */
public class Memo {
	private long id;
	private String memo;
	private long updated;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public long getUpdated() {
		return updated;
	}

	public void setUpdated(long updated) {
		this.updated = updated;
	}
}
