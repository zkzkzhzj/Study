package oop.grasp;

/**
 * 간접화
 * 
 * 객체들 사이의 직접적인 결합 피함
 *  - 중간 객체를 통해 메시지를 전달함으로써 유연성과 결합도 개선
 * 
 * 직접 연결된 객체들 간의 강한 결합 끊어냄
 *  - 그 사이에 중재자, 어댑터, 인터페이스 등을 두어 유연한 구조 생성
 */
public class Indirection {
	
	public static void main(String[] args) {
		// 수정 전
		IndirectionLogger logger = new IndirectionLogger();
        logger.logToFile("에러 발생!");
        
        // 수정 후
        IndirectionFileLogger fileLogger = new IndirectionFileLogger();
        fileLogger.log("에러 발생!");
        
        IndirectionDBLogger dbLogger = new IndirectionDBLogger();
        dbLogger.log("에러 발생!");
	}
}

/**
 * 수정 전
 * 
 * 로그 방식이 logToFile() 로 직접 고정되어 있음
 * 추후 방식이 변경되면 로거 클래스 수정이 필요
 *  - OCP 위반, 결합도 높음
 */
class IndirectionLogger {
    public void logToFile(String message) {
        System.out.println("[파일 로그] " + message);
    }
}

// 수정 후
interface IndirectionLog {
	void log(String message);
}

class IndirectionFileLogger implements IndirectionLog {

	@Override
	public void log(String message) {
		System.out.println("[파일 로그] " + message);
	}
}

class IndirectionDBLogger implements IndirectionLog {

	@Override
	public void log(String message) {
		System.out.println("[DB 로그] " + message);
	}
}

