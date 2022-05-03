package fit.wenchao.spring.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static fit.wenchao.utils.basic.BasicUtils.forArr;
import static fit.wenchao.utils.basic.BasicUtils.gloop;

@Slf4j
public class ControllerTestBase {
    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        log.info("================================开始构建mockmvc测试环境================================");

        //构建mvc环境
        Field[] declaredFields = this.getClass().getDeclaredFields();
        List<Object> controllers2registered2mockmvcEvn = new ArrayList<>();
        List<HandlerInterceptor> interceptors2registered2mockmvcEvn = new ArrayList<>();
        List<HandlerMethodArgumentResolver> argumentResolvers2registered2mockmvcEvn = new ArrayList<>();
        List<Object> controllerAdvices2registered2mockmvcEvn = new ArrayList<>();
        gloop(forArr(declaredFields), (i, field, state) -> {
            field.setAccessible(true);
            Object o = field.get(this);
            //if (o != null) {
            //    if ((o.getClass().getAnnotation(Controller.class) != null || o.getClass().getAnnotation(RestController.class) != null)) {
            //        controllers2registered2mockmvcEvn.add(o);
            //        log.info("测试类 \"{}\" 中发现controller \"{}\".", this.getClass().getName(), field.getType().getName());
            //    }
            //    if(o.getClass().getAnnotation(ControllerAdvice.class)!=null) {
            //        controllerAdvices2registered2mockmvcEvn.add(o);
            //        log.info("测试类 \"{}\" 中发现controllerAdvice \"{}\".", this.getClass().getName(), field.getType().getName());
            //    }
            //}

            if ((field.getType().getAnnotation(Controller.class) != null || field.getType().getAnnotation(RestController.class) != null)) {
                Object controller = field.getType().newInstance();

                controllers2registered2mockmvcEvn.add(controller);
                log.info("测试类 \"{}\" 中发现controller \"{}\".", this.getClass().getName(), field.getType().getName());
            }
            if (field.getType().getAnnotation(ControllerAdvice.class) != null) {
                Object controllerAdvice = field.getType().newInstance();

                controllerAdvices2registered2mockmvcEvn.add(controllerAdvice);
                log.info("测试类 \"{}\" 中发现controllerAdvice \"{}\".", this.getClass().getName(), field.getType().getName());
            }

            if (HandlerInterceptor.class.isAssignableFrom(field.getType())) {
                Object interceptor = field.getType().newInstance();
                interceptors2registered2mockmvcEvn.add((HandlerInterceptor) interceptor);
                log.info("测试类 \"{}\" 中发现Interceptor \"{}\".", this.getClass().getName(), field.getType().getName());
            }

            if (HandlerMethodArgumentResolver.class.isAssignableFrom(field.getType())) {
                Object argumentResolver = field.getType().newInstance();
                argumentResolvers2registered2mockmvcEvn.add((HandlerMethodArgumentResolver) argumentResolver);
                log.info("测试类 \"{}\" 中发现ArgumentResolver \"{}\".", this.getClass().getName(), field.getType().getName());
            }
        });


        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders
                .standaloneSetup(controllers2registered2mockmvcEvn.toArray(new Object[0]));
        standaloneMockMvcBuilder.addInterceptors(interceptors2registered2mockmvcEvn.toArray(new HandlerInterceptor[0]));
        standaloneMockMvcBuilder.setCustomArgumentResolvers(argumentResolvers2registered2mockmvcEvn.toArray(new HandlerMethodArgumentResolver[0]));
        standaloneMockMvcBuilder.setControllerAdvice(controllerAdvices2registered2mockmvcEvn);
        addComponents(standaloneMockMvcBuilder);


        mockMvc = standaloneMockMvcBuilder
                .build();

        log.info("注册controllers:{}", controllers2registered2mockmvcEvn);
        log.info("注册interceptors:{}", interceptors2registered2mockmvcEvn);
        log.info("注册argumentResolvers:{}", argumentResolvers2registered2mockmvcEvn);
        log.info("注册controllerAdvices:{}", controllerAdvices2registered2mockmvcEvn);

        if (controllers2registered2mockmvcEvn.isEmpty()) {
            log.warn("当前测试类中没有发现controller字段，之后的所有请求都将返回404");
        }

        log.info("================================mockmvc测试环境构建完成================================");

        doBeforeEach();
    }

    protected void addComponents(StandaloneMockMvcBuilder standaloneMockMvcBuilder) {

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
