package Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author chenshuzhuo
 * @date 2018-02-05
 */
public class ListPageUtil {
	/**
	 * 
	 * @param page
	 *            当前页数
	 * @param pageSize
	 *            每页得大小
	 * @param list
	 *            分页的对象
	 * @return
	 */
	private static List<Integer> getListPage(int page, int pageSize, List<Integer> list) {
		if (list == null || list.size() == 0) {
			throw new RuntimeException("分页数据不能为空!");
		}
		
		int totalCount = list.size();
		page = page - 1;
		int fromIndex = page * pageSize;
		//分页不能大于总数
	    if(fromIndex>=totalCount) {
	    	throw new RuntimeException("页数或分页大小不正确!");
		}
		int toIndex = ((page + 1) * pageSize);
		if (toIndex > totalCount) {
			toIndex = totalCount;
		}
		return list.subList(fromIndex, toIndex);

	}

	public static void main(String[] args) {
		// 构造100条数据
		List<Integer> list = new ArrayList<Integer>();
		for (Integer i = 1; i <= 100; i++) {
			list.add(i);
		}

		int page = 1;// 第一页
		int pageSize = 10;// 每页10条

		List<Integer> listPage = getListPage(page, pageSize, list);
		System.out.println("第" + page + "页");
		for (Integer integer : listPage) {
			System.out.println(integer);
		}

		// 测试第10页
		page = 10;
		listPage = getListPage(page, pageSize, list);
		System.out.println("第" + page + "页");
		for (Integer integer : listPage) {
			System.out.println(integer);
		}

		// 测试第11页list分页
		page = 11;
		listPage = getListPage(page, pageSize, list);
		System.out.println("第" + page + "页");
		for (Integer integer : listPage) {
			System.out.println(integer);
		}

	}
}
