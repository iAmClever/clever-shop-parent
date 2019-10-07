package server.feign;

import com.clever.service.MemberLoginService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by clever on 2019/9/2.
 */
@FeignClient("clever-shop-member")
public interface MemberLoginServiceFeign extends MemberLoginService {
}
