package wacky.storagesign;

import org.bukkit.Material;
import org.bukkit.potion.PotionType;
//import org.bukkit.util.NumberConversions;

public class PotionInfo {

	protected Material mat;
	protected PotionType pot;

	public PotionInfo(Material mat, String[] str){
		this.mat = mat;
		if(str.length == 1) pot = PotionType.WATER;

		else{//タイプ
			pot = getType(str[1]);
		}
	}

	private PotionType getType(String str) {

		if(str.endsWith("_L")){
			String base = str.substring(0, str.length()-2);

			if(REVERSE_NAMES.containsKey(base)){
				base = REVERSE_NAMES.get(base);
			}

			return PotionType.valueOf("LONG_" + base);
		}

		if(str.endsWith("_S")){
			String base = str.substring(0, str.length()-2);

			if(REVERSE_NAMES.containsKey(base)){
				base = REVERSE_NAMES.get(base);
			}

			return PotionType.valueOf("STRONG_" + base);
		}

		try{
			if(REVERSE_NAMES.containsKey(str)){
				str = REVERSE_NAMES.get(str);
			}
			return PotionType.valueOf(str);
		}catch(Exception e){
			return PotionType.WATER;
		}
	}

	private static final java.util.Map<String, String> SHORT_NAMES = new java.util.HashMap<>();
	private static final java.util.Map<String, String> REVERSE_NAMES = new java.util.HashMap<>();

	static {
		SHORT_NAMES.put("AWKWARD", "AWK");
		SHORT_NAMES.put("FIRE_RESISTANCE", "FIRE");
		SHORT_NAMES.put("INSTANT_DAMAGE", "DMG");
		SHORT_NAMES.put("INSTANT_HEALTH", "HEAL");
		SHORT_NAMES.put("INFESTED", "INFES");
		SHORT_NAMES.put("INVISIBILITY", "INVIS");
		SHORT_NAMES.put("LEAPING", "JUMP");
		SHORT_NAMES.put("MUNDANE", "MUND");
		SHORT_NAMES.put("NIGHT_VISION", "NIGHT");
		SHORT_NAMES.put("REGENERATION", "REGEN");
		SHORT_NAMES.put("SLOW_FALLING", "SLFALL");
		SHORT_NAMES.put("SLOWNESS", "SLOW");
		SHORT_NAMES.put("STRENGTH", "STR");
		SHORT_NAMES.put("SWIFTNESS", "SPEED");
		SHORT_NAMES.put("TURTLE_MASTER", "TURTLE");
		SHORT_NAMES.put("WATER_BREATHING", "BREATH");
		SHORT_NAMES.put("WEAKNESS", "WEAK");
		SHORT_NAMES.put("WEAVING", "WEAV");
		SHORT_NAMES.put("WIND_CHARGED", "WIND");
		for (String key : SHORT_NAMES.keySet()) {
			REVERSE_NAMES.put(SHORT_NAMES.get(key), key);
		}
	}

	public static String getShortType(PotionType pot){

		String name = pot.name();
		String suffix = "";

		if(name.startsWith("LONG_")){
			name = name.substring(5);
			suffix = "_L";
		}

		else if(name.startsWith("STRONG_")){
			name = name.substring(7);
			suffix = "_S";
		}

		if(SHORT_NAMES.containsKey(name)){
			name = SHORT_NAMES.get(name);
		}

		return name + suffix;
	}

	public Material getMaterial() {
		return mat;
	}

	public PotionType getPotionType() {
		return pot;
	}

}
