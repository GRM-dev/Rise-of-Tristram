package ca.grm.rot.libs;

public class RotClass {
	
	public int strStat,dexStat,intStat,vitStat,agiStat;
	public float baseHp,baseMana,baseStam,hpPerVit,manaPerIntStat,stamPerVitStat;
	public String className;
	
	public RotClass(String className,int strStat,int dexStat,int intStat,int vitStat,
			int agiStat,float baseHp,float baseMana,float baseStam,
			float hpPerVit,float manaPerIntStat,float stamPerVitStat)
	{
		this.strStat = strStat;
		this.dexStat = dexStat;
		this.intStat = intStat;
		this.vitStat = vitStat;
		this.agiStat = agiStat;
		this.baseHp = baseHp;
		this.baseMana = baseMana;
		this.baseStam = baseStam;
		this.hpPerVit = hpPerVit;
		this.manaPerIntStat = manaPerIntStat;
		this.stamPerVitStat = stamPerVitStat;
		this.className = className;
	}

}
