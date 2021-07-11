//과제.
//1. 키보드를 통해서 주민번호를 입력 했을때 유효한 
//   주민번호 인지 아닌지를 판별하는 프로그램을 작성 
//   하세요?
//   (단, 유효하지 않은 주민번호의 경우에는 메시지를 출력)
//
//1. 주민번호 앞자리 6자리가 아니면 메시지 출력
//2. 주민번호 뒷자리 7자리가 아니면 메시지 출력
//3. 유효한 주민번호 아니면 메시지 출력
//제출.
//author: ExtremeCode
import java.util.Scanner;
import java.util.StringTokenizer;
public class H6_ResidentRegistrationNumber {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String strInputRRN = null;
		boolean bRRN = false;

		do {
			System.out.print("주민번호를 입력하세요(xxxxxx-xxxxxxx) : ");
			strInputRRN = sc.nextLine();
			if (!(bRRN = checkRRN(strInputRRN))) {
				printRRN_Error_Message();
			}
		} while (!bRRN);

		System.out.println("주민번호 유효성 검사 통과!");

		sc.close();
	}

	static boolean checkRRN(String _strInputRRN) {
		StringTokenizer strTokenRRN = new StringTokenizer(_strInputRRN, "-");
		if (2 != strTokenRRN.countTokens())
			return false;

		String strFrontRRN = strTokenRRN.nextToken();
		String strBackRRN = strTokenRRN.nextToken();

		if (6 != strFrontRRN.length() || !isNumber(strFrontRRN)) // 1.
		{
			return false;
		}
		if (7 != strBackRRN.length() || !isNumber(strBackRRN)) // 2.
		{
			return false;
		}

		final int[] arrFrontMagicNumbers = { 2, 3, 4, 5, 6, 7 };
		final int[] arrBackMagicNumbers = { 8, 9, 2, 3, 4, 5 };
		final int iDivideMagicNum = 11,
				iCheckMagicNum1 = Character.getNumericValue(strBackRRN.charAt(strBackRRN.length() - 1)),
				iCheckMagicNum2 = 10;
		int iAdd = 0, iRemainder = 0, iCalcResult = 0;

		// 3.
		for (int i = 0; i < arrFrontMagicNumbers.length; ++i) {
			iAdd += Character.getNumericValue(strFrontRRN.charAt(i)) * arrFrontMagicNumbers[i];
		}
		for (int i = 0; i < arrBackMagicNumbers.length; ++i) {
			iAdd += Character.getNumericValue(strBackRRN.charAt(i)) * arrBackMagicNumbers[i];
		}

		iRemainder = iAdd % iDivideMagicNum;
		iCalcResult = iDivideMagicNum - iRemainder;
		if (iCalcResult >= iCheckMagicNum2) {
			iCalcResult = iRemainder = iCalcResult % iCheckMagicNum2;
		}		
		if (iCalcResult == iCheckMagicNum1)
			return true;

		return false;
	}

	static boolean isNumber(String _str) {
		for (int i = 0; i < _str.length(); ++i) {
			if (!Character.isDigit(_str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	static void printRRN_Error_Message() {
		System.out.println("주민번호 유효성 실패! 입력 예시)xxxxxx-xxxxxxx");
	}
}
