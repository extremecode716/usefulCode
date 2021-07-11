import java.util.*;

// 도와드린 코드: ExtremeCode
public class LottoTmp {

	public static void main(String[] args) {
		int[] random = new int[6];
		int i, j;

		for (i = 0; i < random.length; i++) { 
			random[i] = (int) (Math.random() * 45) + 1; 
			for (j = 0; j < i; j++) {
				if (random[j] == random[i]) {
					i--;
					break;
				}
			}
		}
		
		Arrays.sort(random);
		System.out.println(Arrays.toString(random));
	}
}
