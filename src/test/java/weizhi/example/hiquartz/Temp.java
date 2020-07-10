package weizhi.example.hiquartz;

import java.util.Arrays;

/**
 * @author liweizhi
 * @date 2020/6/19
 */
public class Temp {

    public static void main(String[] args) {

    }

    static class Pet {
        String name;

        public Pet(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Pet{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
