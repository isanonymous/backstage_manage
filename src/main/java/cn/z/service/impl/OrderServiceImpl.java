package cn.z.service.impl;

import cn.z.entity.UserAddress;
import cn.z.service.OrderService;
import cn.z.service.UserService;
// import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
  
  // @Reference
  UserService userService;
  
  @Override
  public List<UserAddress> initOrder(String userId) {
    List<UserAddress> userAddressList = userService.getUserAddressList(userId);
    return userAddressList;
  }
}
