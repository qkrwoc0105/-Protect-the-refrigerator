package kr.ac.hansung.cse.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.cse.model.Product;

@Repository
public class ProductDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	// 데이터소스 주입받는다 => dao-context.xml에 보면 datasource라는 bean을 등록해놓음. 그 datasource가
	// 여기에 주입됨
	public void setDataSource(DataSource dataSource) { // setter메소드의 값이 인자로서 dataSource에 들어감.의존성주입
		jdbcTemplate = new JdbcTemplate(dataSource); // jdbcTemplate에 대한 객체생성
		// datasource를 사용해서 jdbcTemplate라는 클래스를 새롭게 만듦
		// 이 결과를 멤버변수에 집어넣음 dao는 jdbc template를 사용하기때문에 jdbcTemplate에 대한 객체를 생성하였음
	}

	public List<Product> getPhoto() {

		String sqlStatement = "select * from photo where photoID = (select photoID from photo order by photoID desc limit 1)";

		return jdbcTemplate.query(sqlStatement, new RowMapper<Product>() {

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

				Product product = new Product();

				String photoID = new String(rs.getString("photoID")); // int 타입은 getInt,

				String decodedString = new String(rs.getBytes("analyzedData"));

				product.setAnalyzedData(decodedString);
				product.setPhotoID(photoID); // 2020_05_27

				return product;

			}

		});
	}

	public List<Product> getProducts() {

		// String sqlStatement = "select * from photo order by photoID desc limit 1" ;
		// // 내림차순 정렬(desc):값이 큰것부터출력 <->asc
		// String sqlStatement = "(select * from photo order by photoID desc limit 1)
		// union (select * from photographedFood where photoID=?) ";
		// String sqlStatement = "select * from photo join photographedFood on
		// photo.photoID=photographedFood.photoID order by photoID desc limit 1 ";

		// String sqlStatement = "select * from photo order by photoID desc limit 1 in
		// (select * from photographedFood where photoID=?) ";
		// String sqlStatement = "select * from photographedFood natural join photo
		// order by photoID desc limit 2";
		String sqlStatement = "select * from photographedFood where photoID = (select photoID from photo order by photoID desc limit 1)";

		return jdbcTemplate.query(sqlStatement, new RowMapper<Product>() {

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

				Product product = new Product();

				// photoID date 형변환
				String photoID = new String(rs.getString("photoID"));
				String[] photoID_date = photoID.split("_");

				String now1 = photoID_date[0]; // 년
				String now2 = photoID_date[1]; // 월
				String now3 = photoID_date[2]; // 일
				String now4 = photoID_date[3]; // 시
				String now5 = photoID_date[4]; // 분
				String now6 = photoID_date[5]; // 초

				if (now2.length() == 1) {
					now2 = 0 + now2;
				}
				if (now4.length() == 1) {
					now4 = 0 + now4;
				}
				if (now5.length() == 1) {
					now5 = 0 + now5;
				}
				// String now = now1 + "년" + now2 + "월" + now3 + "일" + now4 + "시" + now5 + "분" +
				// now6 + "초";
				String now = now1 + now2 + now3 + now4 + now5 + now6;
				System.out.println(now);
				try {
					DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
					Date date = format.parse(now);
					// System.out.println(date);
					product.setDate(date);

					DateFormat format2 = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
					String nowString = format2.format(date);
					product.setNowString(nowString);

				} catch (ParseException e) {

					e.printStackTrace();
				}

				// inday date 형변환
				String inday = new String(rs.getString("inday"));
				String[] inday_date = inday.split("_");

				String in1 = inday_date[0]; // 년
				String in2 = inday_date[1]; // 월
				String in3 = inday_date[2]; // 일
				String in4 = inday_date[3]; // 시
				String in5 = inday_date[4]; // 분
				String in6 = inday_date[5]; // 초

				if (in2.length() == 1) {
					in2 = 0 + in2;
				}
				if (in4.length() == 1) {
					in4 = 0 + in4;
				}
				if (in5.length() == 1) {
					in5 = 0 + in5;
				}
				// String now = now1 + "년" + now2 + "월" + now3 + "일" + now4 + "시" + now5 + "분" +
				// now6 + "초";
				String in = in1 + in2 + in3 + in4 + in5 + in6;
				System.out.println(in);
				try {
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
					Date indate = format.parse(in);
					product.setIndate(indate);

					DateFormat format2 = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
					String inString = format2.format(indate);
					product.setInString(inString);
				} catch (ParseException e) {

					e.printStackTrace();
				}

				// shelfLife date로 형변환
				int shelfLife = rs.getInt("shelfLife");
				String shelfLifeS = Integer.toString(shelfLife);

				if (shelfLifeS.length() == 1) {
					shelfLifeS = 0 + shelfLifeS;
				}

				try {
					SimpleDateFormat format = new SimpleDateFormat("dd");
					Date last = format.parse(shelfLifeS); // last는 shelfLifeS를 DATE 타입

					DateFormat format2 = new SimpleDateFormat("dd일");
					String shelfLifeString = format2.format(last); // last의 date format 변환
					product.setShelfLifeString(shelfLifeString);
				} catch (ParseException e) {

					e.printStackTrace();
				}
				

				// inday-photoID 즉, indate-date
				try {
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
					Date indate = format.parse(in);
					Date date = format.parse(now);
					long diff = indate.getTime() - date.getTime();
					long diffDays = diff/(24*60*60*1000); //24시간.60분 60초 1000분의1
					
					//String diff2 = String.valueOf(diff);
					//product.setDiff2(diff2);
					
					System.out.println("날짜차이"+diffDays);
					product.setDiffDays(diffDays);
					
					//last - diff : diff를 date타입으로 바꿔서
					int diff2 = (int) (diffDays);
					product.setDiff2(diff2);
					
					int realShelf = shelfLife - diff2;
					product.setRealShelf(realShelf);
					
				} catch (ParseException e) {

					e.printStackTrace();
				}
				
				// inday + shelfLife  즉, indate + last
				
				try {
					String finish = null;
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
					Calendar cal = Calendar.getInstance();
					Date indate = format.parse(in);
					product.setIndate(indate);
					cal.setTime(indate);
					
					product.setShelfLife(shelfLife);
					//SimpleDateFormat format2 = new SimpleDateFormat("dd");
					//int last = format2.parse(shelfLifeS);
					
					cal.add(Calendar.DATE, shelfLife);
					
					finish = format.format(cal.getTime()); // finish는 String 타입
					System.out.println(finish); 
					
					SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
					Date finish2 = format2.parse(finish); 
					product.setFinish2(finish2);

					DateFormat format3 = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
					String finish3 = format3.format(finish2);
					product.setFinish3(finish3); //최종
				
				} catch (ParseException e) {

					e.printStackTrace();
				}
				
				
				
				String foodName = new String(rs.getString("foodName"));

				product.setCount(rs.getInt("count"));

				product.setFoodName(foodName);
				// product.setShelfLife(rs.getInt("shelfLife"));
				product.setShelfLifeS(shelfLifeS);
				product.setInday(inday);

				return product;

			}

		});
	}

}