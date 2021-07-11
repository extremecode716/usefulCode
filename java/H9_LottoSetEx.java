//과제.  1~45 사이의 정수중에서 6개의 숫자를 추출하는
//로또 프로그램을 작성하세요?
//
//1) Set자료구조를 사용해서 중복 숫자가 나오지 않도록 작성하세요.
//2) 추출된 6개의 숫자를 오름차순으로 정렬해서 출력하세요.
//제출.
//author: ExtremeCode
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class H9_LottoSetEx {

	public static void main(String[] args) {
		Set<Integer> treeSet = new TreeSet<Integer>();
		Random random = new Random(System.currentTimeMillis());

		while (treeSet.size() < 6) {
			treeSet.add((Integer) (random.nextInt(45) + 1));
		}
		
		StringBuilder strBuilder = new StringBuilder(39);
		strBuilder.append("로또 번호 : ");
		strBuilder.append(treeSet.stream().map(num->num.toString()).collect(Collectors.joining(" / ", "( ", " )")));	
		System.out.println(strBuilder.toString());
	}
}
