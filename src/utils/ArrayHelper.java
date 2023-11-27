package utils;

import java.util.ArrayList;
import java.util.function.Function;

public class ArrayHelper<T> {
    public static <T> ArrayList<T> Find(ArrayList<T> items, Function<T, Boolean> func){
        var output = new ArrayList<T>();
        for (var item : items) {
            if(func.apply(item)){
                output.add(item);
            }
        }
        return output;
    }
}
