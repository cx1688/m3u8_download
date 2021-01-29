import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

interface Listener {
    void setInfo(String msg);
}

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-29 15:46
 */
public class TestListener {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i <100; i++) {

            list.add(UUID.randomUUID().toString());
        }
    }

    void test(List<String> list, Listener listener) {
        list.stream().forEach(t -> {
            listener.setInfo(t);
        });
    }
}