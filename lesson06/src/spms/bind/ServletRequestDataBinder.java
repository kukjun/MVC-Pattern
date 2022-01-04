package spms.bind;

import javax.servlet.ServletRequest;
import javax.xml.crypto.Data;
import java.lang.reflect.Method;
import java.util.Set;

public class ServletRequestDataBinder {

  // 프런트 컨트롤러에서 호출하는 메서드
  // 요청 매개변수 값과 데이터 이름, 데이터 타입을 받아서 데이터 객체를 만드는 일을 한다.
  public static Object bind(ServletRequest request, Class<?> dataType, String dataName) throws Exception {

    // dataType 이 기본 타입인지 검사
    if (isPrimitiveType(dataType)) {
      return createValueObject(dataType, request.getParameter(dataName));
    }

    // 기본 타입이 아닌 경우, 요청 매개 변수의 이름과 일치하는 Setter 메서드 호출
    Set<String> paramNames = request.getParameterMap().keySet();
    Object dataObject = dataType.newInstance();
    Method m;

    // 반복문을 통해 데이터 타입 클래스에서 매개변수 이름과 일치하는 프로퍼티( Setter Method )를 찾는다.
    for (String paramName : paramNames) {
      m = findSetter(dataType, paramName);
      if (m != null) {
        m.invoke(dataObject, createValueObject(m.getParameterTypes()[0],
                request.getParameter(paramName)));
      }
    }

    return dataObject;
  }


  private static boolean isPrimitiveType(Class<?> type) {
    if (type.getName().equals("int") || type == Integer.class ||
            type.getName().equals("long") || type == Long.class ||
            type.getName().equals("float") || type == Float.class ||
            type.getName().equals("double") || type == Double.class ||
            type.getName().equals("boolean") || type == Boolean.class ||
            type == Data.class || type == String.class) {
      return true;
    }
    return false;
  }

  private static Object createValueObject(Class<?> type, String value) {
    if (type.getName().equals("int") || type == Integer.class) {
      return Integer.valueOf(value);
    } else if (type.getName().equals("float") || type == Float.class) {
      return Float.valueOf(value);
    } else if (type.getName().equals("double") || type == Double.class) {
      return Double.valueOf(value);
    } else if (type.getName().equals("long") || type == Long.class) {
      return Long.valueOf(value);
    } else if (type.getName().equals("boolean") || type == Boolean.class) {
      return Boolean.valueOf(value);
    } else {
      return value;
    }
  }

  private static Method findSetter(Class<?> type, String name) {
    Method[] methods = type.getMethods();

    String propName;
    for (Method m : methods) {
      if (!m.getName().startsWith("set")) continue;
      propName = m.getName().substring(3);
      if (propName.toLowerCase().equals(name.toLowerCase())) {
        return m;
      }
    }
    return null;
  }
}
