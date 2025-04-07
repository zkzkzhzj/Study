package designpattern.Creational;

/**
 * 하나의 인스턴스만 존재
 * 전역 접근을 허용
 * 
 * 데이터 일관성 유지
 * 메모리 절약
 * 전역 접근성
 */
public class Singleton {
	public static void main(String[] args) {
		Logger logger1 = new Logger();
		logger1.log("첫 번째 로그");

        Logger logger2 = new Logger();
        logger2.log("두 번째 로그");

        System.out.println("동일 객체: " + (logger1 == logger2)); // false

		System.out.println("--------------------------------------");
		
		ApplyLogger applyLogger1 = ApplyLogger.getApplyLogger();
        logger1.log("첫 번째 로그");
        
        ApplyLogger applyLogger2 = ApplyLogger.getApplyLogger();
        logger2.log("두 번째 로그");

        System.out.println("동일 객체: " + (applyLogger1 == applyLogger2)); // true
	}
}

// 미적용
class Logger {
    public Logger() {
        System.out.println("새로운 로그 객체 생성!");
    }

    public void log(String message) {
        System.out.println("[LOGF]: " + message);
    }
}

// 적용
class ApplyLogger {
	private static volatile ApplyLogger applyLogger;
	
	private ApplyLogger() { }
	
	public static synchronized ApplyLogger getApplyLogger() {
        if (applyLogger == null) { 
            synchronized (ApplyLogger.class) {
                if (applyLogger == null) {
        	        System.out.println("새로운 로그 객체 생성!");
        	        applyLogger = new ApplyLogger();
                }
            }
        }
		return applyLogger;
	}
	
	public void log(String message) {
		System.out.println("[LOG]: " + message);
	}
}

/**
 * GPT 의 개선점
 * 
 * 멀티스레드 환경에서 문제 발생 여부 존재[동시 2개 이상 생성]
 *  - synchronized 키워드 사용
 *   - 하나의 스레드만 메소드에 접근하도록 보장 -> 하나의 객체만 생성 보장
 *   - 다만, 메소드 전체를 잠그기 때문에 성능 저하 발생 가능
 *   - 특히, 이미 인스턴스가 존재해도 매번 동기화 처리
 *  - 메소드 전체 동기화
 * 
 * 동기화로 인한 성능 저하 방지
 *  - 이중 검증 잠금(DCL) 방식 사용
 *   - 첫 번째 if 조건 -> 인스턴스가 없을 때만 동기화 블록 진입 하도록 설정
 *   - 동기화 블록 내부에서도 존재 확인하여 중복 생성 방지
 *  - volatile 키워드 사용
 *   - JVM 에서 인스턴스의 가시성 보장
 *   - 캐시가 아닌 메인 메모리에서 값을 읽도록 하여 인스턴스의 최신 상태를 보장
 */