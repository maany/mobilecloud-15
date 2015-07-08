package org.magnum.dataup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.magnum.dataup.model.Video;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RESTController {
	Map<Long,Video> videos = new HashMap<Long,Video>();
	
	@RequestMapping(value="/video",method=RequestMethod.GET)
	public @ResponseBody Map<Long,Video> fetchVideoMetaData(){
		System.out.println("Inside addVideoMetadata() Handler method");
		return videos;
	}
	
	@RequestMapping(value="/video",method=RequestMethod.POST)
	public @ResponseBody Video addVideoMetaData(@RequestBody Video video,HttpServletRequest request){
		// Generate unique ID
		video.setId(generateRandomId(video));
		// generate data url
		video.setDataUrl(generateDataUrl(video,request));
		videos.put(video.getId(),video);
		return video;
	}


	@RequestMapping(value="/video/{id}/data", method=RequestMethod.POST)
	public void uploadVideo(@PathVariable("id") String id){
		System.out.println("inside upload");
	}
	
	@RequestMapping(value="/video/{id}/data", method=RequestMethod.GET)
	public void sendVideoToClient(@PathVariable String id,HttpServletResponse response){
		System.out.println("received download request");
	}
	
	private long generateRandomId(Video video){
		Date date = new Date();
		return date.getTime();
	}
	
	private String generateDataUrl(Video video, HttpServletRequest request) {
		String base = 
	              "http://"+request.getServerName() 
	              + ((request.getServerPort() != 80) ? ":"+request.getServerPort() : "");
		String url = base + "/video/" + video.getId() + "/data"; 
		return url;
	}
}
