package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.DistributionMapper;
import cn.hsf.hsfmanager.mapper.DistributionStatusMapper;
import cn.hsf.hsfmanager.mapper.UserOrderMapper;
import cn.hsf.hsfmanager.mapper.UserReleaseMapper;
import cn.hsf.hsfmanager.pojo.user.*;
import cn.hsf.hsfmanager.service.user.DistributionService;
import cn.hsf.hsfmanager.service.user.UserDetailService;
import cn.hsf.hsfmanager.service.user.UserService;
import cn.hsf.hsfmanager.service.wx.TemplateService;
import cn.hsf.hsfmanager.util.URLS;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DistributionServiceImpl implements DistributionService {

    @Resource
    private DistributionMapper distributionMapper;
    @Resource
    private DistributionStatusMapper distributionStatusMapper;
    @Resource
    private UserReleaseMapper userReleaseMapper;
    @Resource
    private UserOrderMapper userOrderMapper;
    @Resource
    private UserService userService;
    @Resource
    private TemplateService templateService;
    @Resource
    private UserDetailService userDetailService;

    @Override
    public int insDistribution(Distribution distribution) {
        return distributionMapper.insDistribution(distribution);
    }

    @Override
    public Distribution selByResId(Distribution distribution) {
        return distributionMapper.selByResId(distribution);
    }

    @Override
    public int updDistribution(Distribution distribution) {
        return distributionMapper.updDistribution(distribution);
    }

    @Override
    public Distribution selDistributionById(Integer id) {
        return distributionMapper.selDistributionById(id);
    }

    @Override
    public List<DistributionStatus> selAllDisName() {
        return distributionStatusMapper.selAllDisName();
    }

    @Override
    public int delDetailById(Integer id) {
        return distributionMapper.delDetailById(id);
    }

    @Override
    public DistributionStatus selByDisId(Integer id) {
        return distributionStatusMapper.selByDisId(id);
    }

    @Override
    public List<Distribution> selByDistribution(Distribution distribution) {
        return distributionMapper.selByDistribution(distribution);
    }

    @Override
    public List<Distribution> selDisList(Integer pageCurrentNo, Integer pageSize, Integer statusId, Integer sfId) {
        return distributionMapper.selDisList((pageCurrentNo-1)*pageSize,pageSize,statusId,sfId);
    }

    @Override
    public int selDisTotal(Integer statusId, Integer sfId) {
        return distributionMapper.selDisTotal(statusId, sfId);
    }

    /**
     * 更新状态
     * @param id
     * @param statusId
     * @param refusedMessage
     */
    @Override
    public void updStatus(Integer id, Integer statusId, String refusedMessage) {
        Distribution distribution = distributionMapper.selDistributionById(id);   //根据id得到派单表实体对象
        UserRelease userRelease = userReleaseMapper.selUserReleaseById(distribution.getReleaseId());//得到下单表对象
        User user = userService.selUserById(userRelease.getUserId());   //得到用户对象
        String userOpenId = user.getOpenId();   //用户openId
        UserDetail userDetail = null;
        User SfUser = null;
        if(distribution.getSfId() != null){
            userDetail = userDetailService.selUserDetailById(distribution.getSfId());   //得到师傅信息
            SfUser = userService.selUserByDetailId(userDetail.getId());   //得到师傅的微信信息
        }
        String userStatus = null;
        String orderNno = null;
        String changeMessage = null;
        String changeMessagesf = null;
        if(statusId == 1 || statusId == 8){  //新订单  订单失效
            //修改派单表
            Distribution dis = new Distribution();
            dis.setId(id);
            dis.setStatusId(statusId);
            distributionMapper.updStatus(dis);
            //修改下单表
            userReleaseMapper.updStatus(new UserRelease(distribution.getReleaseId(),statusId));
            //修改订单表
            if(distribution.getOrderId() != null && !distribution.getOrderId().equals("")){
                userOrderMapper.delUserOrderById(distribution.getOrderId());
            }
            userStatus = "新订单";
            orderNno = "无";
            changeMessage = "平台将为您分配师傅，请等待";
        }else if(statusId == 2){  //服务进行中
            //新增订单表
            UserOrder userOrder = new UserOrder();
            userOrderMapper.insUserOrder2(userOrder);
            //修改派单表
            distributionMapper.updDistribution(new Distribution(id,statusId, userOrder.getId(),1));
            //修改下单表
            userReleaseMapper.updateUserRelease(new UserRelease(distribution.getReleaseId(),statusId,distribution.getSfId()));
            userStatus = "已接单";
            orderNno =  userOrder.getId() +"";
            changeMessage = "您的订单已经有师傅接单\\n师傅信息 ："+userDetail.getName() +":"+SfUser.getPhone();
            changeMessagesf = "接单成功\\n雇主信息 ："+userRelease.getNickName()+":"+userRelease.getPhone();
        }else if(statusId == 3 || statusId == 5){  //已拒单  已取消
            //修改派单表
            distributionMapper.updDistribution(new Distribution(id,statusId,refusedMessage,1));
            //删除评论表中的orderId
            // userOrderMapper.delUserOrderById(distribution.getOrderId());
            //修改下单表 仍为新订单
            userReleaseMapper.updStatus(new UserRelease(distribution.getReleaseId(),1));
            userStatus = statusId == 3 ? "已拒单" :"已取消";
            orderNno =  distribution.getOrderId() +"";
            String a = statusId == 3 ? "拒绝了您的雇佣" :"取消了此次订单";
            changeMessage = "抱歉师傅因为特殊原因"+a+"。请等待平台给您另外安排师傅。";
            String s = statusId == 3 ? "拒单" : "订单取消";
            changeMessagesf = s+"成功\\n雇主信息 ："+userRelease.getNickName()+":"+userRelease.getPhone();
        }else if(statusId == 6 || statusId == 7){  // 已完成 申请完工
            //修改派单表
            distributionMapper.updDistribution(new Distribution(id,statusId,1));
            //修改下单表
            if(userRelease.getId() !=null){
                userReleaseMapper.updateUserRelease(new UserRelease(distribution.getReleaseId(),statusId));
            }else{
                UserOrder userOrder = new UserOrder();
                userOrderMapper.insUserOrder2(userOrder);
                userReleaseMapper.updateUserRelease(new UserRelease(distribution.getReleaseId(),statusId,distribution.getSfId()));
            }
            userStatus = statusId == 6 ? "已完成" :"申请完工";
            orderNno =  distribution.getOrderId() +"";
            String a = statusId == 6 ? "您此次的订单已完工" :"师傅申请完工，请您点击查看确认信息";
            changeMessage = a+ "\\n师傅信息 ："+userDetail.getName() +":"+SfUser.getPhone();
            String s = statusId == 6 ? "订单完工成功" : "订单已申请完工";
            String changeMessagesf1 = s+"\\n雇主信息 ："+userRelease.getNickName()+":"+userRelease.getPhone();
            changeMessagesf = statusId == 6 ?  changeMessagesf1+"\\n感谢您的使用" :changeMessagesf1 +"\\n请您耐心等待";


            //给用户发送的
            Map map = new HashMap();
            map.put("openId",userOpenId) ;
            map.put("template_id","HI9ygOFtJ_rbPK1JT3KD8ujsfIcaRBeCJrhQqgRZ0Oc") ;
            map.put("url", URLS.DOMAIN_NAME+"/_api/goUserOrderDetail?id=" + userRelease.getId());
            map.put("title","您的订单状态更新啦") ;
            map.put("serviceType","订单状态更改通知") ;
            map.put("orderNo",orderNno) ;
            map.put("orderState",userStatus) ;
            map.put("end",changeMessage);
            templateService.serviceStatus(map);
        }
        if(statusId ==1 || statusId == 2 || statusId == 3 || statusId == 5){
            //给用户发送的
            Map map = new HashMap();
            map.put("openId",userOpenId) ;
            map.put("template_id","HI9ygOFtJ_rbPK1JT3KD8ujsfIcaRBeCJrhQqgRZ0Oc") ;
            map.put("url", URLS.DOMAIN_NAME+"/_api/goUserOrderDetail?id=" + userRelease.getId());
            map.put("title","您的订单状态更新啦") ;
            map.put("serviceType","订单状态更改通知") ;
            map.put("orderNo",orderNno) ;
            map.put("orderState",userStatus) ;
            map.put("end",changeMessage);
            templateService.serviceStatus(map);
        }
        if(statusId !=1 && statusId !=4){
            //给师傅发送通知
            Map map2 = new HashMap();
            map2.put("openId",SfUser.getOpenId()) ;
            map2.put("url",URLS.DOMAIN_NAME+"/_api/goOrderShow?id="+id);
            map2.put("template_id","HI9ygOFtJ_rbPK1JT3KD8ujsfIcaRBeCJrhQqgRZ0Oc") ;
            map2.put("title","您的订单状态更新啦") ;
            map2.put("serviceType","订单状态更改通知") ;
            map2.put("orderNo",orderNno) ;
            map2.put("orderState",userStatus) ;
            map2.put("end",changeMessagesf) ;
            templateService.serviceStatus(map2);
        }

    }
}
