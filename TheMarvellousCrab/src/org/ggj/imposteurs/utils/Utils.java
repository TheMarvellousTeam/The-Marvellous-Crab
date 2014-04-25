package org.ggj.imposteurs.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

public class Utils {
	public static <T> Type getArrayListTypeFor(Class<T> clazz){
		Type collectionType = new TypeToken<ArrayList<T>>(){}.getType();
		return collectionType;
}
}
