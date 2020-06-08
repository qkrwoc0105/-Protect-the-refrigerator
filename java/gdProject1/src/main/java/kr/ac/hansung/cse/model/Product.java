package kr.ac.hansung.cse.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//DB ��ȸ<����2 ������ 2-2����>

@ToString
@Getter
@Setter
public class Product {

	private String photoID;
	private Date date; //photoID�� DATE�� ����ȯ��
	private String nowString; // date�� ��,���� format
	
	private String cameraID;

	private String data;

	private String analyzedData;

	private int count;
	
	private String foodName;
	
	private int shelfLife;
	private String shelfLifeS; //int type shelfLife -> String type 
	private Date last; //shelfLifeS�� DATE ��ȯ
	private String shelfLifeString; //last�� date format ��ȯ(��)

	
	private long diffDays;  // indate - date
	private long diff; //indate.getTime() - date.getTime();
	private int diff2; //longŸ�� diff�� stringŸ�Ժ�ȯ
	private int realShelf; //shelfLife - diff : ���� ���� �ϼ�
	
	private String inday;
	private Date indate; //inday�� date ����ȯ
	private String inString; //inday�� DATE�� ����ȯ -> ��� �ٿ��� String���� 
	
	private String finish; // indate + shelfLife STRING ��
	private Date finish2; // finish�� date��
	private String finish3; //finish2�� ��� ����
	

	// private MultipartFile data;

	// private LongBlob data2;

	// private
}
