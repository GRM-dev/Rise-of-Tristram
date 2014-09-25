package ca.grm.rot.items;

public enum ItemSizeModifiers {
	GIANT(
			0.5f,
			2,
			-2,
			2) ,
	LARGE(
			0.25f,
			1,
			-1,
			1) ,
	NORMAL(
			0,
			0,
			0,
			0) ,
	SMALL(
			-0.25f,
			0,
			1,
			-1);

	private int		str, agi, dmg;
	private float	scale;
	
	private ItemSizeModifiers(float scale, int str, int agi, int dmg) {
		this.scale = scale;
		this.str = str;
		this.agi = agi;
		this.dmg = dmg;
	}

	public int getAgi() {
		return this.agi;
	}
	
	public int getDmg() {
		return this.dmg;
	}
	
	public float getScale() {
		return this.scale;
	}
	
	public int getStr() {
		return this.str;
	}
}
