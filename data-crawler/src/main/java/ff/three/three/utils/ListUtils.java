package ff.three.three.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Forest Wang
 * @package ff.three.three.utils
 * @class ListUtils
 * @email forest@magicwindow.cn
 * @date 2018/9/23 17:11
 * @description
 */
public class ListUtils {


    public static <T> List<List<T>> split2Group(List<T> list, int groupSize) {
        int length = list.size() / groupSize;
        List<List<T>> groups = new ArrayList<>();
        for (int i = 0; i < groupSize; i++) {
            int fromIndex = length * i;
            int toIndex = length * (i + 1);
            if (i == groupSize - 1) {
                toIndex = list.size();
            }
            List<T> item = new ArrayList<>();
            item.addAll(list.subList(fromIndex, toIndex));
            groups.add(item);
        }
        return groups;
    }

}
