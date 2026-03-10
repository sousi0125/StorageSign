package wacky.storagesign;

import org.bukkit.Material;
import org.bukkit.potion.PotionType;
import org.bukkit.util.NumberConversions;

public class PotionInfo {

	protected Material mat;
	protected PotionType pot;
	protected short damage = 0;

	public PotionInfo(Material mat, String[] str){
		this.mat = mat;
		if(str.length == 2){//ダメージ値
			short old = NumberConversions.toShort(str[1]);
			if(old >= 16384) this.mat = Material.SPLASH_POTION;
			switch(old % 16){
			case 1:
				pot = PotionType.REGENERATION;
			    break;
			case 2:
				pot = PotionType.SWIFTNESS;
			    break;
			case 3:
				pot = PotionType.FIRE_RESISTANCE;
			    break;
			case 4:
				pot = PotionType.POISON;
			    break;
			case 5:
				pot = PotionType.HEALING;
			    break;
			case 6:
				pot = PotionType.NIGHT_VISION;
			    break;
			case 8:
				pot = PotionType.WEAKNESS;
			    break;
			case 9:
				pot = PotionType.STRENGTH;
			    break;
			case 10:
				pot = PotionType.SLOWNESS;
			    break;
			case 11:
				pot = PotionType.LEAPING;
			    break;
			case 12:
				pot = PotionType.HARMING;
			    break;
			case 13:
				pot = PotionType.WATER_BREATHING;
				break;
			case 14:
				pot = PotionType.INVISIBILITY;
				break;

			default:
				if(old == 16) pot = PotionType.AWKWARD;
				else if(old == 32) pot = PotionType.THICK;
				else if(old == 64 || old == 8192 || old == 16384) pot = PotionType.MUNDANE;
				else pot = PotionType.WATER;
			}

			if(old % 8192 > 64 && pot.isExtendable()) damage = 1;//延長
			else if(old % 64 > 32 && pot.isUpgradeable()) damage = 2;//強化

		}else if(str.length == 1) pot = PotionType.WATER;

		else{//タイプとレベル
			pot = getType(str[1]);
			damage = NumberConversions.toShort(str[2]);
		}
	}

	private PotionType getType(String substring) {
		if(substring.equals("BREAT")) return PotionType.WATER_BREATHING;//例外
		else if(substring.equals("HEAL")) return PotionType.HEALING;
		else if(substring.equals("DAMAG")) return PotionType.HARMING;
        else{ //後ろ切れてるかも
            for(PotionType p : PotionType.values()) {
                if(p.toString().startsWith(substring)) return p;
            }
        }
		return null;
	}

	public static String getShortType(PotionType pot){
		if(pot == PotionType.WATER_BREATHING) return "BREAT";
		else if(pot == PotionType.HEALING) return "HEAL";
		else if(pot == PotionType.HARMING) return "DAMAG";
		else if(pot.toString().length() <= 5) return pot.toString();
		return pot.toString().substring(0, 5);
	}


	public short getDamage() {
		return damage;
	}

	public Material getMaterial() {
		return mat;
	}

	public PotionType getPotionType() {
		return pot;
	}

}
