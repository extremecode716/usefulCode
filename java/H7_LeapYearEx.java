//과제.
//키보드를 통해서 연도(ex) 2020)를  입력 받는다.
//이때 입력 받은 연도가 윤년인지 평년인지를 판별하는 
//프로그램을 작성하세요?
//제출.
//author: ExtremeCode
import java.util.Scanner;
import java.util.GregorianCalendar;

public class H7_LeapYearEx {
	@FunctionalInterface
	interface LeapYearFunctionalInterface {
		public abstract boolean run(int _iValue);
	}

	enum LeapYearMode {
		USER_METHOD, GCALENDAR_METHOD
	}

	public static void main(String[] args) {
		final Scanner sc = new Scanner(System.in);
		final GregorianCalendar gcalendar = new GregorianCalendar();
		LeapYearMode leapYearMode = LeapYearMode.GCALENDAR_METHOD; // 사용자 정의 (수정)
		LeapYearFunctionalInterface func = null;

		switch (leapYearMode) {
		case USER_METHOD:
			func = (x) -> H7_LeapYearEx.isLeapYear(x);
			break;
		case GCALENDAR_METHOD:
			func = (x) -> gcalendar.isLeapYear(x);
			break;
		default:
			sc.close();
			return;
		}

		String strInputData = null;
		boolean bInt = false;
		int iYear = 0;

		System.out.println("윤년인지 평년인지 판별하는 프로그램");
		do {
			System.out.print("입력 년도 : ");
			strInputData = sc.nextLine();
			if (bInt = tryParseInt(strInputData)) {
				iYear = Integer.parseInt(strInputData);

				if (func.run(iYear)) {
					System.out.format("%d 는 윤년 입니다.\n", iYear);
				} else {
					System.out.format("%d 는 평년 입니다.\n", iYear);
				}
			}
		} while (bInt);

		System.out.println("process terminated");

		sc.close();
	}

	static boolean isLeapYear(int _iYear) {
		if ((_iYear & 3) != 0) {
			return false;
		}

		return (_iYear % 100 != 0) || (_iYear % 400 == 0);
	}

	static boolean isLeapYear_Tmp(int _iYear) {
		if (_iYear % 400 == 0)
			return true;
		if (_iYear % 100 == 0)
			return false;
		if (_iYear % 4 == 0)
			return true;
		return false;
	}

	static boolean isLeapYear_Tmp1(int _iYear) {
		if (_iYear % 4 == 0 && _iYear % 100 != 0 || _iYear % 400 == 0)
			return true;
		return false;
	}

	static boolean tryParseInt(String _strValue) {
		try {
			Integer.parseInt(_strValue);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
