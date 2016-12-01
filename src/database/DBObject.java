package database;

public abstract class DBObject {
	
	private boolean isModified = true, toBeDeleted = false;

	public boolean isModified() {
		return isModified;
	}

	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}

	public boolean isToBeDeleted() {
		return toBeDeleted;
	}

	public void setToBeDeleted(boolean toBeDeleted) {
		this.toBeDeleted = toBeDeleted;
	}

	abstract public void sqlSave();
	abstract public void sqlDelete();
}
