import java.lang.reflect.Field;

public class private_파라미터_리플렉션 {
    static class Person {
        private int weight;

        // setter 없이 getter만 존재함 => 값 변경 불가
        public int getWeight() {
            return weight;
        }
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Person p = new Person();

        Class<Person> personClass = Person.class;
        Field weight = personClass.getDeclaredField("weight");

        // 단, JPMS 사용시 module-info에 opens를 정의해줘야 함
        weight.setAccessible(true); // private 제어를 위한 설정(DeepReflection)

        // private 파라미터 값 변경
        weight.set(p, 100);

        System.out.println("weight: " + p.getWeight());
    }
}
