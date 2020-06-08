package kr.ac.hansung.cse.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//DB 조회<웹프2 동영상 2-2참조>

@ToString
@Getter
@Setter
public class Product {

	private String photoID;
	private Date date; //photoID를 DATE로 형변환시
	private String nowString; // date를 년,월로 format
	
	private String cameraID;

	private String data;

	private String analyzedData;

	private int count;
	
	private String foodName;
	
	private int shelfLife;
	private String shelfLifeS; //int type shelfLife -> String type 
	private Date last; //shelfLifeS의 DATE 변환
	private String shelfLifeString; //last의 date format 변환(일)

	
	private long diffDays;  // indate - date
	private long diff; //indate.getTime() - date.getTime();
	private int diff2; //long타입 diff를 string타입변환
	private int realShelf; //shelfLife - diff : 최종 남은 일수
	
	private String inday;
	private Date indate; //inday를 date 형변환
	private String inString; //inday를 DATE로 형변환 -> 년월 붙여서 String으로 
	
	private String finish; // indate + shelfLife STRING 형
	private Date finish2; // finish의 date형
	private String finish3; //finish2에 년월 삽입
	

	// private MultipartFile data;

	// private LongBlob data2;

	// private
}
