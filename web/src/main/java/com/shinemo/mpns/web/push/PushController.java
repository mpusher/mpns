package com.shinemo.mpns.web.push;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.shinemo.mpns.client.common.GsonUtil;
import com.shinemo.mpns.client.mpush.domain.NotificationPushPayload;
import com.shinemo.mpns.client.mpush.domain.PayloadFactory;
import com.shinemo.mpns.client.mpush.domain.PayloadFactory.MessageType;
import com.shinemo.mpns.client.mpush.domain.PushRequest;
import com.shinemo.mpns.client.mpush.domain.PushRequest.AttributeKey;
import com.shinemo.mpns.client.mpush.domain.PushType;
import com.shinemo.mpns.client.mpush.service.PushService;
import com.shinemo.mpns.core.async.InternalEventBus;
import com.shinemo.mpns.core.async.event.UserOnlineEvent;
import com.shinemo.mpns.core.mpush.service.AllotService;
import com.shinemo.mpns.core.mpush.service.ConnectionService;
import com.shinemo.mpns.core.mpush.service.UserService;
import com.shinemo.mpns.web.push.vo.PushVO;
import com.shinemo.mpns.web.push.vo.StatVO;
import com.shinemo.mpns.web.push.vo.StatVO.StatType;
import com.shinemo.mpns.web.util.PushUtil;

@Controller
@RequestMapping("/push")
public class PushController {
	
	@Resource
	private PushService pushService;
	
	@Resource
	private ConnectionService connService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private InternalEventBus internalEventBus;
	
	@Resource
	private AllotService allotService;
	
	private static final Executor pool = Executors.newFixedThreadPool(5);

	@RequestMapping(value="/hello",method=RequestMethod.GET)
	public String helloView() {
        return "push/hello";
	}
	
	@RequestMapping(value="/conn",method=RequestMethod.GET)
	@ResponseBody
	public String conn(PushVO pushVO) throws Exception{
		if(pushVO.getUserId()==null){
			return "none";
		}
		connService.connect(Arrays.asList(pushVO.getUserId()));
        return "success";
	}
	
	@RequestMapping(value="/close",method=RequestMethod.GET)
	@ResponseBody
	public String close(PushVO pushVO) throws Exception{
		if(pushVO.getUserId()==null){
			return "none";
		}
		
		connService.close(pushVO.getUserId());
        return "success";
	}
	
	@RequestMapping(value="/mockonline",method=RequestMethod.GET)
	@ResponseBody
	public String mockonline(PushVO pushVO) throws Exception{
		if(pushVO.getUserId()==null){
			return "none";
		}
		internalEventBus.postAsync(new UserOnlineEvent(pushVO.getUserId()));
        return "success";
	}
	
	@RequestMapping(value="/push",method=RequestMethod.GET)
	@ResponseBody
	public String push(final PushVO pushVO) {
		if(pushVO.getUserId()==null){
			return "none";
		}
		if(StringUtils.isBlank(pushVO.getContent())){
			pushVO.setContent("hello world");
		}
		if(pushVO.getExtras()==null||pushVO.getExtras().size()<=0){
			pushVO.setExtras(PushUtil.getFeedMap());
		}
		if(pushVO.getFlag()==null){
			pushVO.setFlag(3L);
		}
		
		pool.execute(new Runnable() {
			@Override
			public void run() {
				NotificationPushPayload payload = PayloadFactory.create(MessageType.NOTIFICATION);
				payload.setContent(pushVO.getContent()).setTitle(pushVO.getTitle()).setExtras(pushVO.getExtras());
				PushRequest request = PushRequest.build(PushType.MESSAGE, payload, pushVO.getUserId());
				request.option(AttributeKey.DOCTORFLAG, pushVO.getFlag()); 
				pushService.push(request);
			}
		});
        return "success";
	}
	
	@RequestMapping(value="/stat",method=RequestMethod.GET)
	@ResponseBody
	public String stat(StatVO statVO) throws Exception{
		StatType type = StatVO.StatType.get(statVO.getType());
		
		int start = statVO.getPage()*statVO.getSize();
		int end = start+statVO.getSize()-1;
		if(StringUtils.isBlank(statVO.getIp())){
			statVO.setIp("120.27.196.68");
		}
		
		Object ret = MethodUtils.invokeMethod(userService, type.getMethod(), type.getParam(statVO.getIp(),start,end), type.getClazz());
        return GsonUtil.toJson(ret);
        
	}
	
	@RequestMapping(value="/server",method=RequestMethod.GET)
	@ResponseBody
	public String server() throws Exception{
        return allotService.serverList();
	}
	
	public static void main(String[] args) {
		System.out.println(GsonUtil.toJson(Lists.newArrayList("10.1.1.1:3000","10.1.2.1:3000")));
	}
	
	
	
}
