package designpattern.Structural;

import java.util.HashMap;
import java.util.Map;

/**
 * 복잡한 시스템을 단순하게 사용할 수 있도록 인터페이스 제공
 *  - 여러 개의 객체와 그 객체들의 복잡한 상호작용을 감추고, 단순한 인터페이스 제공
 * 
 * 단순화
 *  - 복잡한 서브 시스템을 감추고 단일 인터페이스 제공
 * 캡슐화
 *  - 클라이언트가 내부 세부 사항을 몰라도 사용할 수 있음
 * 의존성 감소
 *  - 클라이언트가 복잡한 서브 시스템과 직접 연결되지 않음
 */
public class Facade {

	public static void main(String[] args) {

        CharacterFacade facade = new CharacterFacade();
        facade.createCharacter(GENDER.MALE, TRIBE.ELF, WEAPON.SWORD);
        facade.createCharacter(GENDER.FEMALE, TRIBE.HUMAN, WEAPON.GUN);
	}
}
/**
 * Subsystem(서브 시스템)
 *  - 복잡하고 다양한 기능을 가진 여러 클래스
 * 
 * Facade(퍼사드)
 *  - 클라이언트가 복잡한 서브 시스템을 쉽게 사용할 수 있도록 간단한 인터페이스 제공
 * 
 * Client(클라이언트)
 *  - Facade 를 통해 복잡한 서브 시스템을 간단하게 사용
 */
enum GENDER {
	MALE, FEMALE
}

enum TRIBE {
	HUMAN, ELF, GIANT
}

enum WEAPON {
	SWORD, GUN, WAND
}

// Subsystem
class GenderSetting {
	
	public String setGender(GENDER gender) {
		System.out.println("Gender 설정: " + gender);
		return gender.toString();
	}
}

class TribeSetting {
	
	public String setTribe(TRIBE tribe) {
		System.out.println("Tribe 설정: " + tribe);
		return tribe.toString();
	}
}

class WeaponSetting {
	
	public String setWeapon(WEAPON weapon) {
		System.out.println("Weapon 설정: " + weapon);
		return weapon.toString();
	}
}

class CreateCharacter {
	
	private Map<Integer, String> characterData = new HashMap<>();
	private int idCounter = 1;
	
	public void createCharacter(String data) {
		characterData.put(idCounter, data);
		System.out.println("캐릭터 생성 완료");
	}
}

class LogService {
	
	public void log(String message) {
		System.out.println("[LOG] : " + message);
	}
}

// Facade
// 개별로 생성하고 선언하면 메인 코드가 복잡해지지만 책임질 클래스 생성하여 관리
class CharacterFacade {
    private GenderSetting genderSetting;
    private TribeSetting tribeSetting;
    private WeaponSetting weaponSetting;
    private CreateCharacter createCharacter;
    private LogService logger;

    public CharacterFacade() {
        this.genderSetting = new GenderSetting();
        this.tribeSetting = new TribeSetting();
        this.weaponSetting = new WeaponSetting();
        this.createCharacter = new CreateCharacter();
        this.logger = new LogService();
    }

    public void createCharacter(GENDER gender, TRIBE tribe, WEAPON weapon) {
        String genderData = genderSetting.setGender(gender);
        String tribeData = tribeSetting.setTribe(tribe);
        String weaponData = weaponSetting.setWeapon(weapon);
        
        String characterData = "Character [Gender: " + genderData + ", Tribe: " + tribeData + ", Weapon: " + weaponData + "]";
        createCharacter.createCharacter(characterData);
        
        logger.log("캐릭터 생성 완료: " + characterData);
    }
}