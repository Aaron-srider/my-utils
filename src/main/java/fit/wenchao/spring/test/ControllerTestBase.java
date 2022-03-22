package fit.wenchao.spring.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

import static fit.wenchao.utils.basic.BasicUtils.*;

public class ControllerTestBase {
    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        ////构建mvc环境
        Field[] declaredFields = this.getClass().getDeclaredFields();

        boolean flag = gloop(forArr(declaredFields), (i, e, state, f) -> {
            Field field = e;
            boolean matches = Pattern.matches(".*Controller", field.getName());
            if (matches) {
                field.setAccessible(true);
                Object o = field.get(this);
                if (o != null) {
                    mockMvc = MockMvcBuilders.standaloneSetup(o).build();
                    f.set(true);
                    state.breakLoop();
                    return;
                } else {
                    state.continueLoop();
                    return;
                }
            }
        });

        if (!flag) {
            throw new AssertionError("当前测试了中未发现Controller对象");
        }

        doBeforeEach();
    }

    private void doBeforeEach() {

    }

    @AfterEach
    public void afterEach() {
        doAfterEach();
    }

    private void doAfterEach() {

    }
}
