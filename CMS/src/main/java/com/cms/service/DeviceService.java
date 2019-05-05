package com.cms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cms.dao.DeviceMapper;
import com.cms.pojo.ConstData;
import com.cms.pojo.Device;
import com.cms.pojo.Page;
import com.cms.pojo.User;
import com.cms.util.CMSException;
import com.cms.util.DateUtil;
import com.cms.util.StringUtil;

@Service
public class DeviceService {
		@Resource
		private DeviceMapper deviceMapper;
		@Resource
		private UserService userService;
		
		
		private void updateDeviceMsg(Device device){
			device.setOpDate(new Date());
			deviceMapper.updateByPrimaryKeySelective(device);
		}
		
		public void updateDeviceBaseMsg(Device device) throws Exception{
			if (StringUtil.isNotEmptyOrNull(device.getStatus())) {
				throw new CMSException("不能直接修改设备状态！");
			}
			this.updateDeviceMsg(device);
		}
		
		public  Device  getDeviceMsg(String iemi) {
			return deviceMapper.selectByPrimaryKey(iemi);
		}
		
		/**
		 * 添加设备
		 * @param device
		 * @throws Exception
		 */
		public void addDevice(Device device) throws Exception {
			Device oldDevice=this.getDeviceMsg(device.getIemi());
			if (oldDevice!=null) {
				throw new CMSException("该设备号已被注册，请检查设备号！");
			}
			device.setStatus(ConstData.DEVICE_STATUS_FREE);
			device.setCreateDate(new Date());
			deviceMapper.insert(device);
		}
		
		/**
		 * 分页查询设备
		 * @param map
		 * @return
		 */
		public Page queryPageByCondition(Map<String, Object> map){
			int currPage = (Integer) map.get("currPage");
			int pageSize = (Integer) map.get("pageSize");
			int count=deviceMapper.queryCountByCondition(map);
			Page page=new Page(count, pageSize, currPage);
			if (currPage <= 0) {
				currPage = 1;
			}
			map.put("index", page.getIndex());
			List<Device> list=deviceMapper.queryPageByCondition(map);
			page.setList(list);
			return page;
		}
		
		/**
		 * 申请使用设备
		 * @param userId
		 * @throws Exception 
		 */
		public void useDevice(int userId,String iemi) throws Exception{
			Device device=this.getDeviceMsg(iemi);
			if(device.getStatus().equals(ConstData.DEVICE_STATUS_USE)||StringUtil.isNotEmptyOrNull(device.getUserId())){
				throw new CMSException("该设备正在被使用中！");
			}
			if (device.getStatus().equals(ConstData.DEVICE_STATUS_BREAK)) {
				throw new CMSException("该设备已损坏！");
			}
			device.setUserId(userId);
			device.setOpDate(DateUtil.getSysDate());
			device.setStatus(ConstData.DEVICE_STATUS_USE);
			this.updateDeviceMsg(device);
		}
		/**
		 * 停止使用设备
		 * @param iemi
		 * @throws Exception 
		 */
		public void stopDevice(String iemi) throws Exception {
			Device device=this.getDeviceMsg(iemi);
			if(device.getStatus().equals(ConstData.DEVICE_STATUS_FREE)||StringUtil.isEmptyOrNull(device.getUserId())){
				throw new CMSException("该设备没有被使用！");
			}
			if (device.getStatus().equals(ConstData.DEVICE_STATUS_BREAK)) {
				throw new CMSException("该设备已损坏！");
			}
			device.setUserId(null);
			device.setOpDate(new Date());
			device.setStatus(ConstData.DEVICE_STATUS_FREE);
			deviceMapper.stopDevice(device);
		}
		
		/**
		 * 设备报损
		 * @param iemi
		 * @throws Exception
		 */
		public void deleteDevice(String iemi) throws Exception {
			Device device=this.getDeviceMsg(iemi);
			if (!device.getStatus().equals(ConstData.DEVICE_STATUS_USE)) {
				throw new CMSException("设备状态异常，只能报损使用中的设备！");
			}
			if (StringUtil.isEmptyOrNull(device.getUserId())) {
				throw new CMSException("设备责任人不能为空！");
			}
			device.setUserId(null);
			device.setOpDate(DateUtil.getSysDate());
			device.setStatus(ConstData.DEVICE_STATUS_BREAK);
			this.updateDeviceMsg(device);
		}
		/**
		 * 打分时查询设备号
		 * @param iemi
		 * @return
		 * @throws Exception
		 */
		public int  getJudgeIdByDeviceIemi(String iemi) throws Exception {
			Device device=deviceMapper.selectByPrimaryKey(iemi);
			if(null==device){
				throw new CMSException("该设备不存在！");
			}
			if (device.getStatus().equals(ConstData.DEVICE_STATUS_BREAK)) {
				throw new CMSException("该设备已损坏！");
			}
			if(device.getStatus().equals(ConstData.DEVICE_STATUS_FREE)||StringUtil.isEmptyOrNull(device.getUserId())){
				throw new CMSException("该设备没有被使用！");
			}
			User user=new User();
			user.setUserId(device.getUserId());
			user=userService.getUserMsg(user);
			if (!user.getUserRole().equals(ConstData.JUDGE_ROLE_DEFAULT)) {
				throw new CMSException("该设备用户的不是评委！");
			}
			return device.getUserId();
		}
}
