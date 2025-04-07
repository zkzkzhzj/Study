package designpattern.Structural;

/**
 * 서로 다른 인터페이스를 가진 클래스들을 호환 가능하게 만듬
 * 클라이언트가 기대하는 인터페이스 제공
 *  - 기존 클래스를 수정하지 않고 재사용
 * 
 * 호환성 문제 해결
 *  - 기존 코드와 새로운 코드의 인터페이스가 다를 때
 * 코드 수정 최소화
 *  - 기존 클래스 수정 없이 새 인터페이스에 맞춤
 * 레거시 코드 활용
 *  - 오래된 코드를 새로운 구조에 맞춰 재사용할 때 유용
 */
public class Adapter {
	
	public static void main(String[] args) {
        AudioPlayer player = new AudioPlayer();

        player.play(MediaType.MP3, "mySong.mp3");
        player.play(MediaType.MP4, "video.mp4");
        player.play(MediaType.VLC, "movie.vlc");
	}
}

/**
 * Target(목표 인터페이스)
 *  - 클라이언트가 사용하려고 기대하는 인터페이스
 * 
 * Adaptee(적응 대상)
 *  - 이미 존재하는 클래스
 *   - 인터페이스가 달라서 클라이언트가 직접 사용하지 못함
 * 
 * Adapter(어댑터 클래스)
 *  - Target 인터페이스 구현
 *   - 내부에 Adaptee 객체를 사용하여 변환 수행
 * 
 * Client(클라이언트)
 *  - Target 인터페이스를 사용하여 작업 수행
 *   - 어댑터 덕분에 기존 클래스와 새 인터페이스를 매끄럽게 사용
 */
// Target
interface MediaPlayer {
	void play(MediaType type, String fileName);
}

// Adaptee
class Mp4Player {
	
	void play(String fileName) {
		System.out.println("Play MP4 file: " + fileName);
	}
}

class VlcPlayer {
	
    void play(String fileName) {
        System.out.println("Play VLC file: " + fileName);
    }
}

// Adapter
class MediaAdapter implements MediaPlayer {
	private Mp4Player mp4Player;
	private VlcPlayer vlcPlayer;
	
	public MediaAdapter(MediaType type) {
		switch (type) {
		case MP4: mp4Player = new Mp4Player(); break;
		case VLC: vlcPlayer = new VlcPlayer(); break;
		default: throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}

	@Override
	public void play(MediaType type, String fileName) {
		switch (type) {
		case MP4: mp4Player.play(fileName); break;
		case VLC: vlcPlayer.play(fileName); break;
		default: throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}
}

// Client
class AudioPlayer implements MediaPlayer {
    MediaAdapter mediaAdapter;

	@Override
	public void play(MediaType type, String fileName) {
		switch (type) {
		case MP3: System.out.println("Play MP3 file: " + fileName); break;
		case MP4:
		case VLC: 
            mediaAdapter = new MediaAdapter(type);
			mediaAdapter.play(type, fileName);
			break;
		default: throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}
}

enum MediaType {
	MP4, VLC, MP3
}

/**
 * 구현체를 보고 느낀점
 * 
 * 이미 구현된 클래스가 있고 확장을 해야할 때 쓰면 좋을 것 같다
 * 이미 구현된 클래스의 변동 없이 새로운 타입에서 실행할 부분들을 따로 지정해줄 수도 있는 장점이 보인다.
 */

/**
 * GPT 의 개선점
 * 
 * case 문에서 break 미선언
 * 
 * AudioPlayer 내부에서 MediaAdapter 객체 초기화를 해주지 않아 NPE 발생 가능성 있음
 */