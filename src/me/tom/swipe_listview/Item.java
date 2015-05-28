package me.tom.swipe_listview;

public class Item{
	private String itemName = "none";
	private int itemIndex = 0;
	private int itemId = -1;
	private int itemColorCode = 0;
	private int iconId = 0;
	public Item(String itemName, int itemIndex, int itemId,
			int itemColorCode,int iconId) {
		super();
		this.itemName = itemName;
		this.itemIndex = itemIndex;
		this.itemId = itemId;
		this.itemColorCode = itemColorCode;
		this.setIconId(iconId);
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getItemIndex() {
		return itemIndex;
	}
	public void setItemIndex(int itemIndex) {
		this.itemIndex = itemIndex;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getItemColorCode() {
		return itemColorCode;
	}
	public void setItemColorCode(int itemColorCode) {
		this.itemColorCode = itemColorCode;
	}
	public int getIconId() {
		return iconId;
	}
	public void setIconId(int iconId) {
		this.iconId = iconId;
	}
	
	
	
}