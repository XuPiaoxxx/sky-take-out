package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时订单
     */
    @Scheduled(cron = "0 * * * * ? ")
    public void processTimeOut(){
        //select * from order where status = ? and order_time < now-15min
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        List<Orders> list = orderMapper.getByStatusAndTime(Orders.PENDING_PAYMENT, time);

        if(list!=null&&list.size()>0){
            for (Orders order : list) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时");
                order.setCancelTime(LocalDateTime.now());
                //orderMapper.update(orders);
            }
        }
    }
    /**
     * 处理堆积订单
     */
    @Scheduled(cron = "0 0 1 * * ? ")
    public void processDeliverOut(){
        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);
        List<Orders> list = orderMapper.getByStatusAndTime(Orders.DELIVERY_IN_PROGRESS, time);
        if(list!=null&&list.size()>0){
            for (Orders order : list) {
                order.setStatus(Orders.COMPLETED);
                //orderMapper.update(orders);
            }
        }
    }
}
