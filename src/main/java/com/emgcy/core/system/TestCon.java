//package com.emgcy.core.system;
//
//import java.util.Date;
//import java.util.Iterator;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.emgcy.core.common.base.BaseMsg;
//import com.emgcy.core.common.shiro.ShiroUserUtil;
//
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//
//
//@Controller
//@Api(tags="AAAAAAA类")
//public class TestCon {
//
//	/*@Autowired
//	private IArticleService articleService;
//
//	@Autowired
//	private ArticleEsRepository articleSearch;
//	@Autowired
//	private AlbumEsRepository albumEsRepository;
//	@Autowired
//	private VideoEsRepository videoEsRepository;
//	@Autowired
//	private TrainEsRepository trainEsRepository;
//	@Autowired
//	private VideoAlbumEsRepository videoAlbumEsRepository;*/
//
//
//	/*@RequestMapping("/{pagePath}.ftl")
//	public ModelAndView toView(@PathVariable String pagePath){
//		System.out.println(pagePath);
//		return new ModelAndView("/app/"+pagePath);
//	}*/
//
////
//
////		User user = new User();
////		Random random = new Random();
////		int id = random.nextInt(1000);
////		int orgHospitalId = random.nextInt(1000);
////		user.setId(new Long(id));
////		user.setOrgHospitalId(new Long(orgHospitalId));
//
////		ModelAndView modelAndView = new ModelAndView("ws");
////		modelAndView.addObject("userId", userId);
////		return modelAndView;
////	}
////
////	@RequestMapping("/es/add")
////	@ResponseBody
////	public BaseMsg esAdd(String id) {
////		System.out.println("baseSave2"+id);
////		BaseMsg saveOrUpdateEsData = articleService.saveOrUpdateEsData(new Long(id));
////		System.out.println(saveOrUpdateEsData);
////		return saveOrUpdateEsData;
////	}
////
////  @RequestMapping("/findAll")
////  @ApiImplicitParams({
////		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
////  @ResponseBody
////  public BaseMsg findAll(){
////	System.out.println("0-----------");
////	 Iterable<ArticleEs> findAll = articleSearch.findAll();
////  	Iterator<ArticleEs> iterator = findAll.iterator();
////  	while(iterator.hasNext()){
////  		ArticleEs next = iterator.next();
////  		System.out.println(next);
////  	}
////  	System.out.println("1-----------");
////  	Iterable<VideoAlbumEs> findAll2 = videoAlbumEsRepository.findAll();
////  	Iterator<VideoAlbumEs> iterator2 = findAll2.iterator();
////  	while (iterator2.hasNext()) {
////		VideoAlbumEs videoAlbumEs = (VideoAlbumEs) iterator2.next();
////		System.out.println(videoAlbumEs);
////
////	}
////  	System.out.println("2-----------");
////  	Iterable<AlbumEs> findAll3 = albumEsRepository.findAll();
////  	Iterator<AlbumEs> iterator3 = findAll3.iterator();
////  	while (iterator3.hasNext()) {
////  		AlbumEs albumEs = (AlbumEs) iterator3.next();
////  		System.out.println(albumEs);
////
////  	}
////  	System.out.println("3-----------");
////  	Iterator<VideoEs> iterator4 = videoEsRepository.findAll().iterator();
////  	while (iterator4.hasNext()) {
////  		VideoEs videoEs = (VideoEs) iterator4.next();
////  		System.out.println(videoEs);
////
////	}
////  	System.out.println("4-----------");
////  	Iterator<TrainEs> iterator5 = trainEsRepository.findAll().iterator();
////  	while(iterator5.hasNext()) {
////  		TrainEs next = iterator5.next();
////  		System.out.println(next);
////  	}
////  	return BaseMsg.successMsg("获取成功!");
////  }
////
////
////   @PostMapping("/albumAdd")
////   @ApiImplicitParams({
////		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
////   @ApiOperation(value = "专辑添加")
////   @ResponseBody
////	public BaseMsg addVidoAlbum(AlbumEs albumEs) {
////		albumEs.setUserId(ShiroUserUtil.getCurrentUser().getId());
////		albumEs.setOrgHospitalId(ShiroUserUtil.getHospitalId());
////		albumEs.setCreateTime(new Date());
////		albumEsRepository.save(albumEs);
//
////		List<VideoAlbumEs> list = Lists.newArrayList();
////		for (int i = 1; i <= 3; i++) {
////			VideoEs videoEs=new VideoEs();
////			long nextLong2 = new Random().nextInt(1000000000);
////			videoEs.setId(nextLong2);
////			videoEs.setClassifyName("按时拿到了"+i);
////			videoEs.setLabel(i+"我去了"+(i+1));
////			videoEs.setOrgHospitalId(ShiroUserUtil.getCurrentUser().getOrgHospitalId());
////			videoEs.setCreateTime(new Date());
////			videoEs.setSummary("昂克赛拉卡掉了"+i);
////			videoEs.setState(3);
////			videoEsRepository.save(videoEs);
////
////			VideoAlbumEs videoAlbumEs = new VideoAlbumEs();
////			videoAlbumEs.setId(new Long(i));
////			videoAlbumEs.setAlbumEs(albumEs);
////			videoAlbumEs.setVideoEs(videoEs);
////			list.add(videoAlbumEs);
////		}
////		videoAlbumEsRepository.save(list);
//		return BaseMsg.success();
//	}
//
//}    @PostMapping("/videoAdd")
//    @ApiImplicitParams({
//		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
//    @ApiOperation(value = "视频添加")
//    @ResponseBody
//	public BaseMsg videoAdd(VideoEs videoEs) {
//    	videoEs.setUserId(ShiroUserUtil.getCurrentUser().getId());
//		videoEs.setOrgHospitalId(ShiroUserUtil.getCurrentUser().getOrgHospitalId());
////		videoEs.setCreateTime(new Date());
//		videoEs.setState(3);
//		videoEsRepository.save(videoEs);
//		return BaseMsg.success();
//
//
////    @PostMapping("/videoAlbumEsAdd")
////    @ApiImplicitParams({
////		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
////    @ApiOperation(value = "视频专辑es添加")
////    @ResponseBody
////	public BaseMsg videoAlbumEsAdd() {
////    	VideoAlbumEs videoAlbumEs = new VideoAlbumEs();
////    	AlbumEs albumEs = albumEsRepository.findOne(2L);
////    	videoAlbumEs.setAlbumEs(albumEs);
////    	videoAlbumEs.setId(2L);
////    	VideoEs videoEs = videoEsRepository.findOne(4L);
////    	videoAlbumEs.setVideoEs(videoEs);
//////    	videoEs = videoEsRepository.findOne(2L);
//////    	videoAlbumEs.setVideoEs(videoEs);
//////    	videoEs = videoEsRepository.findOne(3L);
//////    	videoAlbumEs.setVideoEs(videoEs);
////		videoAlbumEsRepository.save(videoAlbumEs);
////		return BaseMsg.success();
////   }
////
//
//}
