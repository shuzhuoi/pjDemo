package crEmgcy;

import java.util.ArrayList;
import java.util.List;

public class Testw2 {
	public static void main(String[] args) {
		 List<Long> list = new ArrayList<>();
		 list.add(1L);
		 list.add(2L);
		 list.add(3L);
		 for (int i = 0; i <=4; i++) {
			 Long long1 = new Long(i);
			System.out.println(list.contains(long1));
		}
	}
}
